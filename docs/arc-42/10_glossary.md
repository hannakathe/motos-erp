[← Volver al índice](arc42-template-ES.md)

# 10. Glosario

## Términos generales / metodología

| Término | Definición |
|---|---|
| Épica | Historia de usuario de gran tamaño que agrupa funcionalidades de un mismo módulo (ej. "Módulo de Compras"). |
| Historia de Usuario (HU) | Descripción breve de una funcionalidad desde la perspectiva de un rol, con el formato "Como &lt;rol&gt;, quiero &lt;acción&gt;, para que &lt;beneficio&gt;". |
| Criterio de Aceptación | Condición verificable, en formato Dado-Cuando-Entonces, que determina si una historia de usuario está correctamente implementada. |
| C4 Model | Notación de diagramas de arquitectura de software en niveles (Contexto, Contenedores, Componentes, Código). |

## Módulos del sistema

Ver [diagrama de componentes](../diagramas/plantuml/diagrama_componentes.plantuml) y [Vista de Bloques](05_building_block_view.md).

| Término | Definición |
|---|---|
| Inventario (Stock/Costos) | Módulo responsable del control de stock de motos y repuestos, disponibilidad por sucursal y costos. |
| Facturación | Módulo responsable de la venta de motos, órdenes de servicio de taller y cálculo de comisiones. |
| Compras | Módulo responsable de los pedidos a fábrica/distribuidor y la recepción de mercancía. |
| Empleados | Módulo responsable del registro de vendedores y mecánicos, y la asignación de órdenes de taller. |
| EIS | Módulo de reportes e indicadores gerenciales (histórico de ventas). |
| ActivosFijos | Módulo responsable del registro de activos y cálculo de depreciación. |

## Términos de la Vista Lógica (Diagrama de Clases y Objetos)

Dominio de Facturación — ver [5.4 Vista Lógica](05_building_block_view.md#54-vista-lógica-diagrama-de-clases-y-objetos).

| Término | Definición |
|---|---|
| Cliente | Persona que adquiere motos o repuestos, o solicita servicio de taller. |
| Vendedor | Rol responsable de registrar ventas (facturas) a nombre de un cliente. |
| ItemVendible | Clase abstracta que agrupa todo lo que una línea de factura puede referenciar (`Moto` o `Repuesto`), a través de un atributo común `precio`. |
| Moto | Unidad de inventario vendible, identificada por su número de chasis; hereda de `ItemVendible`. |
| Repuesto | Ítem de inventario utilizado en ventas directas o en órdenes de taller; hereda de `ItemVendible`. |
| Factura | Comprobante de una venta, compuesto por una o más líneas de detalle. |
| DetalleFactura | Línea de una factura que referencia un `ItemVendible` (una `Moto` o un `Repuesto`), con cantidad y precio unitario. |
| Orden de Taller | Solicitud de servicio de un cliente, atendida por un mecánico. |
| Mecánico | Rol responsable de atender órdenes de taller y registrar diagnósticos. |

---
[← Anterior: Vista de Despliegue](07_deployment_view.md) · [Volver al índice](arc42-template-ES.md)
