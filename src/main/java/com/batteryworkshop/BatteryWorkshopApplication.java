package com.batteryworkshop;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main class that initializes the Battery Workshop application.
 * This class configures and boots up the Spring Boot context,
 * initializing all components and services required for the application.
 */
@SpringBootApplication
public class BatteryWorkshopApplication {

    /**
     * Logger instance for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(BatteryWorkshopApplication.class);

    /**
     * Application class reference used for Spring Boot initialization
     */
    private static final Class<BatteryWorkshopApplication> APPLICATION_CLASS = BatteryWorkshopApplication.class;

    /**
     * Main entry point of the application.
     * Initializes the Spring Boot context and configures the application.
     *
     * @param args Command line arguments passed to the application
     * @throws RuntimeException if there is an error during application startup
     */
    public static void main(String[] args) {
        try {
            logger.info("Iniciando la aplicación del Taller de Baterías...");
            SpringApplication.run(APPLICATION_CLASS, args);
            logger.info("Aplicación iniciada exitosamente");
        } catch (Exception e) {
            logger.error("Error al iniciar la aplicación: {}", e.getMessage(), e);
            throw new RuntimeException("Error en el inicio de la aplicación", e);
        }
    }

}