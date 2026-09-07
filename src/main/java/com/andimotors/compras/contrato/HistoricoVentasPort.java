package com.andimotors.compras.contrato;

import java.time.YearMonth;

/**
 * MOCK / CONTRATO TEMPORAL &mdash; reemplazar cuando HU-12/13/14 esten integradas.
 *
 * <p>Puerto que representa la operacion {@code consultarHistoricoVentas()} de EIS
 * usada por Compras. Lo consume HU-13; se incluye aqui para poder ejecutar PT-13.1 y
 * PT-13.2 contra el mock mientras EIS / HU-13 no existan.</p>
 */
public interface HistoricoVentasPort {

    /**
     * Consulta las unidades vendidas de un producto entre dos meses.
     *
     * @param productoId producto / modelo a consultar
     * @param desde      primer mes (inclusive)
     * @param hasta      ultimo mes (inclusive)
     * @return el historico; con {@code unidadesVendidas == 0} si el producto no tuvo
     *         ventas en el rango (nunca lanza excepcion por "sin datos")
     */
    HistoricoVentasContrato consultarHistoricoVentas(Long productoId, YearMonth desde, YearMonth hasta);
}
