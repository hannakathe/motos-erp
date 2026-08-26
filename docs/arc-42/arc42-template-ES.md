[← Volver al README raíz](../../README.md)

# Documentación de Arquitectura — AndiMotors ERP (arc42)

**Acerca de arc42**

arc42, la plantilla de documentación para arquitectura de sistemas y de software.

Por Dr. Gernot Starke, Dr. Peter Hruschka y otros contribuyentes.

Revisión de la plantilla: 7.0 ES (basada en asciidoc), Enero 2017.

© Reconocemos que este documento utiliza material de la plantilla de arquitectura arc42, <https://www.arc42.org>. Creada por Dr. Peter Hruschka y Dr. Gernot Starke.

---

## Datos del documento

| Campo | Valor |
|---|---|
| Sistema | AndiMotors ERP (motos-erp) |
| Versión del documento | 2.0 (esqueleto arc42 completo, 12 secciones) |
| Fecha | 2026-08-26 |
| Estado | Esqueleto completo — ver estado real de cada sección en la [matriz de cobertura](coverage.md) |
| Fuentes principales | [README.md](../../README.md), [alcance.md](../alcance.md), [antecedentes.md](../antecedentes.md), [requisitos/funcionales.md](../requisitos/funcionales.md), [requisitos/No funcionales.md](<../requisitos/No%20funcionales.md>), [product-backlog.md](../product-backlog.md), [sprint/sprint-1-planning.md](../sprint/sprint-1-planning.md), diagramas PlantUML en [docs/diagramas/plantuml/](../diagramas/plantuml) |

**Nota metodológica**: esta documentación se construyó a partir del estado actual del repositorio en la fecha indicada arriba. Las secciones 1, 2, 3, 5, 6 y 7 ya existían, elaboradas durante el taller de Arquitectura de Software, y se conservaron sin alterar su contenido validado. Las secciones 4, 8, 9, 10 y 11 se completaron para dejar el esqueleto arc42 completo de 12 capítulos, documentando únicamente lo que tiene evidencia directa o es una inferencia razonable (marcada explícitamente como tal) a partir del código, los diagramas y los documentos de requisitos existentes. Donde no hay evidencia suficiente, se indica `[POR DEFINIR]` o `[NO EVIDENCIADO EN EL REPOSITORIO]`. El glosario, originalmente numerado como capítulo 10, se renumeró a 12 para alinearse con la numeración estándar de arc42 (10 = Requisitos de Calidad, 11 = Riesgos y Deuda Técnica, 12 = Glosario).

## Índice

Este documento describe la arquitectura del **ERP Andina Motors (AndiMotors ERP)**, un sistema con 6 módulos (Inventario/Stock-Costos, Facturación, Compras, Empleados, EIS, ActivosFijos). Cada sección se desarrolla en un archivo independiente dentro de este directorio:

| Capítulo | Contenido | Enlace |
|---|---|---|
| 1. Introducción y Metas | Objetivo del ERP, requisitos de negocio y metas de calidad | [01_introduction_and_goals.md](01_introduction_and_goals.md) |
| 2. Restricciones de la Arquitectura | Decisiones tecnológicas (Java/Spring Boot, React, PostgreSQL, AWS, Docker) y restricciones organizativas | [02_architecture_constraints.md](02_architecture_constraints.md) |
| 3. Alcance y Contexto del Sistema | Actores del negocio y contexto técnico del sistema | [03_system_scope_and_context.md](03_system_scope_and_context.md) |
| 4. Estrategia de Solución | Decisiones tecnológicas y estructurales de alto nivel (estilo, modularización, persistencia, seguridad) | [04_solution_strategy.md](04_solution_strategy.md) |
| 5. Vista de Bloques | Diagrama de Componentes, diagramas de Paquetes por módulo, estructura compuesta de Facturación y vista lógica (clases/objetos) | [05_building_block_view.md](05_building_block_view.md) |
| 6. Vista de Ejecución | Escenario de facturación basado en la estructura interna del componente Facturación | [06_runtime_view.md](06_runtime_view.md) |
| 7. Vista de Despliegue | Diagrama de Despliegue: cliente, servidor cloud y base de datos | [07_deployment_view.md](07_deployment_view.md) |
| 8. Conceptos Transversales | Seguridad, persistencia, comunicación entre módulos, validaciones | [08_crosscutting_concepts.md](08_crosscutting_concepts.md) |
| 9. Decisiones de Arquitectura | Decisiones documentadas e inferidas (backend único, BD compartida, `ItemVendible`, Docker) | [09_architecture_decisions.md](09_architecture_decisions.md) |
| 10. Requisitos de Calidad | RNF-1 a RNF-5 en formato de escenario | [10_quality_requirements.md](10_quality_requirements.md) |
| 11. Riesgos y Deuda Técnica | Riesgos e inconsistencias detectadas entre requisitos, backlog y diagramas | [11_risks_and_technical_debt.md](11_risks_and_technical_debt.md) |
| 12. Glosario | Términos clave del dominio | [12_glossary.md](12_glossary.md) |

