[← Volver al índice](arc42-template-ES.md)

# 5. Vista de Bloques

## 5.0 Contexto y contenedores (C4 Nivel 1 y 2)

Agregado por el equipo después de los 11 diagramas UML originales, como caja negra del sistema completo:

![Diagrama C4 - Contexto](../diagramas/img/diagrama_c4_contexto.png)

![Diagrama C4 - Contenedores](../diagramas/img/diagrama_c4_contenedores.png)

El diagrama de contenedores (C4 Nivel 2) identifica 3 contenedores de caja negra: `Aplicación Web (Cliente-Sucursal)` (React), `API Backend` (Spring Boot) y `Base de Datos` (PostgreSQL) — equivalentes a los 3 nodos del [diagrama de despliegue](../diagramas/plantuml/diagrama_despliegue.plantuml) original, más la relación explícita `API Backend → Fábrica/Distribuidor` ("Envía pedidos de compra a"), no presente en el diagrama de componentes original. Ver detalle completo en [03.1](03_system_scope_and_context.md#31-contexto-de-negocio) y [03.2](03_system_scope_and_context.md#32-contexto-técnico).

## 5.1 Sistema general de caja blanca (Nivel 1) — Diagrama de Componentes

![Diagrama de Componentes](../diagramas/img/diagrama_componentes.png)

**Motivación**: AndiMotors ERP está compuesto por 6 componentes de negocio (uno por módulo), documentados en [diagrama_componentes.plantuml](../diagramas/plantuml/diagrama_componentes.plantuml).

**Bloques de construcción contenidos**: Inventario, Facturación, Compras, Empleados, EIS, ActivosFijos.

**Interfases importantes**:

| Componente origen | Componente destino | Operación |
|---|---|---|
| Facturación | Inventario | `verificarDisponibilidad()` |
| Facturación | Inventario | `descontarRepuesto()` |
| Facturación | Empleados | `consultarComision()` |
| Compras | EIS | `consultarHistoricoVentas()` |
| Empleados | Facturación | `asignarMecanico()` |

### Inventario

- **Propósito/Responsabilidad**: control de stock de motos y repuestos, disponibilidad por sucursal y costos (RF-1).
- **Interfase(s)**: expone `verificarDisponibilidad()` y `descontarRepuesto()`, consumidas por Facturación.

### Facturación

- **Propósito/Responsabilidad**: venta de motos, órdenes de servicio de taller y cálculo de comisiones (RF-2).
- **Interfase(s)**: consume `verificarDisponibilidad()`/`descontarRepuesto()` de Inventario y `consultarComision()` de Empleados; expone `asignarMecanico()`, consumida por Empleados.

### Compras

- **Propósito/Responsabilidad**: pedidos a fábrica/distribuidor y recepción de mercancía (RF-3).
- **Interfase(s)**: consume `consultarHistoricoVentas()` de EIS.

### Empleados

- **Propósito/Responsabilidad**: registro de vendedores y mecánicos, asignación de órdenes de taller (RF-4).
- **Interfase(s)**: expone `consultarComision()`, consumida por Facturación; consume `asignarMecanico()` de Facturación.

### EIS

- **Propósito/Responsabilidad**: reportes e indicadores gerenciales (histórico de ventas).
- **Interfase(s)**: expone `consultarHistoricoVentas()`, consumida por Compras.

### ActivosFijos

- **Propósito/Responsabilidad**: registro de activos y cálculo de depreciación.
- **Interfase(s)**: [PENDIENTE: el diagrama de componentes no modela dependencias entrantes/salientes de ActivosFijos con otros módulos. Los diagramas agregados posteriormente (C4, Vista 4+1) tampoco lo cubren — ActivosFijos no aparece en la Vista Lógica, la Vista de Desarrollo, la Vista Física ni la Vista de Escenarios. Sigue siendo el módulo con menos evidencia arquitectónica de todo el proyecto (ver [11. Riesgos — R-05](11_risks_and_technical_debt.md#r-05--componente-activosfijos-sin-interfaces-con-otros-componentes)).]

## 5.2 Caja blanca por componente (Nivel 2) — Diagramas de Paquetes

Cada módulo se descompone en paquetes con sus clases (casos de uso), documentados en `docs/diagramas/plantuml/diagrama_paquetes_*.plantuml`:

### Inventario (Stock/Costos)

![Paquetes — Inventario](../diagramas/img/diagrama_paquetes_inventario.png)

- **GestionMotos**: `RegistrarMotoNueva`, `ConsultarDisponibilidad`.
- **GestionRepuestos**: `RegistrarRepuesto`, `DefinirStockMinimo` (depende de GestionMotos).
- **Costos**: `ActualizarCostoInventario` (depende de GestionMotos).

### Facturación

![Paquetes — Facturación](../diagramas/img/diagrama_paquetes_facturacion.png)

- **VentaMotos**: `VerificarDisponibilidad`, `CalcularImpuestos`, `GenerarComprobantePDF` (depende de Inventario/Stock-Costos).
- **OrdenServicio**: `RegistrarRepuestosUsados`, `RegistrarManoDeObra` (depende de Inventario/Stock-Costos).
- **Comisiones**: `CalcularComisionVendedor` (depende de Empleados).

### Compras

![Paquetes — Compras](../diagramas/img/diagrama_paquetes_compras.png)

- **PedidosFabrica**: `SeleccionarModelosYCantidades`, `RegistrarFechaEntrega` (depende de EIS). *(HU-12/13/14 — en construcción; ver [5.7](#57-estado-de-implementación-y-contrato-temporal-hu-12hu-15).)*
- **RecepcionPedidos**: `ValidarUnidadesContraOrden` (depende de PedidosFabrica). **Implementado en HU-15** — el detalle de este bloque está en [5.6](#56-caja-blanca-del-componente-recepción-de-pedidos-hu-15).

### Empleados

![Paquetes — Empleados](../diagramas/img/diagrama_paquetes_empleados.png)

- **GestionPersonal**: `RegistrarVendedor`, `RegistrarMecanico`.
- **AsignacionTaller**: `AsignarOrdenAMecanico`, `ConsultarDisponibilidadTaller` (depende de GestionPersonal).

### EIS

![Paquetes — EIS](../diagramas/img/diagrama_paquetes_eis.png)

- **Reportes**: `ConsultarHistoricoVentas`, `GenerarIndicadores`.

### ActivosFijos

![Paquetes — ActivosFijos](../diagramas/img/diagrama_paquetes_activosfijos.png)

- **ControlActivos**: `RegistrarActivo`, `CalcularDepreciacion`.

## 5.2.1 Vista de Desarrollo — Arquitectura en capas

![Vista de Desarrollo](<../../Vista de Procesos (4+1)/3. vista de desarrollo.jpg>)

Agregada posteriormente por el equipo, descompone el backend en 4 capas horizontales (ver también [4.2](04_solution_strategy.md#42-estilo-arquitectónico)):

| Capa | Bloques |
|---|---|
| Frontend (SPA) | `UI Ventas`, `UI Compras`, `UI Inventario`, `UI Servicio Técnico` |
| API (REST) | `API Ventas`, `API Compras`, `API Inventario`, `API Facturación`, `API Servicio Técnico`, `API Seguridad` |
| Dominio | `Servicio Ventas`, `Servicio Compras`, `Servicio Inventario`, `Servicio Facturación` |
| Persistencia | `Repositorio Ventas`, `Repositorio Compras`, `Repositorio Inventario` |
| Infraestructura | `ORM`, `Cliente DIAN (SOAP/REST)` |

⚠️ Esta vista solo nombra explícitamente los módulos Ventas, Compras, Inventario y Servicio Técnico/Facturación. Empleados, EIS y ActivosFijos no tienen bloques propios dibujados aquí — no se puede confirmar con esta fuente si seguirían la misma arquitectura en capas. La capa de Persistencia tampoco dibuja un `Repositorio Facturación` explícito (a diferencia de `API Facturación` y `Servicio Facturación`, que sí aparecen) — posible omisión del diagrama, no necesariamente una decisión de diseño.

## 5.3 Estructura interna del componente Facturación — Diagrama de Estructura Compuesta

![Estructura compuesta — Facturación](<../diagramas/img/Diagrama estructura compuesta.png>)

Descompone Facturación (fuente: [`diagrama estructura compuesta`](<../diagramas/plantuml/diagrama estructura compuesta>)) en 4 subcomponentes internos:

- **GeneradorComprobante**: usa `ValidadorComision` y consulta `ConectorInventario` (`verificarDisponibilidad()`).
- **ValidadorComision**: consulta `ConectorEmpleados` (`consultarComision()`).
- **ConectorInventario**: conecta con el componente externo Inventario (`verificarDisponibilidad()`, `descontarRepuesto()`).
- **ConectorEmpleados**: conecta con el componente externo Empleados (`consultarComision()`, `asignarMecanico()`).

Entrada externa al componente: solicitud de facturación (venta o taller).

## 5.4 Vista lógica (Diagrama de Clases y Objetos)

[diagrama_clases.plantuml](../diagramas/plantuml/diagrama_clases.plantuml) — [diagrama_objetos.plantuml](../diagramas/plantuml/diagrama_objetos.plantuml)

![Diagrama de Clases](../diagramas/img/diagrama_clases.png)

**Clases principales**: `Cliente`, `Vendedor`, `ItemVendible` (abstracta), `Moto`, `Repuesto`, `Factura`, `DetalleFactura`, `OrdenTaller`, `Mecanico`.

- **Cliente**: persona que adquiere motos/repuestos y/o solicita servicio de taller.
- **Vendedor**: registra ventas (facturas) a nombre de un cliente.
- **ItemVendible**: clase abstracta que agrupa lo que una línea de factura puede referenciar (atributo común `precio`); `Moto` y `Repuesto` heredan de ella. Existe para que `DetalleFactura` tenga una única asociación obligatoria (a `ItemVendible`) en vez de dos asociaciones obligatorias simultáneas a `Moto` y a `Repuesto`, que exigirían ambas presentes en cada línea.
- **Moto**: unidad de inventario vendible, identificada por su chasis.
- **Repuesto**: ítem de inventario usado tanto en ventas directas como en órdenes de taller.
- **Factura**: comprobante de una venta, compuesto por una o más líneas (`DetalleFactura`).
- **DetalleFactura**: línea de factura que referencia un `ItemVendible` (una `Moto` o un `Repuesto`).
- **OrdenTaller**: solicitud de servicio de un cliente, atendida por un `Mecanico` y que puede consumir `Repuesto`.
- **Mecanico**: atiende órdenes de taller y registra diagnósticos.

![Diagrama de Objetos](../diagramas/img/diagrama_objetos.png)

El diagrama de objetos ilustra una instancia concreta de este modelo: un cliente que compra una moto a través de un vendedor, documentado en una factura con su detalle.

> Este modelo de clases cubre el dominio de Facturación (ventas y taller). Los módulos de Compras, Empleados, EIS y ActivosFijos no tienen un diagrama de clases propio en este repositorio — su estructura se documenta únicamente a nivel de paquetes/casos de uso (sección 5.2). [PENDIENTE: modelo de clases detallado para esos módulos, si el proyecto avanza a una futura iteración].

### 5.4.1 Vista Lógica actualizada (agregada posteriormente)

![Vista Lógica](<../../Vista de Procesos (4+1)/1. vista logica.jpg>)

El equipo agregó, después del diagrama de clases original, una segunda Vista Lógica dentro de [`Vista de Procesos (4+1)/`](<../../Vista de Procesos (4+1)>), que **extiende y renombra** parte del modelo de dominio en vez de sustituirlo formalmente. Se documenta aquí como modelo complementario, señalando explícitamente las diferencias:

| Vista Lógica (nueva) | Equivalente en `diagrama_clases.plantuml` (original) | Diferencia |
|---|---|---|
| `Usuario` (`id`, `rol`, `usuario`, `passwordHash`) | — (no existe) | **Nueva**: resuelve la autenticación/autorización pendiente (RNF-2) — ver [4.5](04_solution_strategy.md#45-seguridad) y [AD-09](09_architecture_decisions.md#ad-09--autenticación-y-autorización-basada-en-la-entidad-usuario). |
| `Proveedor` (`id`, `nombre`, `nit`) | — (no existe) | **Nueva**: entidad para el módulo Compras, coincide con el actor "Proveedor"/"Fábrica-Distribuidor" del C4 de contexto. |
| `Compra` (`id`, `fecha`, `total`, `registrarCompra()`) | — (no existe) | **Nueva**: entidad para el módulo Compras; los paquetes originales (`PedidosFabrica`, `RecepcionPedidos`) no tenían clases de dominio propias. |
| `Vehiculo` (`id`, `modelo`, `marca`, `precio`, `estado`) | `Moto` (`chasis`, `marca`, `modelo`, `cilindraje`, `color`, `estado`) | **Renombrada** y simplificada: ya no expone `chasis` (VIN) como atributo explícito ni `cilindraje`/`color`. Esto es una posible regresión frente a RF-1.1.1 ("Capturar número de VIN/chasis... necesario para trazabilidad individual") — ver [11. Riesgos — R-13](11_risks_and_technical_debt.md#r-13--la-vista-lógica-actualizada-simplifica-vehiculo-y-podría-perder-trazabilidad-por-vin). |
| `Venta` (`id`, `fecha`, `total`, `registrarVenta()`) + `Factura` (`id`, `cufe`, `fechaEmision`, `generarFacturaElectronica()`, `enviarADIAN()`) | `Factura` (`numeroFactura`, `fecha`, `subtotal`, `impuestos`, `total`, `generarPDF()`, `calcularTotal()`) | **Reestructurada**: separa la venta (`Venta`) de su comprobante fiscal (`Factura`, ahora con `cufe`/DIAN), donde antes una sola clase `Factura` cubría ambos conceptos. |
| `DetalleVenta` (`id`, `cantidad`, `precioUnitario`) | `DetalleFactura` (`cantidad`, `precioUnitario`, `subtotalLinea`) | **Renombrada**; ya no referencia `ItemVendible` — se asocia directamente a `Vehiculo`, sin el mecanismo de superclase abstracta que unificaba `Moto`/`Repuesto` (ver [AD-04](09_architecture_decisions.md#ad-04--itemvendible-como-superclase-abstracta-de-moto-y-repuesto)). No queda claro cómo `DetalleVenta` referenciaría un `Repuesto` en una orden de servicio con esta nueva estructura. [POR DEFINIR]. |
| `Repuesto` (`id`, `nombre`, `stock`, `stockMinimo`) | `Repuesto` (`codigo`, `nombre`, `cantidadStock`, `descontarStock()`, `reabastecer()`) | Renombra `cantidadStock`→`stock`, agrega `stockMinimo` (ya cubierto por RF-1.2.2 pero no antes modelado como atributo de clase); quita los métodos de comportamiento. |
| `OrdenServicio` (`id`, `descripcion`, `estado`, `agendar()`) | `OrdenTaller` (`idOrden`, `fechaIngreso`, `diagnostico`, `estado`, `asignarMecanico()`, `cerrarOrden()`) | **Renombrada**; simplifica atributos y métodos (pierde `diagnostico`, `asignarMecanico()`, `cerrarOrden()` explícitos). |
| `Cliente` | `Cliente` | Igual, sin cambios relevantes. |

⚠️ Estas dos vistas lógicas **coexisten en el repositorio sin una nota de conciliación** entre ellas. No está claro cuál es la autoritativa (ver [11. Riesgos — R-14](11_risks_and_technical_debt.md#r-14--dos-modelos-de-clases-incompatibles-coexisten-sin-conciliar)). Para el resto de esta documentación, el diagrama de clases original (`diagrama_clases.plantuml`) se sigue tratando como la fuente principal por ser parte de los 11 diagramas UML del taller, mientras que la Vista Lógica nueva se documenta como la evolución más reciente que resuelve la autenticación y agrega Compras/Proveedor/DIAN.

## 5.5 Nivel 3

*(No aplica para el alcance de este taller — se documentaría aquí el desglose interno de cada paquete en clases de implementación, si el proyecto avanzara a ese nivel de detalle.)*

## 5.6 Caja blanca del componente Recepción de Pedidos (HU-15)

Primer componente del proyecto con **código implementado** (Java + Spring Boot). Realiza el
paquete `RecepcionPedidos` de [5.2](#52-caja-blanca-por-componente-nivel-2--diagramas-de-paquetes)
y cubre RF-3.6 / RF-3.6.1 / RF-3.6.2 (validar unidades contra la orden, registrar faltantes y
sobrantes). Código en `src/main/java/com/andimotors/compras/recepcion/`.

### 5.6.1 Propósito y responsabilidad

Dado un pedido que llega a bodega, registrar las unidades recibidas por ítem, compararlas contra
lo solicitado en la orden de compra, clasificar cada línea como **completo / faltante / sobrante**
y actualizar el estado del pedido (`RECIBIDO` o `RECIBIDO_CON_DIFERENCIAS`).

### 5.6.2 Estructura interna (arquitectura en capas — ver [5.2.1](#521-vista-de-desarrollo--arquitectura-en-capas))

| Capa | Elemento | Archivo | Responsabilidad (SRP) |
|---|---|---|---|
| API (REST) | `RecepcionController` | `recepcion/api/RecepcionController.java` | Expone `GET /api/compras/recepciones/pedidos`, `GET .../pedidos/{id}`, `POST .../recepciones`. Solo enruta. |
| API (REST) | `RecepcionApiMapper` | `recepcion/api/RecepcionApiMapper.java` | Traduce DTO ↔ dominio. |
| API (REST) | `RecepcionExceptionHandler` | `recepcion/api/RecepcionExceptionHandler.java` | Traduce excepciones de dominio a HTTP 400/404/409. |
| Dominio | `ValidarRecepcionService` | `recepcion/dominio/ValidarRecepcionService.java` | Orquesta el caso de uso: carga pedido, valida precondiciones, delega comparación, persiste resultado. |
| Dominio | `ComparadorRecepcion` | `recepcion/dominio/ComparadorRecepcion.java` | **Lógica pura** de comparación solicitado vs. recibido. Sin dependencias. |
| Dominio | `ResultadoRecepcion`, `ResultadoComparacion`, `LineaRecepcionResultado`, `TipoDiferencia`, `ResultadoGlobalRecepcion` | `recepcion/dominio/` | Value objects del resultado. |
| Dominio | `excepcion/*` | `recepcion/dominio/excepcion/` | `PedidoNoEncontradoException` (404), `EstadoPedidoNoRecepcionableException` (409), `CantidadRecibidaInvalidaException`, `LineaDesconocidaException`, `RegistroRecepcionIncompletoException` (400). |
| Persistencia | *(ninguna propia)* | — | Se accede al pedido a través de los puertos del contrato (ver 5.7). La persistencia real llega con HU-12. |

### 5.6.3 Interfaces (puertos) — DIP

`ValidarRecepcionService` **no depende** de ninguna implementación concreta: depende de dos
interfaces del paquete `com.andimotors.compras.contrato` e inyecta Spring la implementación:

| Puerto | Operación usada por HU-15 |
|---|---|
| `PedidoContratoPort` | `obtenerPedido(id)`, `listarPedidosPorEstado(PENDIENTE, APROBADO)` |
| `RecepcionPedidoPort` | `aplicarResultadoRecepcion(id, nuevoEstado, cantidadesRecibidas)` (atómico — RNF-6) |

### 5.6.4 Reglas de negocio / validaciones

- El pedido debe existir → si no, 404.
- El pedido debe estar en estado `PENDIENTE` o `APROBADO` → si no, 409.
- Cada cantidad recibida debe ser ≥ 0 → si no, 400.
- El registro debe cubrir **exactamente una vez** cada línea del pedido (sin líneas ajenas, sin líneas repetidas, sin líneas faltantes) → si no, 400.
- `diferencia = recibida − solicitada`: `0` → COMPLETO, `< 0` → FALTANTE, `> 0` → SOBRANTE.
- Resultado global: todas COMPLETO → `RECIBIDO`; alguna con diferencia → `RECIBIDO_CON_DIFERENCIAS`.

### 5.6.5 Fuera del alcance de HU-15 (siguiente iteración)

- RF-3.6.3 "generar el ingreso a inventario de lo recibido conforme" — requiere el módulo Inventario (`RegistrarMovimiento` / entrada por compra), aún sin construir.
- Aprobación de la orden (RF-3.5 / HU-04) — otra HU.

## 5.7 Estado de implementación y contrato temporal (HU-12↔HU-15)

**HU-15 se implementó antes que HU-12/13/14** (desarrollo en paralelo). Para no bloquearse, HU-15
programa contra un **contrato explícito** en `com.andimotors.compras.contrato` (interfaces +
DTOs de frontera), con una implementación mock en memoria (`contrato/mock/`) marcada
`MOCK — reemplazar cuando HU-12/13/14 estén integradas`. Documentación del contrato:
`src/main/java/com/andimotors/compras/contrato/README-MOCK.md`.

### 5.7.1 Qué define el contrato

| Elemento | Tipo | Representa |
|---|---|---|
| `EstadoPedido` | enum | `PENDIENTE, APROBADO, RECIBIDO, RECIBIDO_CON_DIFERENCIAS, CANCELADO`. El valor `RECIBIDO_CON_DIFERENCIAS` lo **agrega HU-15**. |
| `PedidoContrato` | record | Orden de compra: `id, proveedorId, estado, fechaEstimadaEntrega (HU-14), lineas[]`. |
| `LineaPedidoContrato` | record | Ítem: `itemId, productoId, productoNombre, cantidadSolicitada`. |
| `HistoricoVentasContrato` | record | Resultado de `EIS.consultarHistoricoVentas()` (HU-13). |
| `PedidoContratoPort` | interface | Consulta de pedidos. |
| `RecepcionPedidoPort` | interface | Persistir el resultado de la recepción. |
| `PlaneacionPedidoPort` | interface | Registrar fecha estimada de entrega (HU-14). |
| `HistoricoVentasPort` | interface | Consultar histórico de ventas (HU-13). |

### 5.7.2 Checklist de integración (Tarea C — al mergear HU-12/13/14 reales)

Verificar y ajustar, **solo dentro de `com.andimotors.compras.contrato`** (idealmente un nuevo
`contrato/jpa/` con `@Profile("jpa")`), sin tocar `com.andimotors.compras.recepcion`:

- [ ] **Enum de estados**: HU-12 real usa los valores exactos de `EstadoPedido` (el código transitorio de HU-12 usaba minúsculas `pendiente/aprobado/recibido/cancelado`). Añadir `RECIBIDO_CON_DIFERENCIAS` en el modelo real o mapear en el adaptador.
- [ ] **Nombres de campos** `PedidoContrato` ↔ entidad real: `id`↔`_id`/`id`, `proveedorId`↔`proveedor`, `lineas`↔`detalles`, `fechaEstimadaEntrega`↔ (campo nuevo de HU-14, confirmar nombre).
- [ ] **Nombres de campos** `LineaPedidoContrato` ↔ `DetallePedido`/entidad real: `itemId`↔`_id`/`id`, `productoId`↔`producto`, `productoNombre`↔`producto.nombre` (¿viene poblado?), `cantidadSolicitada`↔`cantidad`.
- [ ] **Tipos de id**: el contrato usa `Long`; el código transitorio de HU-12 usaba `ObjectId`/`String`. Definir el tipo real y ajustar el contrato (`Long` → `String`/`UUID`) en un único sitio.
- [ ] **Rutas de endpoints**: si HU-12 expone `GET /api/pedidos/:id`, el adaptador real lo consume; los endpoints de HU-15 (`/api/compras/recepciones/**`) no cambian.
- [ ] **Persistencia de cantidad recibida**: definir dónde vive `cantidadRecibida` por línea (campo nuevo en `DetallePedido`) y que `aplicarResultadoRecepcion` la escriba de forma atómica junto con el estado (RNF-6).
- [ ] **`consultarHistoricoVentas`**: conectar `HistoricoVentasPort` al módulo EIS real (HU-13); confirmar unidad de tiempo (mes) y forma del resultado.
- [ ] **Perfil**: arrancar con `--spring.profiles.active=jpa`; el perfil `mock` desactiva la autoconfiguración de datasource, revisar `application.yml`.
- [ ] **Pruebas**: re-ejecutar PT-12.1, PT-12.2, PT-13.1, PT-13.2, PT-14.1 contra el código real (hoy corren contra el mock; ver `PlanPruebasSprint1Test`). PT-15.1 y PT-15.2 no cambian.
- [ ] Borrar `contrato/mock/` y `README-MOCK.md` cuando el adaptador real esté en verde.

---
[← Anterior: Estrategia de Solución](04_solution_strategy.md) · [Volver al índice](arc42-template-ES.md) · [Siguiente: Vista de Ejecución →](06_runtime_view.md)
