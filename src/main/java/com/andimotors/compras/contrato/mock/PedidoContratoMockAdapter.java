package com.andimotors.compras.contrato.mock;

import com.andimotors.compras.contrato.EstadoPedido;
import com.andimotors.compras.contrato.PedidoContrato;
import com.andimotors.compras.contrato.PedidoContratoPort;
import com.andimotors.compras.contrato.PlaneacionPedidoPort;
import com.andimotors.compras.contrato.RecepcionPedidoPort;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * MOCK &mdash; reemplazar cuando HU-12/13/14 esten integradas.
 *
 * <p>Implementacion en memoria de los puertos del contrato de pedidos. Es el
 * <b>unico punto de reemplazo</b>: cuando exista HU-12 real, se crea un adaptador
 * JPA equivalente (perfil {@code jpa}) y se elimina este paquete {@code mock}.</p>
 *
 * <p>HU-15 nunca importa esta clase: depende de las interfaces
 * ({@link PedidoContratoPort}, {@link RecepcionPedidoPort}) y Spring inyecta esta
 * implementacion bajo el perfil {@code mock}.</p>
 */
@Component
@Profile("mock")
public class PedidoContratoMockAdapter
        implements PedidoContratoPort, RecepcionPedidoPort, PlaneacionPedidoPort {

    private final Map<Long, PedidoContrato> pedidos = new ConcurrentHashMap<>();
    private final Map<Long, Map<Long, Integer>> recibidasPorPedido = new ConcurrentHashMap<>();

    public PedidoContratoMockAdapter() {
        reiniciarDatosSemilla();
    }

    /**
     * Solo para pruebas / demo: restablece el estado en memoria a los datos semilla.
     * Los tests lo llaman en su {@code @BeforeEach} para no depender del orden.
     */
    public final void reiniciarDatosSemilla() {
        pedidos.clear();
        recibidasPorPedido.clear();
        for (PedidoContrato p : DatosSemillaMock.pedidos()) {
            pedidos.put(p.id(), p);
        }
    }

    // --- PedidoContratoPort (consulta) -------------------------------------------------

    @Override
    public Optional<PedidoContrato> obtenerPedido(Long pedidoId) {
        return Optional.ofNullable(pedidos.get(pedidoId));
    }

    @Override
    public List<PedidoContrato> listarPedidosPorEstado(EstadoPedido... estados) {
        Set<EstadoPedido> filtro = (estados == null || estados.length == 0)
                ? EnumSet.noneOf(EstadoPedido.class)
                : EnumSet.copyOf(Arrays.asList(estados));
        return pedidos.values().stream()
                .filter(p -> filtro.contains(p.estado()))
                .sorted(Comparator.comparing(PedidoContrato::id))
                .toList();
    }

    // --- RecepcionPedidoPort (escritura HU-15) ---------------------------------------

    @Override
    public synchronized void aplicarResultadoRecepcion(Long pedidoId,
                                                       EstadoPedido nuevoEstado,
                                                       Map<Long, Integer> cantidadesRecibidasPorItem) {
        PedidoContrato actual = exigirPedido(pedidoId);
        // Atomico en la practica: dos puts sobre mapas en memoria bajo el lock del metodo.
        recibidasPorPedido.put(pedidoId, Map.copyOf(cantidadesRecibidasPorItem));
        pedidos.put(pedidoId, new PedidoContrato(
                actual.id(), actual.proveedorId(), nuevoEstado,
                actual.fechaEstimadaEntrega(), actual.lineas()));
    }

    // --- PlaneacionPedidoPort (escritura HU-14) ------------------------------------

    @Override
    public synchronized void registrarFechaEstimadaEntrega(Long pedidoId, LocalDate fecha) {
        PedidoContrato actual = exigirPedido(pedidoId);
        pedidos.put(pedidoId, new PedidoContrato(
                actual.id(), actual.proveedorId(), actual.estado(),
                fecha, actual.lineas()));
    }

    // --- Ayudas de prueba ----------------------------------------------------------

    /** Solo para pruebas: cantidades recibidas registradas para un pedido. */
    public Map<Long, Integer> cantidadesRecibidas(Long pedidoId) {
        return recibidasPorPedido.getOrDefault(pedidoId, Map.of());
    }

    private PedidoContrato exigirPedido(Long pedidoId) {
        PedidoContrato actual = pedidos.get(pedidoId);
        if (actual == null) {
            throw new IllegalStateException("Pedido " + pedidoId + " no existe en el mock");
        }
        return actual;
    }
}
