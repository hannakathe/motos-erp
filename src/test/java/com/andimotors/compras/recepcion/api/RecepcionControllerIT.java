package com.andimotors.compras.recepcion.api;

import com.andimotors.compras.contrato.EstadoPedido;
import com.andimotors.compras.contrato.PedidoContratoPort;
import com.andimotors.compras.contrato.mock.PedidoContratoMockAdapter;
import com.andimotors.compras.recepcion.api.dto.LineaRecibidaRequest;
import com.andimotors.compras.recepcion.api.dto.RegistrarRecepcionRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.oneOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Prueba de integracion de HU-15 de punta a punta (HTTP -> servicio -> adaptador
 * mock del contrato). Equivale a la prueba de integracion de HU-12 que usaba
 * mongodb-memory-server; aqui el "doble" es el adaptador en memoria del contrato.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("mock")
@DisplayName("HU-15 API /api/compras/recepciones - integracion contra el mock del contrato")
class RecepcionControllerIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper json;

    @Autowired
    private PedidoContratoPort pedidoPort;

    @Autowired
    private PedidoContratoMockAdapter mockAdapter;

    @BeforeEach
    void reiniciarDatosSemilla() {
        mockAdapter.reiniciarDatosSemilla();
    }

    private String cuerpo(long pedidoId, LineaRecibidaRequest... lineas) throws Exception {
        return json.writeValueAsString(new RegistrarRecepcionRequest(pedidoId, List.of(lineas)));
    }

    // --- Pantalla de seleccion -----------------------------------------------

    @Test
    @DisplayName("GET /pedidos -> solo pedidos PENDIENTE o APROBADO")
    void listaPedidosPorRecibir() throws Exception {
        mvc.perform(get("/api/compras/recepciones/pedidos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[*].pedidoId", containsInAnyOrder(1, 2)))
                .andExpect(jsonPath("$[*].estado", everyItem(oneOf("PENDIENTE", "APROBADO"))));
    }

    @Test
    @DisplayName("GET /pedidos/{id} -> detalle con lineas")
    void detallePedido() throws Exception {
        mvc.perform(get("/api/compras/recepciones/pedidos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pedidoId").value(1))
                .andExpect(jsonPath("$.lineas.length()").value(2))
                .andExpect(jsonPath("$.lineas[0].cantidadSolicitada").value(10));
    }

    @Test
    @DisplayName("GET /pedidos/{id} inexistente -> 404")
    void detallePedidoInexistente() throws Exception {
        mvc.perform(get("/api/compras/recepciones/pedidos/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("PEDIDO_NO_ENCONTRADO"));
    }

    // --- Registrar recepcion -----------------------------------------------

    @Test
    @DisplayName("POST recepcion completa -> 200, RECIBIDO, y el pedido sale de la lista de pendientes")
    void recepcionCompleta() throws Exception {
        mvc.perform(post("/api/compras/recepciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cuerpo(1L,
                                new LineaRecibidaRequest(11L, 10),
                                new LineaRecibidaRequest(12L, 24))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estadoAnterior").value("PENDIENTE"))
                .andExpect(jsonPath("$.estadoNuevo").value("RECIBIDO"))
                .andExpect(jsonPath("$.resultadoGlobal").value("COMPLETA"))
                .andExpect(jsonPath("$.lineas[*].tipo", everyItem(is("COMPLETO"))));

        assertThat(pedidoPort.obtenerPedido(1L)).get()
                .extracting(p -> p.estado()).isEqualTo(EstadoPedido.RECIBIDO);
        mvc.perform(get("/api/compras/recepciones/pedidos"))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].pedidoId").value(2));
    }

    @Test
    @DisplayName("POST recepcion con faltante -> 200, RECIBIDO_CON_DIFERENCIAS y linea FALTANTE senalada")
    void recepcionConFaltante() throws Exception {
        mvc.perform(post("/api/compras/recepciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cuerpo(2L,
                                new LineaRecibidaRequest(21L, 6),   // solicitado 8 -> faltan 2
                                new LineaRecibidaRequest(22L, 5)))) // solicitado 5 -> completo
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estadoNuevo").value("RECIBIDO_CON_DIFERENCIAS"))
                .andExpect(jsonPath("$.resultadoGlobal").value("CON_DIFERENCIAS"))
                // El orden de las lineas replica el del pedido semilla: item 21 y luego item 22.
                .andExpect(jsonPath("$.lineas[0].itemId").value(21))
                .andExpect(jsonPath("$.lineas[0].tipo").value("FALTANTE"))
                .andExpect(jsonPath("$.lineas[0].diferencia").value(-2))
                .andExpect(jsonPath("$.lineas[1].itemId").value(22))
                .andExpect(jsonPath("$.lineas[1].tipo").value("COMPLETO"));
    }

    @Test
    @DisplayName("POST sobre pedido inexistente -> 404")
    void recepcionPedidoInexistente() throws Exception {
        mvc.perform(post("/api/compras/recepciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cuerpo(999L, new LineaRecibidaRequest(1L, 1))))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("PEDIDO_NO_ENCONTRADO"));
    }

    @Test
    @DisplayName("POST con cantidad recibida negativa -> 400")
    void recepcionCantidadNegativa() throws Exception {
        mvc.perform(post("/api/compras/recepciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cuerpo(1L,
                                new LineaRecibidaRequest(11L, -1),
                                new LineaRecibidaRequest(12L, 24))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("RECEPCION_INVALIDA"));
    }

    @Test
    @DisplayName("POST sobre pedido ya RECIBIDO -> 409")
    void recepcionEstadoNoRecepcionable() throws Exception {
        mvc.perform(post("/api/compras/recepciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cuerpo(3L, new LineaRecibidaRequest(31L, 6))))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("ESTADO_NO_RECEPCIONABLE"));
    }

    @Test
    @DisplayName("POST sin lineas -> 400 por validacion de payload")
    void recepcionPayloadInvalido() throws Exception {
        mvc.perform(post("/api/compras/recepciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"pedidoId\": 1, \"lineas\": []}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("PAYLOAD_INVALIDO"));
    }
}
