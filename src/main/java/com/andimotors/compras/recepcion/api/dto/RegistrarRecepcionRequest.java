package com.andimotors.compras.recepcion.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * Payload de {@code POST /api/compras/recepciones}: registrar la recepcion de un
 * pedido con las cantidades recibidas por linea.
 */
public record RegistrarRecepcionRequest(

        @NotNull(message = "pedidoId es obligatorio")
        Long pedidoId,

        @NotEmpty(message = "debe registrar al menos una linea")
        @Valid
        List<LineaRecibidaRequest> lineas
) {
}
