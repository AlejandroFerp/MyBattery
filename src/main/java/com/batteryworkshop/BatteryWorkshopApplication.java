package com.batteryworkshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase principal que inicializa la aplicación del Taller de Baterías.
 * Esta clase configura y arranca el contexto de Spring Boot, inicializando
 * todos los componentes y servicios necesarios para la aplicación.
 */
@SpringBootApplication
public class BatteryWorkshopApplication {

    private static final Logger logger = LoggerFactory.getLogger(BatteryWorkshopApplication.class);
    private static final Class<BatteryWorkshopApplication> APPLICATION_CLASS = BatteryWorkshopApplication.class;

    /**
     * Punto de entrada principal de la aplicación.
     * Inicializa el contexto de Spring Boot y configura la aplicación.
     *
     * @param args Argumentos de la línea de comandos que se pasan a la aplicación
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