package com.erp.shardingjdbcdemo.config.datasorce;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;

import javax.sql.DataSource;

/**
 * @author 2p
 * @date 2021/8/19 12:10
 * @desc DataBaseConfig
 */
@Data
public class DataBaseConfig {

    private String url;
    private String username;
    private String password;
    private String driverClassName;
    private String dbname;

    public DataSource createDataSource() {
        DruidDataSource result = new DruidDataSource();
        result.setDriverClassName(getDriverClassName());
        result.setUrl(getUrl());
        result.setUsername(getUsername());
        result.setPassword(getPassword());
        return result;
    }
}
