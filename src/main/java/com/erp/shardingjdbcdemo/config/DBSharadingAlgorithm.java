package com.erp.shardingjdbcdemo.config;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author 2p
 * @date 2021/8/19 12:05
 * @desc 分库策略
 */
@Component
public class DBSharadingAlgorithm implements PreciseShardingAlgorithm<Integer> {
    @Override
    public String doSharding(Collection<String> dbNames, PreciseShardingValue<Integer> shardingValue) {
        for (String dbName : dbNames) {
            if (dbNames.size() == 1) {
                return dbName;
            }
            System.out.println(String.format("DBSharadingAlgorithm >> doSharding dbName:%s,value:%s,size:%s", dbName, shardingValue.getValue() % dbNames.size(), dbNames.size()));
            int s = shardingValue.getValue() % dbNames.size();
            if (dbName.endsWith(s < 10 ? "0" + s : s + "")) {
                return dbName;
            }
        }
        throw new UnsupportedOperationException();
    }
}
