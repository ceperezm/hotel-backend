package com.hotel.backend.config;

import javax.sql.DataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.*;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.hotel.backend.repository.cloud",
        entityManagerFactoryRef = "cloudEntityManagerFactory",
        transactionManagerRef    = "cloudTransactionManager"
)
public class CloudDataSourceConfig {

    @Value("${cloud.datasource.url}")
    private String cloudUrl;

    @Value("${cloud.datasource.username}")
    private String cloudUser;

    @Value("${cloud.datasource.password}")
    private String cloudPass;

    @Value("${cloud.datasource.driver-class-name}")
    private String cloudDriver;

    @Bean
    public DataSource cloudDataSource() {
        return DataSourceBuilder.create()
                .driverClassName(cloudDriver)
                .url(cloudUrl)
                .username(cloudUser)
                .password(cloudPass)
                .build();
    }

    @Bean
    @ConfigurationProperties("cloud.jpa")
    public JpaProperties cloudJpaProperties() {
        return new JpaProperties();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean cloudEntityManagerFactory(
            @Qualifier("cloudDataSource") DataSource ds,
            @Qualifier("cloudJpaProperties") JpaProperties jpaProps) {

        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(ds);
        emf.setPackagesToScan("com.hotel.backend.model");
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        emf.setJpaPropertyMap(jpaProps.getProperties());
        return emf;
    }

    @Bean
    public PlatformTransactionManager cloudTransactionManager(
            @Qualifier("cloudEntityManagerFactory") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}
