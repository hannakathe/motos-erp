[← Volver al índice](arc42-template-ES.md)

# 6. Vista de Ejecución

Los escenarios de esta sección se derivan de las llamadas documentadas en el [diagrama de estructura compuesta](<../diagramas/plantuml/diagrama estructura compuesta>) (caja blanca de Facturación) y el [diagrama de componentes](../diagramas/plantuml/diagrama_componentes.plantuml). No existe un diagrama de secuencia dedicado en el repositorio; esta vista narra el orden de llamadas tal como está modelado en esos dos diagramas.

## 6.1 Escenario: Facturar venta de moto (RF-2.1)

![Estructura compuesta — Facturación](<../diagramas/img/Diagrama estructura compuesta.png>)

1. Llega la solicitud de facturación (venta) al componente **Facturación**.
2. `GeneradorComprobante` invoca a `ConectorInventario.verificarDisponibilidad()`, que consulta al componente **Inventario** para confirmar que la unidad sigue disponible (RF-2.1.1).
3. `GeneradorComprobante` usa `ValidadorComision`, que invoca a `ConectorEmpleados.consultarComision()`, consultando al componente **Empleados** el porcentaje de comisión del vendedor (RF-2.3.1).
4. `GeneradorComprobante` calcula impuestos y accesorios (RF-2.1.2) y genera el comprobante en PDF (RF-2.1.3).

## 6.2 Escenario: Facturar orden de servicio de taller (RF-2.2)

1. Llega la solicitud de facturación (orden de taller) al componente **Facturación**.
2. Se registran los repuestos usados (RF-2.2.1); `ConectorInventario` invoca `descontarRepuesto()` sobre el componente **Inventario**, descontando el stock (RF-2.2.1.1).
3. Se registra la mano de obra del mecánico asignado (RF-2.2.2).

## 6.3 Escenario: Asignar mecánico a una orden de taller (RF-4.2)

Del [diagrama de componentes](../diagramas/plantuml/diagrama_componentes.plantuml): **Empleados** consume la operación `asignarMecanico()` expuesta por **Facturación** (sobre la entidad `OrdenTaller`, ver [Vista de Bloques](05_building_block_view.md#54-vista-lógica-diagrama-de-clases-y-objetos)).

1. Empleados verifica disponibilidad de taller (RF-4.2.1, paquete `AsignacionTaller`).
2. Empleados invoca `asignarMecanico()` sobre Facturación para vincular el mecánico disponible a la `OrdenTaller`.

---
[← Anterior: Vista de Bloques](05_building_block_view.md) · [Volver al índice](arc42-template-ES.md) · [Siguiente: Vista de Despliegue →](07_deployment_view.md)
