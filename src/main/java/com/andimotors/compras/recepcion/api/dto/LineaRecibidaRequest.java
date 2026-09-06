package com.andimotors.compras.recepcion.api.dto;

import jakarta.validation.constraints.NotNull;

/**
 * Cantidad recibida para una linea del pedido (payload de entrada).
 *
 * <p>La regla "no negativa" se valida en el dominio
 * ({@code ValidarRecepcionService}) para tener una unica fuente de verdad; aqui
 * solo se exige que los campos vengan presentes.</p>
 */
public record LineaRecibidaRequest(

        @NotNull(message = "itemId es obligatorio")
        Long itemId,

        @NotNull(message = "cantidadRecibida es obligatoria")
        Integer cantidadRecibida
) {
}
