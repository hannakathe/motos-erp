package com.andimotors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto de entrada del ERP AndiMotors.
 *
 * <p>Estado del repositorio: este Sprint solo entrega el modulo de Compras / Recepcion
 * (HU-15). Los modulos Inventario, Facturacion, Empleados, EIS y ActivosFijos todavia
 * no tienen codigo; se documentan en {@code docs/arc-42/}.</p>
 */
@SpringBootApplication
public class AndiMotorsErpApplication {

    public static void main(String[] args) {
        SpringApplication.run(AndiMotorsErpApplication.class, args);
    }
}
