package com.andimotors.compras.recepcion.dominio;

import com.andimotors.compras.contrato.LineaPedidoContrato;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Logica pura de HU-15: compara, linea por linea, lo solicitado en la orden de
 * compra contra lo recibido, y clasifica el resultado (faltante / sobrante /
 * completo) tanto por linea como de forma global.
 *
 * <p>Responsabilidad unica (SRP): solo compara. No valida entradas, no consulta el
 * pedido y no persiste nada &mdash; de eso se encarga {@link ValidarRecepcionService}.
 * Al no tener dependencias, es trivial de probar unitariamente
 * ({@code new ComparadorRecepcion()}).</p>
 */
@Component
public class ComparadorRecepcion {

    /**
     * @param lineasPedido      lineas del pedido con su cantidad solicitada
     * @param recibidasPorItem  itemId &rarr; cantidad recibida (se asume ya validada:
     *                          no negativa y con una entrada por cada linea del pedido)
     * @return el detalle por linea y el resultado global
     */
    public ResultadoComparacion comparar(List<LineaPedidoContrato> lineasPedido,
                                         Map<Long, Integer> recibidasPorItem) {
        List<LineaRecepcionResultado> resultados = new ArrayList<>(lineasPedido.size());

        for (LineaPedidoContrato linea : lineasPedido) {
            int recibida = recibidasPorItem.getOrDefault(linea.itemId(), 0);
            int diferencia = recibida - linea.cantidadSolicitada();
            resultados.add(new LineaRecepcionResultado(
                    linea.itemId(),
                    linea.productoId(),
                    linea.productoNombre(),
                    linea.cantidadSolicitada(),
                    recibida,
                    diferencia,
                    TipoDiferencia.desde(diferencia)));
        }

        boolean hayDiferencias = resultados.stream().anyMatch(LineaRecepcionResultado::tieneDiferencia);
        ResultadoGlobalRecepcion global = hayDiferencias
                ? ResultadoGlobalRecepcion.CON_DIFERENCIAS
                : ResultadoGlobalRecepcion.COMPLETA;

        return new ResultadoComparacion(resultados, global);
    }
}
