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
public class DBMybatisConfig {

    @Value("${spring.mybatisdb.datasource.jdbc-url}")
    private String url;
    @Value("${spring.mybatisdb.datasource.username}")
    private String username;
    @Value("${spring.mybatisdb.datasource.password}")
    private String password;

    @Autowired
    StandardPBEStringEncryptor pbeEnc;

    @Bean(name="mybatisDataSource")
    //@ConfigurationProperties(prefix="spring.mybatisdb.datasource")
    public DataSource dataSource() {
        String DJdbcUrl = pbeEnc.decrypt(url.substring(4));
        String DPassword = pbeEnc.decrypt(password.substring(4));
        String DUsername = pbeEnc.decrypt(username.substring(4));

        return DataSourceBuilder.create()
                .url(DJdbcUrl)
                .username(DUsername)
                .password(DPassword)
                .build();

    }
}
*/
