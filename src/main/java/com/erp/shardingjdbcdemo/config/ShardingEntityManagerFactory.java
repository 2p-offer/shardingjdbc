package com.erp.shardingjdbcdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.sql.DataSource;

/**
 * @author 2p
 * @date 2021/8/19 14:39
 * @desc ShardingEntityManagerFactory
 */
//@Configuration
public class ShardingEntityManagerFactory {

    @Autowired
    @Qualifier("shardingDataSource")
    private DataSource dataSource;


}
