package com.duzon.lulu.bean;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JasyptConfig {

    @Bean
    public EnvironmentStringPBEConfig encryptorConfig() {
        EnvironmentStringPBEConfig config = new EnvironmentStringPBEConfig();
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setPasswordEnvName("APP_ENCRYPTION_PASSWORD");
        return config;
    }
    @Autowired
    private EnvironmentStringPBEConfig encryptorConfig;

    @Bean
    public StandardPBEStringEncryptor encryptor() {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setConfig(encryptorConfig);
        encryptor.setPassword("tmzkdlvmffotvha");
        return encryptor;
    }
}
