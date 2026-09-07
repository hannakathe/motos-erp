package com.andimotors.compras.contrato;

import java.time.LocalDate;

/**
 * MOCK / CONTRATO TEMPORAL &mdash; reemplazar cuando HU-12/13/14 esten integradas.
 *
 * <p>Puerto de escritura de HU-14 ("Registrar fecha estimada de entrega"). No lo usa
 * HU-15; se incluye en el contrato para poder ejecutar el caso de prueba PT-14.1
 * contra el mock mientras HU-14 no exista.</p>
 */
public interface PlaneacionPedidoPort {

    /**
     * Asocia una fecha estimada de entrega a un pedido existente.
     *
     * @param pedidoId pedido a actualizar
     * @param fecha    fecha estimada de entrega
     */
    void registrarFechaEstimadaEntrega(Long pedidoId, LocalDate fecha);
}
