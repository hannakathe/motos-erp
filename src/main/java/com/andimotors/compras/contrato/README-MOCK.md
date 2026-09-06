# Contrato temporal Compras &rarr; Recepcion  ·  `com.andimotors.compras.contrato`

> ⚠️ **MOCK — reemplazar cuando HU-12, HU-13 y HU-14 esten integradas.**
> Este paquete es la frontera que HU-15 ("Validar recepcion") necesita para
> compilar y probarse **sin** el codigo del flujo previo del pedido, que todavia
> no existe. Cuando ese codigo llegue, se reemplaza el subpaquete `mock/` por
> adaptadores reales y **este contrato es el unico punto de edicion**.

## Por que existe

En el Sprint 1 (epica Compras / SCRUM-11), HU-12/13/14 (generar pedido, histórico
de ventas, fecha estimada de entrega) y HU-15 (validar recepcion) se desarrollan en
paralelo. HU-15 depende de que exista un "Pedido con items y estado", pero ese
modelo lo construye HU-12. Para no bloquearse, HU-15 programa **contra estas
interfaces** (DIP) y hoy corren con un adaptador en memoria.

## Que expone (lo que el codigo de HU-12/13/14 debera cumplir)

### Modelo (DTOs de frontera, no entidades de persistencia)

| Tipo | Campos | Origen esperado en HU-12 real |
|---|---|---|
| `EstadoPedido` (enum) | `PENDIENTE, APROBADO, RECIBIDO, RECIBIDO_CON_DIFERENCIAS, CANCELADO` | El codigo transitorio de HU-12 usaba `pendiente/aprobado/recibido/cancelado`. **HU-15 agrega `RECIBIDO_CON_DIFERENCIAS`** — HU-12 debe adoptarlo. |
| `PedidoContrato` | `id, proveedorId, estado, fechaEstimadaEntrega, lineas[]` | entidad `Pedido` (`_id`, `proveedor`, `estado`, campo nuevo de HU-14, `detalles[]`) |
| `LineaPedidoContrato` | `itemId, productoId, productoNombre, cantidadSolicitada` | subdocumento `DetallePedido` (`_id`, `producto`, `producto.nombre`, `cantidad`) |
| `HistoricoVentasContrato` | `productoId, desde, hasta, unidadesVendidas` | resultado de `EIS.consultarHistoricoVentas()` (HU-13) |

### Puertos (interfaces)

| Puerto | Metodo | Lo consume | Lo implementa (real) |
|---|---|---|---|
| `PedidoContratoPort` | `obtenerPedido(id)`, `listarPedidosPorEstado(...)` | HU-15 (lectura) y la pantalla de seleccion | adaptador JPA sobre `PedidoRepository` (HU-12) |
| `RecepcionPedidoPort` | `aplicarResultadoRecepcion(id, nuevoEstado, cantidadesRecibidas)` | HU-15 (escritura) | HU-12/HU-15: update de `estado` + persistir `cantidadRecibida` por linea, **atomico** (RNF-6) |
| `PlaneacionPedidoPort` | `registrarFechaEstimadaEntrega(id, fecha)` | HU-14 (aqui solo para poder correr PT-14.1) | HU-14 real |
| `HistoricoVentasPort` | `consultarHistoricoVentas(productoId, desde, hasta)` | HU-13 (aqui solo para PT-13.1/PT-13.2) | modulo EIS (HU-13) |

## Implementacion actual (a reemplazar)

`com.andimotors.compras.contrato.mock/`
- `PedidoContratoMockAdapter` — implementa los 3 puertos de pedido en memoria; `@Profile("mock")`.
- `HistoricoVentasMockAdapter` — implementa `HistoricoVentasPort` en memoria; `@Profile("mock")`.
- `DatosSemillaMock` — 4 pedidos y ventas sembradas para la demo y las pruebas.

## Como se reemplaza (Tarea C — integracion HU-12&rarr;HU-15)

1. HU-12/13/14 mergeadas en `develop`.
2. Crear `com.andimotors.compras.contrato.jpa` con adaptadores reales (`@Profile("jpa")`)
   que implementen los mismos puertos sobre las entidades/repositorios de HU-12.
3. Ajustar los **mapeos de nombres/tipos** (ver checklist en
   `docs/arc-42/05_building_block_view.md` &sect; 5.7).
4. Arrancar con `--spring.profiles.active=jpa` y volver a ejecutar las pruebas
   (unitarias intactas; integracion ahora contra BD real).
5. Borrar el subpaquete `mock/` y este README.

**Ningun archivo de `com.andimotors.compras.recepcion` deberia cambiar en ese paso.**
Si cambia, la abstraccion estaba mal puesta.
