# motos-erp — AndiMotors ERP

ERP a medida para **Andina Motors**, un concesionario de motos: gestiona inventario de motos y repuestos, compras a fábrica/distribuidor, facturación de ventas y de taller, empleados (vendedores y mecánicos) y reportes gerenciales (EIS), además del control de activos fijos.

El proyecto nace del taller de **Arquitectura de Software para un Sistema ERP**: gestión del backlog en Jira (épicas, historias de usuario, criterios de aceptación y priorización MoSCoW) y documentación de la arquitectura con la plantilla **arc42** y **11 diagramas UML** (clases, objetos, componentes, despliegue, estructura compuesta y 6 diagramas de paquetes, uno por módulo).

> El alcance completo, con la triple restricción (tiempo, costo, alcance) del taller, está en [docs/alcance.md](docs/alcance.md).

## Módulos del sistema

| Módulo | Responsabilidad |
|---|---|
| Inventario (Stock/Costos) | Alta y control de stock de motos y repuestos, disponibilidad por sucursal, costos |
| Facturación | Venta de motos, órdenes de servicio de taller, cálculo de comisiones |
| Compras | Pedidos a fábrica/distribuidor y recepción de mercancía |
| Empleados | Registro de vendedores y mecánicos, asignación de órdenes de taller |
| EIS | Reportes e indicadores gerenciales (histórico de ventas) |
| ActivosFijos | Registro de activos y cálculo de depreciación |

## Gestión del backlog (Jira)

