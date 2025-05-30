package com.hotel.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Configuración para habilitar la ejecución de tareas asíncronas en la aplicación.
 * Esta clase define un `ThreadPoolTaskExecutor` que se utilizará para manejar tareas
 * asíncronas con un pool de hilos configurado.
 */
@Configuration
@EnableAsync // Habilita el soporte para métodos asíncronos en la aplicación.
public class AsyncConfig {

    /**
     * Define un bean de tipo `Executor` que se usará para ejecutar tareas asíncronas.
     *
     * @return un `ThreadPoolTaskExecutor` configurado con un pool de hilos.
     */
    @Bean(name = "syncTaskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        // Número mínimo de hilos que estarán activos en el pool.
        executor.setCorePoolSize(2);

        // Número máximo de hilos que pueden estar activos en el pool.
        executor.setMaxPoolSize(4);

        // Capacidad de la cola para almacenar tareas pendientes.
        executor.setQueueCapacity(10);

        // Prefijo para los nombres de los hilos creados por este ejecutor.
        executor.setThreadNamePrefix("Sync-");

        // Inicializa el ejecutor con la configuración especificada.
        executor.initialize();

        return executor;
    }
}