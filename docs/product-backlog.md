# Product Backlog y Priorización — AndiMotors ERP

Product Backlog del ERP para el concesionario de motos Andina Motors, con 22 historias de usuario distribuidas en las 6 épicas del producto (Inventario, Facturación, Compras, Empleados, EIS y Activos Fijos), estimadas en Story Points (Fibonacci) y priorizadas con dos métodos: **MoSCoW** y **Matriz Valor de Negocio vs. Esfuerzo**.

> **Nota de evolución (2026-09-01).** Este documento refleja el corte inicial de 22 HU y
> su priorización. El backlog vigente en Jira (proyecto **SCRUM**) creció a 54 historias
> con códigos por módulo (HU-01…HU-09 Compras, HU-F01…HU-F09, HU-S01…HU-S09,
> HU-A01…HU-A09, HU-E01…HU-E09, HU-EIS01…HU-EIS09). El detalle funcional actualizado y la
> correspondencia entre ambos esquemas están en
> [requisitos/funcionales.md](requisitos/funcionales.md); los no funcionales, en
> [requisitos/No funcionales.md](<requisitos/No funcionales.md>). La priorización MoSCoW y
> Valor/Esfuerzo de las HU nuevas aún no se ha rehecho aquí.

## 1. Product Backlog completo

| ID | Módulo (Épica) | Historia de Usuario | SP | MoSCoW |
|----|-----------------|----------------------|----|--------|
| HU-01 | Inventario | Como encargado de inventario, quiero registrar el ingreso de una moto nueva capturando VIN, modelo, cilindraje, color y año, para tener trazabilidad individual de cada unidad. | 5 | Must |
| HU-02 | Inventario | Como encargado de inventario, quiero registrar el ingreso de repuestos asociándolos a los modelos compatibles, para facilitar su búsqueda desde el taller. | 5 | Must |
| HU-03 | Inventario | Como vendedor, quiero consultar la disponibilidad de una moto filtrando por modelo y sucursal, para ofrecerla al cliente en el momento. | 3 | Must |
| HU-04 | Inventario | Como encargado de inventario, quiero definir un stock mínimo de reorden para cada repuesto, para recibir alertas antes de que se agote. | 3 | Should |
| HU-05 | Inventario | Como gerente, quiero registrar el costo de importación/flete de cada unidad, para calcular el margen real de venta. | 2 | Should |
| HU-06 | Facturación | Como vendedor, quiero facturar la venta de una moto verificando su disponibilidad en inventario, para evitar vender una unidad ya reservada o vendida. | 8 | Must |
| HU-07 | Facturación | Como vendedor, quiero que el sistema calcule automáticamente impuestos y accesorios de una venta, para emitir la factura sin errores manuales. | 5 | Must |
| HU-08 | Facturación | Como vendedor, quiero generar el comprobante de venta en PDF, para entregárselo al cliente de forma inmediata. | 3 | Must |
| HU-09 | Facturación | Como mecánico, quiero registrar los repuestos usados en una orden de servicio, para que se descuenten automáticamente del inventario. | 5 | Must |
| HU-10 | Facturación | Como administrador, quiero registrar la mano de obra del mecánico en cada orden de taller, para calcular el costo total del servicio. | 3 | Must |
| HU-11 | Facturación | Como gerente, quiero que el sistema calcule la comisión del vendedor al cerrar una venta, para pagarla de forma correcta y oportuna. | 5 | Should |
| HU-12 | Compras | Como encargado de compras, quiero generar un pedido a fábrica/distribuidor seleccionando modelos y cantidades, para reponer el inventario. | 5 | Must |
| HU-13 | Compras | Como encargado de compras, quiero consultar el histórico de ventas por modelo, para decidir cuánto pedir en el próximo pedido. | 3 | Should |
| HU-14 | Compras | Como encargado de compras, quiero registrar la fecha estimada de entrega de un pedido, para planear el inventario. | 2 | Could |
| HU-15 | Compras | Como encargado de bodega, quiero validar las unidades recibidas contra la orden de compra, para detectar faltantes o errores. | 3 | Must |
| HU-16 | Empleados | Como administrador, quiero registrar vendedores con su porcentaje de comisión, para calcular sus pagos automáticamente. | 3 | Must |
| HU-17 | Empleados | Como administrador, quiero registrar mecánicos con su especialidad, para asignarles las órdenes de taller correctas. | 3 | Must |
| HU-18 | Empleados | Como jefe de taller, quiero asignar una orden de servicio a un mecánico disponible, para distribuir la carga de trabajo del taller. | 5 | Should |
| HU-19 | EIS | Como gerente, quiero visualizar reportes del histórico de ventas por modelo, para tomar decisiones informadas de compra e inventario. | 5 | Should |
| HU-20 | EIS | Como gerente, quiero ver indicadores gerenciales (ventas, comisiones, taller) en un solo panel, para monitorear el desempeño del negocio. | 8 | Could |
| HU-21 | Activos Fijos | Como administrador, quiero registrar los activos fijos del concesionario (herramientas, equipos, vehículos), para llevar control del patrimonio. | 3 | Could |
| HU-22 | Activos Fijos | Como contador, quiero que el sistema calcule automáticamente la depreciación de los activos fijos, para reflejar su valor contable actualizado. | 5 | Won't (este proyecto) |

