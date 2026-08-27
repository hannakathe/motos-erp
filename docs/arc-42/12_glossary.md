[← Volver al índice](arc42-template-ES.md)

# 12. Glosario

## Términos generales / metodología

| Término | Definición |
|---|---|
| ERP | Enterprise Resource Planning — sistema de planificación de recursos empresariales; nombre de la categoría de producto a la que pertenece AndiMotors ERP (ver [README.md](../../README.md)). |
| DMS | Dealer Management System — categoría de software de gestión para concesionarios, usada como referencia comparativa frente al ERP propio (ver [antecedentes.md](../antecedentes.md): CDK Global, Shift Industry, Autologica Sky). |
| Épica | Historia de usuario de gran tamaño que agrupa funcionalidades de un mismo módulo (ej. "Módulo de Compras"). |
| Historia de Usuario (HU) | Descripción breve de una funcionalidad desde la perspectiva de un rol, con el formato "Como &lt;rol&gt;, quiero &lt;acción&gt;, para que &lt;beneficio&gt;". |
| Criterio de Aceptación | Condición verificable, en formato Dado-Cuando-Entonces, que determina si una historia de usuario está correctamente implementada. |
| MoSCoW | Método de priorización de historias de usuario en Must/Should/Could/Won't have, usado en [product-backlog.md](../product-backlog.md). |
| Story Points (SP) | Unidad de estimación de esfuerzo relativo de una historia de usuario, en escala Fibonacci (ver [product-backlog.md](../product-backlog.md)). |
| Sucursal | Sede física del concesionario; el modelo de despliegue **propuesto** (AWS, no desplegado) contempla una sola sucursal cliente (ver [07. Vista de Despliegue](07_deployment_view.md)), aunque RNF-3 exige soportar varias. |
| C4 Model | Notación de diagramas de arquitectura de software en niveles (Contexto, Contenedores, Componentes, Código); usada en [diagrama_c4_contexto.plantuml](../diagramas/plantuml/diagrama_c4_contexto.plantuml) y [diagrama_c4_contenedores.plantuml](../diagramas/plantuml/diagrama_c4_contenedores.plantuml). |
| Vista 4+1 | Modelo de vistas arquitectónicas (Lógica, Procesos, Desarrollo, Física, Escenarios) usado en la carpeta [`Vista de Procesos (4+1)/`](<../../Vista de Procesos (4+1)>), agregada por el equipo después de los 11 diagramas UML originales. |
| SPA | Single Page Application — patrón de frontend donde la aplicación web (React) se ejecuta como una sola página que actualiza su contenido dinámicamente; usado en la Vista de Desarrollo y el diagrama de contenedores C4. |
| ORM | Object-Relational Mapping — capa de infraestructura que traduce entre objetos del backend y tablas de la base de datos relacional; mencionada en la Vista de Desarrollo. |
| DIAN | Dirección de Impuestos y Aduanas Nacionales — entidad tributaria de Colombia; sistema externo al que se enviaría la facturación electrónica en la arquitectura propuesta (ver [4.7](04_solution_strategy.md#47-integración-con-sistemas-externos-dian-y-proveedores)). |
| CUFE | Código Único de Facturación Electrónica — identificador que DIAN devuelve al confirmar una factura electrónica; atributo de la clase `Factura` en la Vista Lógica actualizada. |
| Load Balancer | Componente que distribuye el tráfico entrante entre instancias del servidor de aplicaciones; agregado en la Vista Física como parte de la arquitectura de despliegue propuesta (ver [AD-12](09_architecture_decisions.md#ad-12--load-balancer-en-la-arquitectura-de-despliegue-propuesta)). |

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

## Términos de la Vista Lógica original (Diagrama de Clases y Objetos)

Dominio de Facturación, de los 11 diagramas UML originales — ver [5.4 Vista Lógica](05_building_block_view.md#54-vista-lógica-diagrama-de-clases-y-objetos).

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

## Términos de la Vista Lógica actualizada (agregada posteriormente)

Ver [5.4.1](05_building_block_view.md#541-vista-lógica-actualizada-agregada-posteriormente) para la tabla completa de equivalencias con la Vista Lógica original.

| Término | Definición |
|---|---|
| Usuario | Entidad de acceso al sistema (`id`, `rol`, `usuario`, `passwordHash`), propuesta para resolver la autenticación/autorización de RNF-2 (ver [AD-09](09_architecture_decisions.md#ad-09--autenticación-y-autorización-basada-en-la-entidad-usuario)). |
| Proveedor | Entidad externa (`id`, `nombre`, `nit`) que suministra motos/repuestos; equivalente a "Fábrica/Distribuidor" en el C4 de contexto y en `alcance.md`. |
| Compra | Registro de una compra a proveedor (`id`, `fecha`, `total`), del módulo Compras. |
| Vehiculo | Reemplaza a `Moto` en la Vista Lógica actualizada; ver riesgo de posible pérdida de trazabilidad por VIN en [11. Riesgos — R-13](11_risks_and_technical_debt.md#r-13--la-vista-lógica-actualizada-simplifica-vehiculo-y-podría-perder-trazabilidad-por-vin). |
| Venta | Reemplaza el rol de "venta" que antes cubría `Factura`; ahora separada de su comprobante fiscal. |
| DetalleVenta | Reemplaza a `DetalleFactura`. |
| OrdenServicio | Reemplaza a `OrdenTaller`. |

---
[← Anterior: Riesgos y Deuda Técnica](11_risks_and_technical_debt.md) · [Volver al índice](arc42-template-ES.md)
