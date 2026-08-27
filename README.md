# motos-erp — AndiMotors ERP

ERP a medida para **Andina Motors**, un concesionario de motos: gestiona inventario de motos y repuestos, compras a fábrica/distribuidor, facturación de ventas y de taller, empleados (vendedores y mecánicos) y reportes gerenciales (EIS), además del control de activos fijos.

El proyecto nace del taller de **Arquitectura de Software para un Sistema ERP**: gestión del backlog en Jira (épicas, historias de usuario, criterios de aceptación y priorización MoSCoW) y documentación de la arquitectura con la plantilla **arc42**, **11 diagramas UML** originales (clases, objetos, componentes, despliegue, estructura compuesta y 6 diagramas de paquetes, uno por módulo), **2 diagramas C4** (contexto y contenedores) y una **Vista 4+1** (lógica, procesos, desarrollo, física y escenarios) agregados en una iteración posterior — ver [`Vista de Procesos (4+1)/`](<Vista de Procesos (4+1)>).

> El alcance completo, con la triple restricción (tiempo, costo, alcance) del taller, está en [docs/alcance.md](docs/alcance.md).

## Estado del proyecto y del despliegue

El proyecto se ejecuta **localmente** para fines de desarrollo y demostración académica. La arquitectura AWS presentada en la documentación (diagrama de despliegue, capítulo 7 de arc42) corresponde a una **propuesta teórica de despliegue futuro** y **no** a una infraestructura actualmente operativa. No existe una URL de producción, ni una instancia EC2, RDS u otro recurso AWS activo para este proyecto.

| Entorno | Estado |
|---|---|
| Desarrollo local | Implementado |
| Demostración local | Implementado |
| AWS | Propuesto |
| Azure (mencionado como alternativa en la Vista Física) | Propuesto |
| EC2 | Propuesto |
| RDS | Propuesto |
| Load Balancer | Propuesto |
| App móvil | Propuesta |
| Integración DIAN (facturación electrónica) | Propuesta |
| Integración con Proveedores (compras B2B) | Propuesta |
| Producción | No implementado |

Detalle completo en [docs/arc-42/07_deployment_view.md](docs/arc-42/07_deployment_view.md).

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
| Sprint 1 Planning | Planificación del Sprint 1 (épica Compras): sprint backlog, pruebas, DoD, roles y cronograma | [docs/sprint/sprint-1-planning.md](docs/sprint/sprint-1-planning.md) |

### Arquitectura (arc42)

Documentación completa en [docs/arc-42/arc42-template-ES.md](docs/arc-42/arc42-template-ES.md). Esqueleto arc42 de 12 capítulos:

| Capítulo | Contenido | Enlace |
|---|---|---|
| 1. Introducción y Metas | Objetivo del ERP, requisitos de negocio y metas de calidad | [docs/arc-42/01_introduction_and_goals.md](docs/arc-42/01_introduction_and_goals.md) |
| 2. Restricciones de la Arquitectura | Restricciones técnicas y organizativas (stack, equipo, tiempo) | [docs/arc-42/02_architecture_constraints.md](docs/arc-42/02_architecture_constraints.md) |
| 3. Alcance y Contexto del Sistema | Actores y módulos que interactúan con el sistema, incluyendo el diagrama de contexto C4 y sistemas externos (DIAN, Proveedores) | [docs/arc-42/03_system_scope_and_context.md](docs/arc-42/03_system_scope_and_context.md) |
| 4. Estrategia de Solución | Decisiones tecnológicas y estructurales de alto nivel | [docs/arc-42/04_solution_strategy.md](docs/arc-42/04_solution_strategy.md) |
| 5. Vista de Bloques | Componentes, paquetes por módulo, vista lógica (clases/objetos), diagramas C4 y Vista de Desarrollo en capas | [docs/arc-42/05_building_block_view.md](docs/arc-42/05_building_block_view.md) |
| 6. Vista de Ejecución | Escenario de facturación, basado en la estructura interna del componente Facturación | [docs/arc-42/06_runtime_view.md](docs/arc-42/06_runtime_view.md) |
| 7. Vista de Despliegue | Estado actual (ejecución local) y arquitectura de despliegue AWS **propuesta** (no implementada) | [docs/arc-42/07_deployment_view.md](docs/arc-42/07_deployment_view.md) |
| 8. Conceptos Transversales | Seguridad, persistencia, comunicación entre módulos | [docs/arc-42/08_crosscutting_concepts.md](docs/arc-42/08_crosscutting_concepts.md) |
| 9. Decisiones de Arquitectura | Decisiones documentadas e inferidas | [docs/arc-42/09_architecture_decisions.md](docs/arc-42/09_architecture_decisions.md) |
| 10. Requisitos de Calidad | RNF en formato de escenario | [docs/arc-42/10_quality_requirements.md](docs/arc-42/10_quality_requirements.md) |
| 11. Riesgos y Deuda Técnica | Riesgos e inconsistencias detectadas | [docs/arc-42/11_risks_and_technical_debt.md](docs/arc-42/11_risks_and_technical_debt.md) |
| 12. Glosario | Términos clave del dominio | [docs/arc-42/12_glossary.md](docs/arc-42/12_glossary.md) |