[Tablero de Jira — Proyecto ERP](https://abrilhanna6.atlassian.net/jira/software/projects/KAN/list?jql=project+%3D+KAN+ORDER+BY+cf%5B10019%5D+ASC)

Incluye las 6 épicas del ERP (Compras, Facturación, Stock/Costos, Activos Fijos, Empleados, EIS), con historias de usuario, criterios de aceptación (formato Dado-Cuando-Entonces) y priorización MoSCoW.

## Índice de documentación

### Contexto del proyecto

| Documento | Descripción | Enlace |
|---|---|---|
| Alcance | Triple restricción (tiempo, costo, alcance) y qué está dentro/fuera del proyecto | [docs/alcance.md](docs/alcance.md) |
| Antecedentes | Comparación de AndiMotors ERP contra 3 DMS del mercado (CDK Global, Shift Industry, Autologica Sky) | [docs/antecedentes.md](docs/antecedentes.md) |
| Requisitos funcionales | Árbol de descomposición de requisitos (RF), 4 niveles | [docs/requisitos/funcionales.md](docs/requisitos/funcionales.md) |
| Requisitos no funcionales | RNF de rendimiento, seguridad, escalabilidad, usabilidad, disponibilidad, y tecnologías seleccionadas | [docs/requisitos/No funcionales.md](docs/requisitos/No%20funcionales.md) |

### Arquitectura (arc42)

Documentación completa en [docs/arc-42/arc42-template-ES.md](docs/arc-42/arc42-template-ES.md). Capítulos:

| Capítulo | Contenido | Enlace |
|---|---|---|
| 1. Introducción y Metas | Objetivo del ERP, requisitos de negocio y metas de calidad | [docs/arc-42/01_introduction_and_goals.md](docs/arc-42/01_introduction_and_goals.md) |
| 2. Restricciones de la Arquitectura | Restricciones técnicas y organizativas (stack, equipo, tiempo) | [docs/arc-42/02_architecture_constraints.md](docs/arc-42/02_architecture_constraints.md) |
| 3. Alcance y Contexto del Sistema | Actores y módulos que interactúan con el sistema | [docs/arc-42/03_system_scope_and_context.md](docs/arc-42/03_system_scope_and_context.md) |
| 5. Vista de Bloques | Componentes, paquetes por módulo y vista lógica (clases/objetos) | [docs/arc-42/05_building_block_view.md](docs/arc-42/05_building_block_view.md) |
| 6. Vista de Ejecución | Escenario de facturación, basado en la estructura interna del componente Facturación | [docs/arc-42/06_runtime_view.md](docs/arc-42/06_runtime_view.md) |
| 7. Vista de Despliegue | Nodos físicos: cliente, servidor cloud y base de datos | [docs/arc-42/07_deployment_view.md](docs/arc-42/07_deployment_view.md) |
| 10. Glosario | Términos clave del dominio | [docs/arc-42/10_glossary.md](docs/arc-42/10_glossary.md) |

> Los capítulos 4 (Estrategia de solución), 8 (Conceptos transversales), 9 (Decisiones de diseño) y 11 (Riesgos y deuda técnica) no forman parte del alcance de este taller.

### Diagramas UML (11)

Fuente PlantUML en [`docs/diagramas/plantuml/`](docs/diagramas/plantuml), imágenes exportadas en [`docs/diagramas/img/`](docs/diagramas/img).

| Diagrama | Descripción | Fuente | Imagen |
|---|---|---|---|
| Clases | Modelo de dominio de Facturación: Cliente, Vendedor, ItemVendible, Moto, Repuesto, Factura, DetalleFactura, OrdenTaller, Mecanico | [.plantuml](docs/diagramas/plantuml/diagrama_clases.plantuml) | [.png](docs/diagramas/img/diagrama_clases.png) |
| Objetos | Instancia de ejemplo: un cliente compra una moto a través de un vendedor, documentada en una factura | [.plantuml](docs/diagramas/plantuml/diagrama_objetos.plantuml) | [.png](docs/diagramas/img/diagrama_objetos.png) |
| Componentes | Los 6 módulos del ERP (Inventario, Facturación, Compras, Empleados, EIS, ActivosFijos) y sus dependencias | [.plantuml](docs/diagramas/plantuml/diagrama_componentes.plantuml) | [.png](docs/diagramas/img/diagrama_componentes.png) |
| Despliegue | Nodos físicos: Cliente-Sucursal (React), Servidor Cloud AWS EC2 (Spring Boot), Servidor BD AWS RDS (PostgreSQL) | [.plantuml](docs/diagramas/plantuml/diagrama_despliegue.plantuml) | [.png](docs/diagramas/img/diagrama_despliegue.png) |
| Estructura compuesta | Caja blanca del componente Facturación: GeneradorComprobante, ValidadorComision, ConectorInventario, ConectorEmpleados | [fuente](<docs/diagramas/plantuml/diagrama estructura compuesta>) | [.png](<docs/diagramas/img/Diagrama estructura compuesta.png>) |
| Paquetes — Inventario | GestionMotos, GestionRepuestos, Costos | [.plantuml](docs/diagramas/plantuml/diagrama_paquetes_inventario.plantuml) | [.png](docs/diagramas/img/diagrama_paquetes_inventario.png) |
| Paquetes — Facturación | VentaMotos, OrdenServicio, Comisiones | [.plantuml](docs/diagramas/plantuml/diagrama_paquetes_facturacion.plantuml) | [.png](docs/diagramas/img/diagrama_paquetes_facturacion.png) |
| Paquetes — Compras | PedidosFabrica, RecepcionPedidos | [.plantuml](docs/diagramas/plantuml/diagrama_paquetes_compras.plantuml) | [.png](docs/diagramas/img/diagrama_paquetes_compras.png) |
| Paquetes — Empleados | GestionPersonal, AsignacionTaller | [.plantuml](docs/diagramas/plantuml/diagrama_paquetes_empleados.plantuml) | [.png](docs/diagramas/img/diagrama_paquetes_empleados.png) |
| Paquetes — EIS | Reportes | [.plantuml](docs/diagramas/plantuml/diagrama_paquetes_eis.plantuml) | [.png](docs/diagramas/img/diagrama_paquetes_eis.png) |
| Paquetes — ActivosFijos | ControlActivos | [.plantuml](docs/diagramas/plantuml/diagrama_paquetes_activosfijos.plantuml) | [.png](docs/diagramas/img/diagrama_paquetes_activosfijos.png) |

> Nota: el archivo fuente del diagrama de estructura compuesta se llama literalmente `diagrama estructura compuesta` (sin extensión `.plantuml`, con espacios), a diferencia de los demás que usan `snake_case.plantuml`. No se renombró para no modificar los archivos de diagramas.
