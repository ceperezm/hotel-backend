package com.hotel.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;

@Service
@Slf4j
@RequiredArgsConstructor
public class NetworkMonitorService {

    private static final String[] TEST_URLS = {
            "https://www.google.com",
            "https://www.cloudflare.com"
    };

    @Autowired
    @Qualifier("cloudDataSource")
    private DataSource cloudDataSource;

    /**
     * Verifica si hay conexión a Internet o a la base de datos PostgreSQL
     * @return true si hay conexión a Internet o a PostgreSQL
     */
    public boolean isNetworkAvailable() {
        return isInternetAvailable() || isPostgresAvailable();
    }

    /**
     * Verifica la conexión a Internet probando URLs públicas
     */
    private boolean isInternetAvailable() {
        for (String testUrl : TEST_URLS) {
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(testUrl).openConnection();
                connection.setConnectTimeout(3000);
                connection.setReadTimeout(3000);
                connection.setRequestMethod("HEAD");
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    log.debug("Conexión a Internet disponible");
                    return true;
                }
            } catch (IOException e) {
                log.debug("No se pudo conectar a {}: {}", testUrl, e.getMessage());
            }
        }
        return false;
    }

    /**
     * Verifica la conexión a la base de datos PostgreSQL
     */
    private boolean isPostgresAvailable() {
        try (Connection connection = cloudDataSource.getConnection()) {
            if (connection.isValid(3)) {
                log.debug("Conexión a PostgreSQL disponible");
                return true;
            }
        } catch (SQLException e) {
            log.debug("No se pudo conectar a PostgreSQL: {}", e.getMessage());
        }
        return false;
    }
}