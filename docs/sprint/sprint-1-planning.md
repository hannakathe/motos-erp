# Sprint 1 Planning — Épica Compras — AndiMotors ERP

Planificación del Sprint 1 del ERP de Andina Motors, enfocado en la épica **Compras**, con base en el [Product Backlog priorizado](../product-backlog.md) y en los [requisitos funcionales](../requisitos/funcionales.md) (Rama 3 — Gestionar Compras).

## 1. Objetivo del Sprint (Sprint Goal)

Permitir que el encargado de compras genere pedidos a fábrica/distribuidor apoyado en el histórico de ventas, y que bodega valide la recepción de esos pedidos contra lo solicitado, cerrando el ciclo completo de reposición de inventario (RF-3).

## 2. Sprint Backlog — Épica Compras

| ID | Historia de Usuario | SP | MoSCoW | Criterio de aceptación (Dado-Cuando-Entonces) |
|---|---|---|---|---|
| HU-12 | Como encargado de compras, quiero generar un pedido a fábrica/distribuidor seleccionando modelos y cantidades, para reponer el inventario. | 5 | Must | **Dado** que el encargado de compras está autenticado, **cuando** selecciona uno o más modelos con sus cantidades y confirma el pedido, **entonces** el sistema crea la orden de compra en estado "Pendiente" y queda disponible para su recepción. |
| HU-13 | Como encargado de compras, quiero consultar el histórico de ventas por modelo, para decidir cuánto pedir en el próximo pedido. | 3 | Should | **Dado** que el encargado de compras está creando un pedido, **cuando** consulta el histórico de ventas de un modelo (módulo EIS), **entonces** el sistema muestra las unidades vendidas de ese modelo en el período consultado. |
| HU-14 | Como encargado de compras, quiero registrar la fecha estimada de entrega de un pedido, para planear el inventario. | 2 | Could | **Dado** un pedido ya generado, **cuando** el encargado de compras registra una fecha estimada de entrega, **entonces** el sistema la asocia al pedido y la muestra en el detalle de la orden. |
| HU-15 | Como encargado de bodega, quiero validar las unidades recibidas contra la orden de compra, para detectar faltantes o errores. | 3 | Must | **Dado** un pedido en estado "Pendiente" que llega a bodega, **cuando** el encargado de bodega registra las unidades recibidas, **entonces** el sistema las compara contra lo solicitado en la orden y señala faltantes o discrepancias, si existen. |

**Capacidad del Sprint:** 13 Story Points (suma de HU-12, HU-13, HU-14, HU-15).

> Nota de alcance: HU-13 depende de que el módulo EIS exponga `consultarHistoricoVentas()` (ver [diagrama de componentes](../diagramas/plantuml/diagrama_componentes.plantuml) y [paquete Compras](../diagramas/plantuml/diagrama_paquetes_compras.plantuml), donde `PedidosFabrica ..> EIS`).

## 3. Plan de pruebas

| ID | Historia | Caso de prueba | Resultado esperado |
|---|---|---|---|
| PT-12.1 | HU-12 | Generar pedido con al menos un modelo y cantidad válida (> 0). | El pedido se crea en estado "Pendiente" con los modelos y cantidades indicados. |
| PT-12.2 | HU-12 | Intentar generar un pedido sin seleccionar ningún modelo. | El sistema rechaza la creación y notifica que debe seleccionarse al menos un modelo. |
| PT-13.1 | HU-13 | Consultar histórico de ventas de un modelo con ventas registradas. | Se muestran las unidades vendidas de ese modelo en el período consultado. |
| PT-13.2 | HU-13 | Consultar histórico de ventas de un modelo sin ventas registradas. | Se muestra el histórico vacío (cero unidades), sin error. |
| PT-14.1 | HU-14 | Registrar fecha estimada de entrega en un pedido existente. | La fecha queda asociada y visible en el detalle del pedido. |
| PT-15.1 | HU-15 | Validar recepción cuando las unidades recibidas coinciden con la orden. | El pedido se marca como recibido correctamente, sin discrepancias. |
| PT-15.2 | HU-15 | Validar recepción cuando faltan unidades respecto a la orden. | El sistema señala el faltante y el modelo/cantidad afectado. |

## 4. Definition of Done (DoD)

Una historia de usuario de este Sprint se considera terminada cuando:

- Cumple todos sus criterios de aceptación (Dado-Cuando-Entonces).
- Los casos de prueba definidos en la sección 3 pasan correctamente.
- El código fue revisado por al menos otro integrante del equipo (revisión de pares).
- No introduce errores de regresión en las historias ya entregadas de Inventario y Facturación (validado por su dependencia con `verificarDisponibilidad()` / `consultarHistoricoVentas()`).
- La funcionalidad quedó reflejada en el tablero de Jira del proyecto, con su estado actualizado.

## 5. Roles y responsabilidades

Roles del equipo Scrum para este Sprint, sobre la base del reparto de tareas del equipo (Hanna, Ingrid y Marlon) descrito en [docs/alcance.md](../alcance.md):

| Rol | Responsabilidad en el Sprint |
|---|---|
| Product Owner | Prioriza el backlog de la épica Compras, aclara criterios de aceptación y acepta/rechaza los incrementos entregados. |
| Scrum Master | Facilita las ceremonias del Sprint (planning, daily, review, retro) y remueve impedimentos del equipo. |
| Equipo de desarrollo | Implementa las historias de usuario del Sprint Backlog, escribe y ejecuta las pruebas, y participa en la revisión de pares. |

## 6. Cronograma del Sprint 1

| Ceremonia / Actividad | Momento |
|---|---|
| Sprint Planning | Día 1 — definición del Sprint Goal y del Sprint Backlog (esta tabla, sección 2). |
| Desarrollo (HU-12, HU-13) | Semana 1 — generación de pedidos y consulta de histórico de ventas. |
| Desarrollo (HU-14, HU-15) | Semana 2 — fecha estimada de entrega y validación de recepción contra la orden. |
| Daily standups | Diarios, durante toda la duración del Sprint. |
| Sprint Review | Último día — demo del flujo completo de Compras (generar pedido → recibir pedido) al Product Owner. |
| Sprint Retrospective | Último día, después del Review — lecciones aprendidas y ajustes para el siguiente Sprint. |
