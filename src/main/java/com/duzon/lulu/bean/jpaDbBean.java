package com.duzon.lulu.bean;

import com.duzon.clinichelper.infrastructure.datasource.model.RoutingDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(
    basePackages = "com.duzon.lulu.service.**.mapper",
    entityManagerFactoryRef = "entityManagerFactoryCustom"
)
public class jpaDbBean {

    @Value("${spring.jpa.hibernate.dialect}")
    private String dialect;

    @Value("${spring.jpa.properties.hibernate.format_sql}")
    private String format_sql;

    @Value("${spring.jpa.properties.hibernate.show_sql}")
    private String show_sql;

    @Autowired
    RoutingDataSource routingDataSource;

    @Bean(name = "entityManagerFactoryCustom")
    public EntityManagerFactory entityManagerFactory() {

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(routingDataSource);
        em.setPackagesToScan("com.duzon.lulu.service.model"); // primary 데이터베이스에 대한 Entity 패키지를 지정해주세요.

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(true);
        em.setJpaVendorAdapter(vendorAdapter);

        Properties jpaProperties = new Properties();
        // 여기서 프로퍼티 값을 직접 설정합니다.
        jpaProperties.setProperty("hibernate.dialect", dialect);
        jpaProperties.setProperty("properties.hibernate.format_sql", format_sql);
        jpaProperties.setProperty("properties.hibernate.show_sql", show_sql);
        em.setJpaProperties(jpaProperties);
        em.afterPropertiesSet();

        return em.getObject();
    }

    /**
     * transaction manager
     */
    @Bean
	public PlatformTransactionManager transactionManager(@Qualifier("entityManagerFactoryCustom") EntityManagerFactory entityManagerFactory) throws Exception {
		// MyBatis transactional
		DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
		dataSourceTransactionManager.setDataSource(routingDataSource);

		// JPA transactional
		JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
		jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);

		// Chained transaction manager (MyBatis X JPA)
		ChainedTransactionManager transactionManager = new ChainedTransactionManager(jpaTransactionManager, dataSourceTransactionManager);
		return transactionManager;
	}
//    @Bean(name= "txManager")
//    public PlatformTransactionManager txManager(@Qualifier("test1DataSource") DataSource dataSource) {
//        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(dataSource);
//        dataSourceTransactionManager.setNestedTransactionAllowed(true); // nested
//
//        return dataSourceTransactionManager;
//    }
//
//    @Bean(name= "jpaTxManager")
//    public PlatformTransactionManager txManager(@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
//        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
//        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
//
//        return jpaTransactionManager;
//    }

//    @Bean(name = "transactionManager")
//    public JpaTransactionManager transactionManager(@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
//        return new JpaTransactionManager(entityManagerFactory);
//    }

//    @Bean(name = "j∂dbcTemplate")
//    public JdbcTemplate jdbcTemplate(@Qualifier("test2DataSource") DataSource dataSource) {
//        return new JdbcTemplate(dataSource);
//    }


}
