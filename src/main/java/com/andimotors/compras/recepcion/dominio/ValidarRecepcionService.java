package com.andimotors.compras.recepcion.dominio;

import com.andimotors.compras.contrato.EstadoPedido;
import com.andimotors.compras.contrato.LineaPedidoContrato;
import com.andimotors.compras.contrato.PedidoContrato;
import com.andimotors.compras.contrato.PedidoContratoPort;
import com.andimotors.compras.contrato.RecepcionPedidoPort;
import com.andimotors.compras.recepcion.dominio.excepcion.CantidadRecibidaInvalidaException;
import com.andimotors.compras.recepcion.dominio.excepcion.EstadoPedidoNoRecepcionableException;
import com.andimotors.compras.recepcion.dominio.excepcion.LineaDesconocidaException;
import com.andimotors.compras.recepcion.dominio.excepcion.PedidoNoEncontradoException;
import com.andimotors.compras.recepcion.dominio.excepcion.RegistroRecepcionIncompletoException;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Caso de uso HU-15: "Validar recepcion".
 *
 * <p>Orquesta el flujo (SRP: coordina, no calcula ni persiste directamente):</p>
 * <ol>
 *   <li>carga el pedido a traves de {@link PedidoContratoPort} (DIP);</li>
 *   <li>valida entradas y precondiciones;</li>
 *   <li>delega la comparacion en {@link ComparadorRecepcion};</li>
 *   <li>persiste el resultado a traves de {@link RecepcionPedidoPort} (DIP).</li>
 * </ol>
 *
 * <p>Depende solo de abstracciones del contrato; no conoce el mock ni JPA.</p>
 */
@Service
public class ValidarRecepcionService {

    /** Estados en los que un pedido puede recibirse. */
    private static final EstadoPedido[] ESTADOS_POR_RECIBIR = {EstadoPedido.PENDIENTE, EstadoPedido.APROBADO};

    private final PedidoContratoPort pedidoPort;
    private final RecepcionPedidoPort recepcionPort;
    private final ComparadorRecepcion comparador;

    public ValidarRecepcionService(PedidoContratoPort pedidoPort,
                                   RecepcionPedidoPort recepcionPort,
                                   ComparadorRecepcion comparador) {
        this.pedidoPort = pedidoPort;
        this.recepcionPort = recepcionPort;
        this.comparador = comparador;
    }

    /** Pedidos disponibles para recepcion (pantalla de seleccion). */
    public List<PedidoContrato> consultarPedidosPorRecibir() {
        return pedidoPort.listarPedidosPorEstado(ESTADOS_POR_RECIBIR);
    }

    /** Detalle de un pedido para precargar el formulario de recepcion. */
    public PedidoContrato consultarPedidoParaRecibir(Long pedidoId) {
        return pedidoPort.obtenerPedido(pedidoId)
                .orElseThrow(() -> new PedidoNoEncontradoException(pedidoId));
    }

    /**
     * Registra y valida la recepcion de un pedido: compara lo recibido contra lo
     * solicitado, detecta faltantes/sobrantes y actualiza el estado del pedido.
     *
     * @param registro pedido + cantidades recibidas por linea
     * @return resultado con el detalle por linea y el cambio de estado
     * @throws PedidoNoEncontradoException           si el pedido no existe
     * @throws EstadoPedidoNoRecepcionableException  si el pedido no esta PENDIENTE/APROBADO
     * @throws CantidadRecibidaInvalidaException     si alguna cantidad recibida es negativa
     * @throws LineaDesconocidaException             si se registra una linea ajena al pedido
     * @throws RegistroRecepcionIncompletoException  si el registro no cubre cada linea del pedido exactamente una vez
     */
    public ResultadoRecepcion registrarRecepcion(RegistroRecepcion registro) {
        PedidoContrato pedido = pedidoPort.obtenerPedido(registro.pedidoId())
                .orElseThrow(() -> new PedidoNoEncontradoException(registro.pedidoId()));

        if (!pedido.estado().esRecepcionable()) {
            throw new EstadoPedidoNoRecepcionableException(pedido.id(), pedido.estado());
        }

        Map<Long, Integer> recibidasPorItem = validarYMapear(registro, pedido);

        ResultadoComparacion comparacion = comparador.comparar(pedido.lineas(), recibidasPorItem);
        EstadoPedido estadoNuevo = comparacion.global().estadoResultante();

        recepcionPort.aplicarResultadoRecepcion(pedido.id(), estadoNuevo, recibidasPorItem);

        return new ResultadoRecepcion(
                pedido.id(),
                pedido.estado(),
                estadoNuevo,
                comparacion.global(),
                comparacion.lineas());
    }

    /**
     * Valida el registro contra el pedido y lo convierte en un mapa itemId &rarr;
     * cantidad recibida. Reglas:
     * <ul>
     *   <li>cantidades recibidas no negativas;</li>
     *   <li>sin lineas repetidas;</li>
     *   <li>toda linea del registro debe pertenecer al pedido;</li>
     *   <li>toda linea del pedido debe aparecer en el registro.</li>
     * </ul>
     */
    private Map<Long, Integer> validarYMapear(RegistroRecepcion registro, PedidoContrato pedido) {
        if (registro.lineas().isEmpty()) {
            throw new RegistroRecepcionIncompletoException("no se registro ninguna linea");
        }

        Map<Long, Integer> recibidasPorItem = new LinkedHashMap<>();
        for (LineaRecibida linea : registro.lineas()) {
            if (linea.cantidadRecibida() < 0) {
                throw new CantidadRecibidaInvalidaException(linea.itemId(), linea.cantidadRecibida());
            }
            if (pedido.buscarLinea(linea.itemId()).isEmpty()) {
                throw new LineaDesconocidaException(pedido.id(), linea.itemId());
            }
            if (recibidasPorItem.put(linea.itemId(), linea.cantidadRecibida()) != null) {
                throw new RegistroRecepcionIncompletoException("la linea " + linea.itemId() + " esta repetida");
            }
        }

        for (LineaPedidoContrato lineaPedido : pedido.lineas()) {
            if (!recibidasPorItem.containsKey(lineaPedido.itemId())) {
                throw new RegistroRecepcionIncompletoException(
                        "falta registrar la linea " + lineaPedido.itemId()
                                + " (" + lineaPedido.productoNombre() + ")");
            }
        }

        return recibidasPorItem;
    }
}
