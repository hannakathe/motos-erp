package com.andimotors.compras.recepcion.api;

import com.andimotors.compras.recepcion.api.dto.PedidoParaRecibirResponse;
import com.andimotors.compras.recepcion.api.dto.PedidoPorRecibirResponse;
import com.andimotors.compras.recepcion.api.dto.RecepcionResultadoResponse;
import com.andimotors.compras.recepcion.api.dto.RegistrarRecepcionRequest;
import com.andimotors.compras.recepcion.dominio.ValidarRecepcionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * API REST de HU-15 "Validar recepcion". Capa de adaptacion HTTP: enruta y mapea,
 * la logica vive en {@link ValidarRecepcionService}.
 */
@RestController
@RequestMapping("/api/compras/recepciones")
public class RecepcionController {

    private final ValidarRecepcionService servicio;

    public RecepcionController(ValidarRecepcionService servicio) {
        this.servicio = servicio;
    }

    /** Pantalla de seleccion: pedidos en estado PENDIENTE o APROBADO. */
    @GetMapping("/pedidos")
    public List<PedidoPorRecibirResponse> pedidosPorRecibir() {
        return servicio.consultarPedidosPorRecibir().stream()
                .map(RecepcionApiMapper::aResumen)
                .toList();
    }

    /** Detalle de un pedido para armar el formulario de recepcion. */
    @GetMapping("/pedidos/{pedidoId}")
    public PedidoParaRecibirResponse pedidoParaRecibir(@PathVariable Long pedidoId) {
        return RecepcionApiMapper.aDetalle(servicio.consultarPedidoParaRecibir(pedidoId));
    }

    /** Registra las cantidades recibidas y devuelve la comparacion contra la orden. */
    @PostMapping
    public RecepcionResultadoResponse registrarRecepcion(@Valid @RequestBody RegistrarRecepcionRequest request) {
        return RecepcionApiMapper.aRespuesta(
                servicio.registrarRecepcion(RecepcionApiMapper.aDominio(request)));
    }
}