## 2. Método de priorización 1 — MoSCoW

Clasificación según el nivel de necesidad para el producto:

- **Must have:** funcionalidad indispensable, sin la cual el ERP no cumple su propósito mínimo (core del negocio: vender, facturar, controlar inventario).
- **Should have:** importante pero no crítica para el primer incremento; aporta valor significativo.
- **Could have:** deseable, mejora la experiencia pero puede posponerse sin afectar la operación básica.
- **Won't have (este proyecto):** queda fuera del alcance del incremento actual, se documenta para un futuro sprint.

| Prioridad | Historias |
|-----------|-----------|
| Must | HU-01, HU-02, HU-03, HU-06, HU-07, HU-08, HU-09, HU-10, HU-12, HU-15, HU-16, HU-17 |
| Should | HU-04, HU-05, HU-11, HU-13, HU-18, HU-19 |
| Could | HU-14, HU-20, HU-21 |
| Won't | HU-22 |

## 3. Método de priorización 2 — Matriz Valor de Negocio vs. Esfuerzo

Se cruza el valor de negocio (1 a 10, definido según el impacto en la operación del concesionario) contra el esfuerzo estimado en Story Points:

- **Quick Win** (alto valor, bajo esfuerzo): se implementan primero.
- **Proyecto Mayor** (alto valor, alto esfuerzo): requieren más planeación, pero son indispensables.
- **Relleno** (bajo valor, bajo esfuerzo): se hacen si sobra capacidad en el sprint.
- **Pozo sin fondo** (bajo valor, alto esfuerzo): se posponen o se replantean.

| ID | Valor (1-10) | Esfuerzo (SP) | Cuadrante |
|----|--------------|----------------|-----------|
| HU-03 | 9 | 3 | Quick Win |
| HU-08 | 8 | 3 | Quick Win |
| HU-10 | 7 | 3 | Quick Win |
| HU-15 | 7 | 3 | Quick Win |
| HU-16 | 7 | 3 | Quick Win |
| HU-17 | 7 | 3 | Quick Win |
| HU-01 | 9 | 5 | Proyecto Mayor |
| HU-02 | 8 | 5 | Proyecto Mayor |
| HU-06 | 10 | 8 | Proyecto Mayor |
| HU-07 | 9 | 5 | Proyecto Mayor |
| HU-09 | 8 | 5 | Proyecto Mayor |
| HU-11 | 7 | 5 | Proyecto Mayor |
| HU-12 | 8 | 5 | Proyecto Mayor |
| HU-04 | 6 | 3 | Relleno |
| HU-05 | 5 | 2 | Relleno |
| HU-13 | 6 | 3 | Relleno |
| HU-14 | 4 | 2 | Relleno |
| HU-21 | 4 | 3 | Relleno |
| HU-18 | 6 | 5 | Pozo sin fondo |
| HU-19 | 6 | 5 | Pozo sin fondo |
| HU-20 | 5 | 8 | Pozo sin fondo |
| HU-22 | 3 | 5 | Pozo sin fondo |

## 4. Backlog priorizado final

Combinando MoSCoW (filtro principal) con la matriz Valor/Esfuerzo (criterio de desempate), el orden final para alimentar el Sprint Backlog del primer Sprint es:

1. HU-03, HU-08, HU-10, HU-15, HU-16, HU-17 — *Alta prioridad (Sprint 1)*
2. HU-01, HU-02, HU-06, HU-07, HU-09, HU-12, HU-11 — *Alta prioridad*
3. HU-04, HU-05, HU-13, HU-18, HU-19 — *Media prioridad*
4. HU-14, HU-20, HU-21, HU-22 — *Baja prioridad*

> Evidencia: este backlog debe reflejarse también en el [Tablero de Jira del proyecto](https://abrilhanna6.atlassian.net/jira/software/projects/KAN/list?jql=project+%3D+KAN+ORDER+BY+cf%5B10019%5D+ASC), con épicas, historias de usuario, story points y etiqueta MoSCoW.
