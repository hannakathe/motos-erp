package com.andimotors.compras.contrato;

import java.time.YearMonth;
import java.util.Objects;

/**
 * MOCK / CONTRATO TEMPORAL &mdash; reemplazar cuando HU-12/13/14 esten integradas.
 *
 * <p>Resultado de consultar el historico de ventas de un producto en un rango de
 * meses. Corresponde a la operacion {@code consultarHistoricoVentas()} que el
 * componente EIS expone a Compras (ver arc42 5.1 y diagrama_componentes.plantuml:
 * {@code COM ..> EIS}). Lo consume HU-13.</p>
 *
 * @param productoId       producto / modelo consultado
 * @param desde            primer mes del rango (inclusive)
 * @param hasta            ultimo mes del rango (inclusive)
 * @param unidadesVendidas total de unidades vendidas en el rango; 0 si no hay ventas
 */
public record HistoricoVentasContrato(
        Long productoId,
        YearMonth desde,
        YearMonth hasta,
        long unidadesVendidas
) {

    public HistoricoVentasContrato {
        Objects.requireNonNull(productoId, "productoId es obligatorio");
        Objects.requireNonNull(desde, "desde es obligatorio");
        Objects.requireNonNull(hasta, "hasta es obligatorio");
        if (hasta.isBefore(desde)) {
            throw new IllegalArgumentException("El rango de meses es invalido: 'hasta' es anterior a 'desde'");
        }
        if (unidadesVendidas < 0) {
            throw new IllegalArgumentException("unidadesVendidas no puede ser negativo");
        }
    }
}
