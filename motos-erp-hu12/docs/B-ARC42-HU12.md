# Documentación arc42 — HU-12: Generar pedido

> Insertar este contenido en las secciones correspondientes del documento arc42 del proyecto (típicamente secciones 5 "Building Block View", 6 "Runtime View" y 9 "Architecture Decisions").

## 5.x Bloque de construcción — Módulo Pedidos

**Responsabilidad:** permitir la generación de pedidos de compra a un proveedor,
compuestos por uno o más detalles (producto + cantidad + precio unitario).

**Componentes:**

| Componente | Archivo | Responsabilidad |
|---|---|---|
| Modelo `Pedido` | `src/models/Pedido.js` | Entidad principal; referencia a `Proveedor`; contiene el arreglo embebido de `detalles`; calcula el total. |
| Esquema `DetallePedido` | `src/models/DetallePedido.js` | Sub-entidad embebida; referencia a `Producto`; calcula su propio subtotal. |
| `pedido.service.js` | `src/services/pedido.service.js` | Lógica de negocio: valida proveedor/productos, arma detalles, calcula total, persiste. |
| `pedido.controller.js` | `src/controllers/pedido.controller.js` | Adaptador HTTP: traduce request/response REST. |
| `pedido.routes.js` | `src/routes/pedido.routes.js` | Define los endpoints `/api/pedidos`. |
| `pedido.validator.js` | `src/validators/pedido.validator.js` | Validación de forma del payload de entrada (express-validator). |

## 6.x Vista de tiempo de ejecución — Generar pedido

1. El cliente envía `POST /api/pedidos` con `{ proveedor, detalles[], observaciones? }`.
2. `pedido.validator` valida la forma de los datos (tipos, obligatoriedad, ids válidos).
3. `pedido.controller.crearPedido` invoca `pedido.service.generarPedido`.
4. El servicio:
   - Verifica que el proveedor exista y esté activo.
   - Verifica que cada producto exista, esté activo y pertenezca al proveedor.
   - Calcula `subtotal` por detalle y `total` del pedido.
   - Persiste el `Pedido` en MongoDB.
5. Se responde `201 Created` con el pedido creado y sus referencias pobladas (`populate`).

### Reglas de negocio / validaciones aplicadas

- El proveedor debe existir y estar activo.
- Debe existir al menos un detalle.
- Cada producto del detalle debe existir, estar activo y pertenecer al proveedor del pedido.
- La cantidad de cada detalle debe ser un entero mayor a 0.
- El precio unitario no puede ser negativo.
- El `total` y cada `subtotal` se calculan en el backend (nunca se confía en un total enviado por el cliente).
- Estado inicial del pedido: `pendiente` (enum: `pendiente`, `aprobado`, `recibido`, `cancelado`).

## 9.x Decisión de arquitectura — Detalle de pedido como sub-documento embebido

**Contexto:** HU-12 requiere un modelo de "detalle de pedido" relacionado con `Pedido` y `Producto`.

**Decisión:** modelar `DetallePedido` como un sub-esquema de Mongoose embebido dentro del arreglo `detalles` de `Pedido`, en lugar de una colección independiente.

**Justificación:**
- Los detalles de un pedido siempre se leen y escriben junto con su pedido (no tienen ciclo de vida propio).
- Evita el costo de una consulta `$lookup`/populate adicional para el caso de uso principal (ver un pedido completo).
- Sigue el patrón recomendado de modelado en MongoDB para relaciones 1-a-muchos "de composición".

**Consecuencias:**
- Cada `DetallePedido` sí referencia (`ObjectId`) a `Producto`, para mantener consistencia de precios/stock.
- Si en el futuro se requiere consultar detalles de forma independiente entre pedidos (p. ej. reportes de productos más pedidos), se puede migrar a una colección independiente con índice por `producto` sin romper la API pública del módulo.

## Endpoints (API)

| Método | Ruta | Descripción |
|---|---|---|
| `POST` | `/api/pedidos` | Genera un nuevo pedido. |
| `GET` | `/api/pedidos` | Lista pedidos (filtros opcionales `estado`, `proveedor`). |
| `GET` | `/api/pedidos/:id` | Obtiene el detalle de un pedido. |

## Pruebas

- **Unitarias** (`tests/unit/pedido.service.test.js`): cubren la lógica de negocio del servicio con mocks de Mongoose (proveedor inexistente/inactivo, producto inexistente/inactivo/de otro proveedor, cálculo de total).
- **Integración/API** (`tests/integration/pedido.api.test.js`): levantan una base MongoDB en memoria (`mongodb-memory-server`) y prueban el endpoint `POST /api/pedidos` de punta a punta, además de validaciones de entrada.
