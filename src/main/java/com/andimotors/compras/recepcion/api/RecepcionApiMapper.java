package com.andimotors.compras.recepcion.api;

import com.andimotors.compras.contrato.PedidoContrato;
import com.andimotors.compras.recepcion.api.dto.LineaPorRecibirResponse;
import com.andimotors.compras.recepcion.api.dto.LineaResultadoResponse;
import com.andimotors.compras.recepcion.api.dto.PedidoParaRecibirResponse;
import com.andimotors.compras.recepcion.api.dto.PedidoPorRecibirResponse;
import com.andimotors.compras.recepcion.api.dto.RecepcionResultadoResponse;
import com.andimotors.compras.recepcion.api.dto.RegistrarRecepcionRequest;
import com.andimotors.compras.recepcion.dominio.LineaRecibida;
import com.andimotors.compras.recepcion.dominio.RegistroRecepcion;
import com.andimotors.compras.recepcion.dominio.ResultadoRecepcion;

import java.util.List;

/**
 * Traduce entre los DTOs de la API y los objetos de dominio de HU-15. Mantiene el
 * controlador delgado (SRP): el controlador solo enruta, este mapper solo convierte.
 */
final class RecepcionApiMapper {

    private RecepcionApiMapper() {
    }

    static RegistroRecepcion aDominio(RegistrarRecepcionRequest request) {
        List<LineaRecibida> lineas = request.lineas().stream()
                .map(l -> new LineaRecibida(l.itemId(), l.cantidadRecibida()))
                .toList();
        return new RegistroRecepcion(request.pedidoId(), lineas);
    }

    static RecepcionResultadoResponse aRespuesta(ResultadoRecepcion resultado) {
        List<LineaResultadoResponse> lineas = resultado.lineas().stream()
                .map(l -> new LineaResultadoResponse(
                        l.itemId(),
                        l.productoId(),
                        l.productoNombre(),
                        l.cantidadSolicitada(),
                        l.cantidadRecibida(),
                        l.diferencia(),
                        l.tipo().name()))
                .toList();
        return new RecepcionResultadoResponse(
                resultado.pedidoId(),
                resultado.estadoAnterior().name(),
                resultado.estadoNuevo().name(),
                resultado.resultadoGlobal().name(),
                lineas);
    }

    static PedidoPorRecibirResponse aResumen(PedidoContrato pedido) {
        return new PedidoPorRecibirResponse(
                pedido.id(),
                pedido.proveedorId(),
                pedido.estado().name(),
                pedido.fechaEstimadaEntrega() == null ? null : pedido.fechaEstimadaEntrega().toString(),
                pedido.lineas().size());
    }

    static PedidoParaRecibirResponse aDetalle(PedidoContrato pedido) {
        List<LineaPorRecibirResponse> lineas = pedido.lineas().stream()
                .map(l -> new LineaPorRecibirResponse(
                        l.itemId(), l.productoId(), l.productoNombre(), l.cantidadSolicitada()))
                .toList();
        return new PedidoParaRecibirResponse(pedido.id(), pedido.estado().name(), lineas);
    }
}
