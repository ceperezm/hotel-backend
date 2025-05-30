package com.hotel.backend;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@SpringBootApplication(
        exclude = {
                DataSourceAutoConfiguration.class,
                DataSourceTransactionManagerAutoConfiguration.class,
                HibernateJpaAutoConfiguration.class,
                SecurityAutoConfiguration.class
        }
)
public class HotelBackendAplication {

    // Inyecta el EntityManager local, según configuración que tengas
    @PersistenceContext(unitName = "localEntityManagerFactory")
    private EntityManager localEntityManager;

    // Inyecta el EntityManager cloud, si tienes otro
    @PersistenceContext(unitName = "cloudEntityManagerFactory")
    private EntityManager cloudEntityManager;

    public static void main(String[] args) {
        SpringApplication.run(HotelBackendAplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*");
            }
        };
    }

    @Bean
    public CommandLineRunner verificarTablas() {
        return args -> {
            System.out.println("📦 Verificando tablas en la base de datos LOCAL:");
            printTables(localEntityManager, "sqlite");

            System.out.println("\n☁️ Verificando tablas en la base de datos CLOUD:");
            printTables(cloudEntityManager, "postgres");
        };
    }

    private void printTables(EntityManager em, String dbType) {
        if (em == null) {
            System.out.println("⚠️ EntityManager para " + dbType + " no está configurado.");
            return;
        }
        String sql;
        if (dbType.equalsIgnoreCase("sqlite")) {
            sql = "SELECT name FROM sqlite_master WHERE type='table'";
        } else if (dbType.equalsIgnoreCase("postgres")) {
            sql = "SELECT table_name FROM information_schema.tables WHERE table_schema = 'public'";
        } else {
            System.out.println("Tipo de base de datos no soportado: " + dbType);
            return;
        }
        try {
            Query query = em.createNativeQuery(sql);
            List<?> tables = query.getResultList();
            if (tables.isEmpty()) {
                System.out.println("❌ No se encontraron tablas.");
            } else {
                for (Object table : tables) {
                    System.out.println("✅ Tabla encontrada: " + table.toString());
                }
            }
        } catch (Exception e) {
            System.out.println("⚠️ Error al consultar tablas en " + dbType + ": " + e.getMessage());
        }
    }
}
