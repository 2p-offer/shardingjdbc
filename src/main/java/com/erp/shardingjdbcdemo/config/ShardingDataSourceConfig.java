package com.erp.shardingjdbcdemo.config;

import com.erp.shardingjdbcdemo.config.datasorce.Database01Config;
import com.erp.shardingjdbcdemo.config.datasorce.Database02Config;
import com.erp.shardingjdbcdemo.config.datasorce.Database03Config;
import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author 2p
 * @date 2021/8/19 12:05
 * @desc ShardingDataSourceConfig
 */
@Configuration
public class ShardingDataSourceConfig {

    @Autowired
    private Database01Config database01Config;

    @Autowired
    private Database02Config database02Config;

    @Autowired
    private Database03Config database03Config;

    @Autowired
    private DBSharadingAlgorithm dbSharadingAlgorithm;

    @Autowired
    private TableSharadingAlgorithm tableSharadingAlgorithm;

    @Autowired
    private JpaProperties jpaProperties;

    @Bean(name = "entityManagerFactorySharding")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) throws Exception {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = builder
                .dataSource(getShardingDataSource())
                .properties(jpaProperties.getProperties())//jpa配置项
                .packages("com.erp.shardingjdbcdemo.domain") //实体类所在位置
                .persistenceUnit("shardingPersistenceUnit")
                .build();
        return entityManagerFactory;
    }

    @Bean(name = "transactionManagerSharding")
    public PlatformTransactionManager transactionManagerGm(EntityManagerFactoryBuilder builder) throws Exception {
        return new JpaTransactionManager(entityManagerFactory(builder).getObject());
    }


    @Bean(name = "shardingDataSource")
    public DataSource getShardingDataSource() throws SQLException {
        Map<String, DataSource> dataSourceMap = getDataSourceMap();

        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();

        //配置分表规则
        shardingRuleConfig.getTableRuleConfigs().add(getTableRuleConfiguration(dataSourceMap.keySet(), "name"));
        shardingRuleConfig.getBindingTableGroups().add("name");

        shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("uid", dbSharadingAlgorithm));
        shardingRuleConfig.setDefaultTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("uid", tableSharadingAlgorithm));

        return ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, new Properties());
    }

    private TableRuleConfiguration getTableRuleConfiguration(Set<String> dbNames, String table) {
        // 配置表规则
        TableRuleConfiguration tableRuleConfig = new TableRuleConfiguration(table, getActualDataNodes(dbNames, table));
        tableRuleConfig.setKeyGeneratorConfig(new KeyGeneratorConfiguration("SNOWFLAKE", "uid"));
        // 配置分库 + 分表策略
        tableRuleConfig.setDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("uid", dbSharadingAlgorithm));
        tableRuleConfig.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("uid", tableSharadingAlgorithm));
        return tableRuleConfig;
    }

    private String getActualDataNodes(Set<String> dbNames, String table) {
        int dbCount = dbNames.size();
        StringBuilder actualDataNodes = new StringBuilder();
        for (String dbName : dbNames) {
            actualDataNodes.append(dbName).append(".").append(table);
            if (dbCount > 1) {
                actualDataNodes.append("_${[");
                actualDataNodes.append(getTables(dbName, dbCount));
                actualDataNodes.append("]},");
            }
        }
        return dbCount == 1 ? actualDataNodes.toString() : actualDataNodes.substring(0, actualDataNodes.length() - 1);
    }

    private String getTables(String dbName, int dbCount) {
        int tableCount = 4;
        StringBuilder builder = new StringBuilder();
        int db = Integer.parseInt(dbName.split("_")[1]);
        for (int i = 0; i < tableCount; i++) {
            int tableNum = i % tableCount;
            builder.append("'");
            builder.append(tableNum < 10 ? "0" + tableNum : String.valueOf(tableNum));
            builder.append("'");
            builder.append(",");
        }
        System.out.println("ShardingDataSourceConfig >> db:" + dbName + "tablse:" + builder.toString());
//        return "'00','01','02','03'";
        return removeEnd(builder.toString(), ",");
    }

    private String removeEnd(String str, String remove) {
        if (!isEmpty(str) && !isEmpty(remove)) {
            return str.endsWith(remove) ? str.substring(0, str.length() - remove.length()) : str;
        } else {
            return str;
        }
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    private Map<String, DataSource> getDataSourceMap() {
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        dataSourceMap.put(database01Config.getDbname(), database01Config.createDataSource());
        dataSourceMap.put(database02Config.getDbname(), database02Config.createDataSource());
        dataSourceMap.put(database03Config.getDbname(), database03Config.createDataSource());
        return dataSourceMap;
    }
}
