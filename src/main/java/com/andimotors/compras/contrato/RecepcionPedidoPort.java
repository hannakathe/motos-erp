package com.andimotors.compras.contrato;

import java.util.Map;

/**
 * MOCK / CONTRATO TEMPORAL &mdash; reemplazar cuando HU-12/13/14 esten integradas.
 *
 * <p>Puerto de <b>escritura</b> que HU-15 usa para persistir el resultado de validar
 * una recepcion. Se separa de {@link PedidoContratoPort} (ISP): quien solo consulta
 * pedidos no deberia ver el metodo de escritura de recepcion.</p>
 */
public interface RecepcionPedidoPort {

    /**
     * Aplica el resultado de una validacion de recepcion de forma <b>atomica</b>
     * (RNF-6: integridad transaccional): actualiza el estado del pedido y guarda la
     * cantidad recibida de cada item. Si algo falla, no debe quedar el estado
     * cambiado sin las cantidades ni viceversa.
     *
     * @param pedidoId                     pedido sobre el que se registra la recepcion
     * @param nuevoEstado                  {@code RECIBIDO} o {@code RECIBIDO_CON_DIFERENCIAS}
     * @param cantidadesRecibidasPorItem   itemId &rarr; unidades efectivamente recibidas
     */
    void aplicarResultadoRecepcion(Long pedidoId,
                                   EstadoPedido nuevoEstado,
                                   Map<Long, Integer> cantidadesRecibidasPorItem);
}
