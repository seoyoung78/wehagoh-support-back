package com.duzon.lulu.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Properties;

@Configuration
@PropertySource("classpath:/conf/globals.properties")
public class GlobalProperties {
    @Bean(name="luluProperties")
    @Primary
    public Properties luluProperties() throws IOException {
        Properties properties = new Properties();
        Resource resource = new ClassPathResource("conf/globals.properties");
        properties.load(resource.getInputStream());
        return properties;

        //return  new Properties();
    }

//    private String SERVER_DOMAIN;
//    private String SITE_NAME;
//    private String PORTAL_URL;
//    private String INNER_GATEWAY_URL;
//    private String LOGSYTEM_SERVER_DOMAIN;
//    private String CLIENT_ID;
//    private String WEHAGO_LOG_PATH;
//    private String WEHAGO_LOG_YN;
//    private String RISK_A_URL_LIST;
//    private String RISK_B_URL_LIST;
//    private String RISK_C_URL_LIST;
//    private String RISK_D_URL_LIST;
//    private String SERVICE_CODE;
//    private String SECURITY_KEY;
//    private String LOG_MESSAGE_SYNC_ASYNC;
//    private String INNER_GATEWAY_SYNC_ASYNC;
//    private String HTTP_CONNECTION_SYNC_ASYNC;
}