> Las secciones 1, 2, 3, 5, 6 y 7 fueron parte del alcance original del taller. Las secciones 4, 8, 9, 10 y 11 se agregaron para completar el esqueleto arc42 de 12 capítulos; ver la nota metodológica arriba y la [matriz de cobertura](coverage.md) para el estado real de cada una.

## Matrices complementarias

| Documento | Contenido |
|---|---|
| [traceability.md](traceability.md) | Matriz de trazabilidad: requisito → módulo → componente → sección arc42 → diagrama → evidencia |
| [coverage.md](coverage.md) | Matriz de cobertura: estado de completitud de cada una de las 12 secciones arc42 |

## Documentos de contexto del proyecto

| Documento | Descripción | Enlace |
|---|---|---|
| Alcance | Triple restricción (tiempo, costo, alcance) del taller | [../alcance.md](../alcance.md) |
| Antecedentes | Comparación contra 3 DMS del mercado (CDK Global, Shift Industry, Autologica Sky) | [../antecedentes.md](../antecedentes.md) |
| Product Backlog | 22 historias de usuario, priorización MoSCoW y Valor/Esfuerzo | [../product-backlog.md](../product-backlog.md) |
| Sprint 1 Planning | Planificación del Sprint 1 (épica Compras): sprint backlog, pruebas, DoD, roles | [../sprint/sprint-1-planning.md](../sprint/sprint-1-planning.md) |
| Requisitos funcionales | Árbol de requisitos (RF), 4 niveles | [../requisitos/funcionales.md](../requisitos/funcionales.md) |
| Requisitos no funcionales | RNF y tecnologías seleccionadas | [../requisitos/No funcionales.md](<../requisitos/No funcionales.md>) |

## Diagramas fuente

Los diagramas se modelan en PlantUML en [`docs/diagramas/plantuml/`](../diagramas/plantuml); las imágenes exportadas están en [`docs/diagramas/img/`](../diagramas/img).

| Diagrama | Fuente | Imagen |
|---|---|---|
| Clases (vista lógica de Facturación) | [diagrama_clases.plantuml](../diagramas/plantuml/diagrama_clases.plantuml) | [diagrama_clases.png](../diagramas/img/diagrama_clases.png) |
| Objetos (instancia de ejemplo) | [diagrama_objetos.plantuml](../diagramas/plantuml/diagrama_objetos.plantuml) | [diagrama_objetos.png](../diagramas/img/diagrama_objetos.png) |
| Componentes (6 módulos del ERP) | [diagrama_componentes.plantuml](../diagramas/plantuml/diagrama_componentes.plantuml) | [diagrama_componentes.png](../diagramas/img/diagrama_componentes.png) |
| Despliegue (nodos físicos) | [diagrama_despliegue.plantuml](../diagramas/plantuml/diagrama_despliegue.plantuml) | [diagrama_despliegue.png](../diagramas/img/diagrama_despliegue.png) |
| Estructura compuesta (caja blanca de Facturación) | [diagrama estructura compuesta](<../diagramas/plantuml/diagrama estructura compuesta>) | [Diagrama estructura compuesta.png](<../diagramas/img/Diagrama estructura compuesta.png>) |
| Paquetes — Inventario (Stock/Costos) | [diagrama_paquetes_inventario.plantuml](../diagramas/plantuml/diagrama_paquetes_inventario.plantuml) | [diagrama_paquetes_inventario.png](../diagramas/img/diagrama_paquetes_inventario.png) |
| Paquetes — Facturación | [diagrama_paquetes_facturacion.plantuml](../diagramas/plantuml/diagrama_paquetes_facturacion.plantuml) | [diagrama_paquetes_facturacion.png](../diagramas/img/diagrama_paquetes_facturacion.png) |
| Paquetes — Compras | [diagrama_paquetes_compras.plantuml](../diagramas/plantuml/diagrama_paquetes_compras.plantuml) | [diagrama_paquetes_compras.png](../diagramas/img/diagrama_paquetes_compras.png) |
| Paquetes — Empleados | [diagrama_paquetes_empleados.plantuml](../diagramas/plantuml/diagrama_paquetes_empleados.plantuml) | [diagrama_paquetes_empleados.png](../diagramas/img/diagrama_paquetes_empleados.png) |
| Paquetes — EIS | [diagrama_paquetes_eis.plantuml](../diagramas/plantuml/diagrama_paquetes_eis.plantuml) | [diagrama_paquetes_eis.png](../diagramas/img/diagrama_paquetes_eis.png) |
| Paquetes — ActivosFijos | [diagrama_paquetes_activosfijos.plantuml](../diagramas/plantuml/diagrama_paquetes_activosfijos.plantuml) | [diagrama_paquetes_activosfijos.png](../diagramas/img/diagrama_paquetes_activosfijos.png) |

> El archivo fuente del diagrama de estructura compuesta se llama literalmente `diagrama estructura compuesta` (sin extensión `.plantuml`, con espacios en el nombre), a diferencia del resto que usa `snake_case.plantuml`. Se referencia tal cual está en el repositorio, sin renombrarlo.
