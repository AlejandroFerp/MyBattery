package com.batteryworkshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal del proyecto. Inicializa la aplicación Spring Boot.
 */
@SpringBootApplication
public class BatteryWorkshopApplication {

    /**
     * Método principal que arranca la aplicación Spring Boot.
     *
     * @param args Argumentos de línea de comandos.
     */

    public static void main(String[] args) {
        SpringApplication.run(BatteryWorkshopApplication.class, args);
    }
}