> Los capítulos 1, 2, 3, 5, 6 y 7 fueron parte del alcance original del taller. Los capítulos 4, 8, 9, 10 y 11 se completaron para dejar el esqueleto arc42 completo; ver [matriz de cobertura](docs/arc-42/coverage.md) para el estado real de cada sección.

### Diagramas UML (11)

Fuente PlantUML en [`docs/diagramas/plantuml/`](docs/diagramas/plantuml), imágenes exportadas en [`docs/diagramas/img/`](docs/diagramas/img).

| Diagrama | Descripción | Fuente | Imagen |
|---|---|---|---|
| Clases | Modelo de dominio de Facturación: Cliente, Vendedor, ItemVendible, Moto, Repuesto, Factura, DetalleFactura, OrdenTaller, Mecanico | [.plantuml](docs/diagramas/plantuml/diagrama_clases.plantuml) | [.png](docs/diagramas/img/diagrama_clases.png) |
| Objetos | Instancia de ejemplo: un cliente compra una moto a través de un vendedor, documentada en una factura | [.plantuml](docs/diagramas/plantuml/diagrama_objetos.plantuml) | [.png](docs/diagramas/img/diagrama_objetos.png) |
| Componentes | Los 6 módulos del ERP (Inventario, Facturación, Compras, Empleados, EIS, ActivosFijos) y sus dependencias | [.plantuml](docs/diagramas/plantuml/diagrama_componentes.plantuml) | [.png](docs/diagramas/img/diagrama_componentes.png) |
| Despliegue (arquitectura **propuesta**, no operativa) | Nodos propuestos: Cliente-Sucursal (React), Servidor Cloud AWS EC2 (Spring Boot), Servidor BD AWS RDS (PostgreSQL) — ver [estado del despliegue](#estado-del-proyecto-y-del-despliegue) | [.plantuml](docs/diagramas/plantuml/diagrama_despliegue.plantuml) | [.png](docs/diagramas/img/diagrama_despliegue.png) |
| Estructura compuesta | Caja blanca del componente Facturación: GeneradorComprobante, ValidadorComision, ConectorInventario, ConectorEmpleados | [fuente](<docs/diagramas/plantuml/diagrama estructura compuesta>) | [.png](<docs/diagramas/img/Diagrama estructura compuesta.png>) |
| Paquetes — Inventario | GestionMotos, GestionRepuestos, Costos | [.plantuml](docs/diagramas/plantuml/diagrama_paquetes_inventario.plantuml) | [.png](docs/diagramas/img/diagrama_paquetes_inventario.png) |
| Paquetes — Facturación | VentaMotos, OrdenServicio, Comisiones | [.plantuml](docs/diagramas/plantuml/diagrama_paquetes_facturacion.plantuml) | [.png](docs/diagramas/img/diagrama_paquetes_facturacion.png) |
| Paquetes — Compras | PedidosFabrica, RecepcionPedidos | [.plantuml](docs/diagramas/plantuml/diagrama_paquetes_compras.plantuml) | [.png](docs/diagramas/img/diagrama_paquetes_compras.png) |
| Paquetes — Empleados | GestionPersonal, AsignacionTaller | [.plantuml](docs/diagramas/plantuml/diagrama_paquetes_empleados.plantuml) | [.png](docs/diagramas/img/diagrama_paquetes_empleados.png) |
| Paquetes — EIS | Reportes | [.plantuml](docs/diagramas/plantuml/diagrama_paquetes_eis.plantuml) | [.png](docs/diagramas/img/diagrama_paquetes_eis.png) |
| Paquetes — ActivosFijos | ControlActivos | [.plantuml](docs/diagramas/plantuml/diagrama_paquetes_activosfijos.plantuml) | [.png](docs/diagramas/img/diagrama_paquetes_activosfijos.png) |

> Nota: el archivo fuente del diagrama de estructura compuesta se llama literalmente `diagrama estructura compuesta` (sin extensión `.plantuml`, con espacios), a diferencia de los demás que usan `snake_case.plantuml`. No se renombró para no modificar los archivos de diagramas.

### Diagramas C4 (2)

Agregados después de los 11 diagramas UML originales, como capa de contexto/contenedores dedicada.

| Diagrama | Descripción | Fuente | Imagen |
|---|---|---|---|
| C4 — Contexto (Nivel 1) | Actores (Vendedor, Mecánico, Gerente/Administrador) y sistema externo Fábrica/Distribuidor | [.plantuml](docs/diagramas/plantuml/diagrama_c4_contexto.plantuml) | [.png](docs/diagramas/img/diagrama_c4_contexto.png) |
| C4 — Contenedores (Nivel 2) | Aplicación Web (React), API Backend (Spring Boot/AWS EC2), Base de Datos (PostgreSQL/AWS RDS) | [.plantuml](docs/diagramas/plantuml/diagrama_c4_contenedores.plantuml) | [.png](docs/diagramas/img/diagrama_c4_contenedores.png) |

### Vista de Procesos (4+1) — carpeta [`Vista de Procesos (4+1)/`](<Vista de Procesos (4+1)>)

Agregada en una iteración posterior a los 11 diagramas UML y a los diagramas C4. Extiende el modelo con autenticación (`Usuario`), integración con DIAN (facturación electrónica) y con proveedores (compras B2B), y una arquitectura en capas explícita — ver el análisis completo, incluidas las inconsistencias frente a los diagramas originales, en [docs/arc-42/05_building_block_view.md](docs/arc-42/05_building_block_view.md#541-vista-lógica-actualizada-agregada-posteriormente) y en [docs/arc-42/11_risks_and_technical_debt.md](docs/arc-42/11_risks_and_technical_debt.md).

| Vista | Contenido | Imagen |
|---|---|---|
| 1. Vista Lógica | Modelo de clases actualizado: `Usuario`, `Cliente`, `Proveedor`, `Compra`, `Vehiculo`, `Venta`, `DetalleVenta`, `Factura` (con `cufe`/DIAN), `Repuesto`, `OrdenServicio` | [.jpg](<Vista de Procesos (4+1)/1. vista logica.jpg>) |
| 2. Vista de Procesos | Diagrama de secuencia: registrar venta, verificar disponibilidad, descontar stock, generar factura y enviar a DIAN | [.jpg](<Vista de Procesos (4+1)/2. vista de procesos.jpg>) |
| 3. Vista de Desarrollo | Arquitectura en capas: Frontend (SPA), API (REST), Dominio, Persistencia, Infraestructura (ORM, Cliente DIAN) | [.jpg](<Vista de Procesos (4+1)/3. vista de desarrollo.jpg>) |
| 4. Vista Física | Despliegue propuesto: navegador + app móvil → Load Balancer → Servidor de Aplicaciones → BD/Servidor de Reportes/DIAN/Proveedores, en "Proveedor Cloud (AWS/Azure)" | [.jpg](<Vista de Procesos (4+1)/4. vista fisica.jpg>) |
| Vista de Escenarios | Casos de uso: Administrador, Cliente, Vendedor, Técnico, Jefe de Compras, Proveedor, DIAN | [.jpg](<Vista de Procesos (4+1)/Vista de escenarios.jpg>) |

⚠️ Estos diagramas introducen elementos (facturación electrónica DIAN, app móvil, integración con proveedores) que `alcance.md` había declarado inicialmente fuera de alcance, y usan nombres distintos para los mismos roles/entidades de los diagramas originales (p. ej. `Moto`→`Vehiculo`, `Mecanico`→"Técnico"). Todo esto está documentado explícitamente como inconsistencia en [docs/arc-42/11_risks_and_technical_debt.md](docs/arc-42/11_risks_and_technical_debt.md) (riesgos R-10 a R-14), no oculto ni resuelto silenciosamente.
