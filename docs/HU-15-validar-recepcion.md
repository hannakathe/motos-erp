# HU-15 — Validar recepción (épica Compras · SCRUM-11)

> **Historia:** Como encargado de bodega, quiero validar las unidades recibidas contra la
> orden de compra, para detectar faltantes o errores.
>
> **Criterio de aceptación:** Dado un pedido en estado "Pendiente" que llega a bodega,
> cuando el encargado de bodega registra las unidades recibidas, entonces el sistema las
> compara contra lo solicitado en la orden y señala faltantes o discrepancias, si existen.

Mapea a **RF-3.6 / RF-3.6.1 / RF-3.6.2** de [requisitos/funcionales.md](requisitos/funcionales.md).
RF-3.6.3 (ingreso a inventario) queda fuera de alcance: depende del módulo Inventario, aún sin construir.

## Stack

Java 17 · Spring Boot 3.3.4 (Web + Data JPA + Validation) · Maven · PostgreSQL (runtime) ·
JUnit 5 + Mockito + MockMvc + H2 (pruebas) · JaCoCo (cobertura).

Arquitectura en capas (arc42 [5.2.1](arc-42/05_building_block_view.md#521-vista-de-desarrollo--arquitectura-en-capas)):
API → Dominio → (Persistencia, vía contrato). SRP y DIP: ver arc42
[5.6](arc-42/05_building_block_view.md#56-caja-blanca-del-componente-recepción-de-pedidos-hu-15).

## Estructura del código

```
src/main/java/com/andimotors/
├── AndiMotorsErpApplication.java
└── compras/
    ├── contrato/                      ← CONTRATO TEMPORAL (MOCK). Ver contrato/README-MOCK.md
    │   ├── EstadoPedido.java          ← enum de estados del pedido
    │   ├── PedidoContrato.java        ← orden de compra + ítems (DTO de frontera)
    │   ├── LineaPedidoContrato.java
    │   ├── HistoricoVentasContrato.java
    │   ├── PedidoContratoPort.java    ← puerto: consultar pedidos          (HU-12)
    │   ├── RecepcionPedidoPort.java   ← puerto: persistir recepción        (HU-15/HU-12)
    │   ├── PlaneacionPedidoPort.java  ← puerto: fecha estimada de entrega  (HU-14)
    │   ├── HistoricoVentasPort.java   ← puerto: histórico de ventas        (HU-13)
    │   └── mock/                      ← implementación en memoria (@Profile "mock")
    └── recepcion/                     ← HU-15
        ├── api/       RecepcionController, RecepcionApiMapper, RecepcionExceptionHandler, dto/
        └── dominio/   ValidarRecepcionService, ComparadorRecepcion, ResultadoRecepcion,
                       TipoDiferencia, ResultadoGlobalRecepcion, ... , excepcion/
```

## Construir, ejecutar y probar

Requiere **JDK 17+** y **Maven 3.9+** (o `mvn -N wrapper:wrapper` para generar `./mvnw`).

```bash
# compilar
mvn -q compile

# ejecutar toda la batería de pruebas + reporte de cobertura
mvn -q test
# reporte JaCoCo -> target/site/jacoco/index.html

# levantar la API con el contrato mock en memoria (sin BD)
mvn spring-boot:run
# -> http://localhost:8080

# levantar contra PostgreSQL real (cuando exista la persistencia de HU-12)
mvn spring-boot:run -Dspring-boot.run.profiles=jpa
```

## Endpoints

Base: `/api/compras/recepciones`

| Método | Ruta | Descripción |
|---|---|---|
| `GET` | `/pedidos` | Pedidos por recibir (estado PENDIENTE o APROBADO) — pantalla de selección. |
| `GET` | `/pedidos/{pedidoId}` | Detalle del pedido con sus líneas, para armar el formulario de recepción. |
| `POST`| `` (raíz) | Registra las cantidades recibidas y devuelve la comparación contra la orden. |

### Ejemplo — registrar recepción

```bash
curl -X POST http://localhost:8080/api/compras/recepciones \
  -H "Content-Type: application/json" \
  -d '{
    "pedidoId": 2,
    "lineas": [
      { "itemId": 21, "cantidadRecibida": 6 },
      { "itemId": 22, "cantidadRecibida": 5 }
    ]
  }'
```

Respuesta `200 OK` (el pedido 2 pedía 8 del ítem 21):

```json
{
  "pedidoId": 2,
  "estadoAnterior": "APROBADO",
  "estadoNuevo": "RECIBIDO_CON_DIFERENCIAS",
  "resultadoGlobal": "CON_DIFERENCIAS",
  "lineas": [
    { "itemId": 21, "productoId": 500, "producto": "Casco MX Pro",
      "cantidadSolicitada": 8, "cantidadRecibida": 6, "diferencia": -2, "tipo": "FALTANTE" },
    { "itemId": 22, "productoId": 502, "producto": "Kit de arrastre 428H",
      "cantidadSolicitada": 5, "cantidadRecibida": 5, "diferencia": 0, "tipo": "COMPLETO" }
  ]
}
```

### Códigos de error

| HTTP | `error` | Cuándo |
|---|---|---|
| 400 | `RECEPCION_INVALIDA` | cantidad recibida negativa, línea ajena al pedido, registro que no cubre cada línea exactamente una vez |
| 400 | `PAYLOAD_INVALIDO` | JSON mal formado o sin líneas |
| 404 | `PEDIDO_NO_ENCONTRADO` | el `pedidoId` no existe |
| 409 | `ESTADO_NO_RECEPCIONABLE` | el pedido no está PENDIENTE ni APROBADO |

## Contrato temporal HU-12/13/14

HU-15 se construyó antes que el flujo previo del pedido. Programa contra las interfaces de
`com.andimotors.compras.contrato`, hoy resueltas por un mock en memoria. Todo lo que hay que
revisar/reemplazar al integrar HU-12/13/14 está en:

- `src/main/java/com/andimotors/compras/contrato/README-MOCK.md`
- Checklist de integración: arc42 [5.7.2](arc-42/05_building_block_view.md#572-checklist-de-integración-tarea-c--al-mergear-hu-121314-reales)

## Pruebas — inventario y plan

| Archivo | Tipo | Cubre |
|---|---|---|
| `ComparadorRecepcionTest` | Unitaria (pura) | Lógica de comparación: completo / faltante / sobrante / mezcla / cero recibido; resultado global y estado resultante. |
| `ValidarRecepcionServiceTest` | Unitaria (Mockito) | Orquestación y validaciones: camino feliz completo y con faltante; pedido inexistente; estado no recepcionable (RECIBIDO/CANCELADO); cantidad negativa; línea ajena; registro incompleto / repetido / vacío; verifica que no se persiste ante error. |
| `RecepcionControllerIT` | Integración (`@SpringBootTest` + MockMvc, contra el mock) | Los 3 endpoints de punta a punta: lista de pendientes, detalle, 404; recepción completa (200 + sale de pendientes); recepción con faltante (200 + línea FALTANTE); 404 / 400 / 409. |
| `PlanPruebasSprint1Test` | Plan de pruebas del Sprint | PT-12.1, PT-12.2, PT-13.1, PT-13.2, PT-14.1 (contra el contrato mock — **re-ejecutar con HU-12/13/14 real**), PT-15.1, PT-15.2 (definitivos). |

### Ejecución del plan de pruebas (`docs/sprint/sprint-1-planning.md` §3)

| ID | HU | Estado esperado | Contra qué corre hoy |
|---|---|---|---|
| PT-12.1 | 12 | Pedido semilla en estado PENDIENTE con ítems y cantidades > 0. | Contrato mock — **pendiente** re-ejecutar creando el pedido con la API real de HU-12. |
| PT-12.2 | 12 | Crear pedido sin líneas es rechazado (invariante `PedidoContrato`). | Contrato mock — **pendiente** re-ejecutar con `POST /api/pedidos` real. |
| PT-13.1 | 13 | Producto con ventas → `unidadesVendidas > 0`. | Mock de EIS — **pendiente** re-ejecutar con EIS/HU-13 real. |
| PT-13.2 | 13 | Producto sin ventas → `unidadesVendidas == 0`, sin error. | Mock de EIS — **pendiente** re-ejecutar con EIS/HU-13 real. |
| PT-14.1 | 14 | Fecha estimada queda asociada y visible en el detalle. | Contrato mock — **pendiente** re-ejecutar con la API real de HU-14. |
| PT-15.1 | 15 | Recepción con cantidades exactas → pedido `RECIBIDO`, sin discrepancias. | **Definitivo** — código real de HU-15. |
| PT-15.2 | 15 | Recepción con faltante → `RECIBIDO_CON_DIFERENCIAS` y se señala ítem + cantidad faltante. | **Definitivo** — código real de HU-15. |

### Resultado de la ejecución

> ⚠️ **Pendiente de ejecutar en una máquina con JDK.** El entorno donde se generó este código
> no tiene JVM/Maven instalados, por lo que las pruebas **no se ejecutaron aquí**. Correr
> `mvn test` en local (o dejar que lo haga el CI del PR) y pegar en esta sección la salida de
> Surefire y el `%` de cobertura de `target/site/jacoco/index.html`.
>
> Conteo de casos escritos: **6 + 11 + 10 + 7 = 34** métodos `@Test`.
> Cobertura esperada del paquete `com.andimotors.compras.recepcion`: alta (todas las ramas de
> `ComparadorRecepcion` y de las validaciones de `ValidarRecepcionService` tienen caso; el
> controller y el mapper se ejercitan por `RecepcionControllerIT`).
