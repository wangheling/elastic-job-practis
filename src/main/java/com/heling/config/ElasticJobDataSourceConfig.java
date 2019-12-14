package com.heling.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * elastic-job数据追踪数据源
 */
@Configuration
public class ElasticJobDataSourceConfig {

    @Bean
    public BasicDataSource elasticJobDataSource(@Value("${elasticjob.datasource.driver}") String driver,
                                                       @Value("${elasticjob.datasource.url}") String url,
                                                       @Value("${elasticjob.datasource.username}") String username,
                                                       @Value("${elasticjob.datasource.password}") String password) {
        //org.apache.commons.dbcp.BasicDataSource
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;

    }
}
