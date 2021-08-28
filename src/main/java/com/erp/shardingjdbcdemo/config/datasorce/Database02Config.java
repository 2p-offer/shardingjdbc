package com.erp.shardingjdbcdemo.config.datasorce;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 2p
 * @date 2021/8/19 12:08
 * @desc Database01
 */
@Component
@ConfigurationProperties(prefix = "sharding.db2")
public class Database02Config  extends DataBaseConfig{

}
