package com.hotel.backend.config;

import javax.sql.DataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.*;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.hotel.backend.repository.local",
        entityManagerFactoryRef = "localEntityManagerFactory",
        transactionManagerRef = "localTransactionManager"
)
public class LocalDataSourceConfig {

    @Value("${local.datasource.url}")
    private String localUrl;

    @Value("${local.datasource.driver-class-name}")
    private String localDriver;

    @Value("${local.datasource.connection-properties:busy_timeout=30000;journal_mode=WAL;synchronous=NORMAL}")
    private String connectionProperties;

    @Bean
    @Primary
    public DataSource localDataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create()
                .driverClassName(localDriver)
                .url(localUrl);

        DataSource dataSource = dataSourceBuilder.build();

        if (dataSource instanceof com.zaxxer.hikari.HikariDataSource hikariDataSource) {
            hikariDataSource.setMaximumPoolSize(5);
            hikariDataSource.setMinimumIdle(2);
            hikariDataSource.setConnectionTimeout(60000); // 60 segundos
            hikariDataSource.setIdleTimeout(120000); // 2 minutos
            hikariDataSource.setMaxLifetime(300000); // 5 minutos
            hikariDataSource.setLeakDetectionThreshold(60000); // 60 segundos
            hikariDataSource.setConnectionInitSql("PRAGMA foreign_keys=ON; PRAGMA busy_timeout=30000; PRAGMA journal_mode=WAL; PRAGMA synchronous=NORMAL;");
            hikariDataSource.setAutoCommit(true);

            // Propiedades para evitar bloqueos
            hikariDataSource.setConnectionTestQuery("SELECT 1");
            hikariDataSource.setValidationTimeout(5000);
            hikariDataSource.setAllowPoolSuspension(false);
            // Eliminamos esta línea que causa el conflicto con Spring Boot
            // hikariDataSource.setRegisterMbeans(true);
        }

        return dataSource;
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean localEntityManagerFactory(
            @Qualifier("localDataSource") DataSource ds) {

        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(ds);
        emf.setPackagesToScan("com.hotel.backend.model");
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect", "org.hibernate.community.dialect.SQLiteDialect");
        properties.put("hibernate.show_sql", true);
        properties.put("hibernate.format_sql", true);

        // Propiedades para mejorar rendimiento con SQLite
        properties.put("hibernate.connection_handling", "DELAYED_ACQUISITION_AND_RELEASE_AFTER_TRANSACTION");
        properties.put("hibernate.jdbc.batch_size", 10);
        properties.put("hibernate.order_inserts", true);
        properties.put("hibernate.jdbc.time_zone", "UTC");

        // Propiedades simplificadas para evitar conflictos
        properties.put("hibernate.connection.release_mode", "after_transaction");
        properties.put("hibernate.connection.provider_disables_autocommit", false);

        emf.setJpaPropertyMap(properties);
        return emf;
    }

    @Bean
    @Primary
    public PlatformTransactionManager localTransactionManager(
            @Qualifier("localEntityManagerFactory") EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager(emf);
        transactionManager.setDefaultTimeout(30);
        return transactionManager;
    }
}