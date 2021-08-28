package com.erp.shardingjdbcdemo.config;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author 2p
 * @date 2021/8/19 12:05
 * @desc 分表策略
 */
@Component
public class TableSharadingAlgorithm implements PreciseShardingAlgorithm<Integer> {
    @Override
    public String doSharding(Collection<String> tableNames, PreciseShardingValue<Integer> shardingValue) {
        int tableIndex = shardingValue.getValue() % tableNames.size();
        for (String tableName : tableNames) {
            System.out.println(String.format("TableSharadingAlgorithm >> doSharding table:%s,value:%s,size:%s", tableName, shardingValue.getValue(), tableNames.size()));
            if (tableNames.size() == 1) {
                return tableName;
            }
            if (tableName.endsWith(String.valueOf(tableIndex))) {
                return tableName;
            }
        }
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {
        String a = "01";
        System.out.println(Integer.parseInt(a));

    }
}
