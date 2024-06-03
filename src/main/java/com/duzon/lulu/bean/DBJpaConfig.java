/*
package com.duzon.lulu.bean;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DBJpaConfig {
    @Value("${spring.jpadb.datasource.jdbc-url}")
    private String url;
    @Value("${spring.jpadb.datasource.username}")
    private String username;
    @Value("${spring.jpadb.datasource.password}")
    private String password;

    @Autowired
    StandardPBEStringEncryptor pbeEnc;

    @Bean(name="jpaDataSource")
    //@ConfigurationProperties(prefix="spring.jpadb.datasource")
    public DataSource dataSource2() {
        String DJdbcUrl = pbeEnc.decrypt(url.substring(4));
        String DPassword = pbeEnc.decrypt(password.substring(4));
        String DUsername = pbeEnc.decrypt(username.substring(4));

        return DataSourceBuilder.create()
                .url(DJdbcUrl)
                .username(DUsername)
                .password(DPassword)
                .build();
    }
}*/
