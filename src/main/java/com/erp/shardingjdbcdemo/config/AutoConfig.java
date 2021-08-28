package com.erp.shardingjdbcdemo.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author chaiyueying
 * @date 2021/3/20
 */
@ComponentScan(basePackages = "com.erp.shardingjdbcdemo")
//@EnableJpaRepositories(basePackages = "com.erp.shardingjdbcdemo")
@EntityScan(basePackages = "com.erp.shardingjdbcdemo.domain")
public class AutoConfig {
}
