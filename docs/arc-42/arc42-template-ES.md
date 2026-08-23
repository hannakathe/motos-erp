[← Volver al README raíz](../../README.md)

# Documentación de Arquitectura — AndiMotors ERP (arc42)

**Acerca de arc42**

arc42, la plantilla de documentación para arquitectura de sistemas y de software.

Por Dr. Gernot Starke, Dr. Peter Hruschka y otros contribuyentes.

Revisión de la plantilla: 7.0 ES (basada en asciidoc), Enero 2017.

© Reconocemos que este documento utiliza material de la plantilla de arquitectura arc42, <https://www.arc42.org>. Creada por Dr. Peter Hruschka y Dr. Gernot Starke.

---

## Índice

Este documento describe la arquitectura del **ERP Andina Motors (AndiMotors ERP)**, un sistema con 6 módulos (Inventario/Stock-Costos, Facturación, Compras, Empleados, EIS, ActivosFijos). Cada sección se desarrolla en un archivo independiente dentro de este directorio:

| Capítulo | Contenido | Enlace |
|---|---|---|
| 1. Introducción y Metas | Objetivo del ERP, requisitos de negocio y metas de calidad | [01_introduction_and_goals.md](01_introduction_and_goals.md) |
| 2. Restricciones de la Arquitectura | Decisiones tecnológicas (Java/Spring Boot, React, PostgreSQL, AWS, Docker) y restricciones organizativas | [02_architecture_constraints.md](02_architecture_constraints.md) |
| 3. Alcance y Contexto del Sistema | Actores del negocio y contexto técnico del sistema | [03_system_scope_and_context.md](03_system_scope_and_context.md) |
| 5. Vista de Bloques | Diagrama de Componentes, diagramas de Paquetes por módulo, estructura compuesta de Facturación y vista lógica (clases/objetos) | [05_building_block_view.md](05_building_block_view.md) |
| 6. Vista de Ejecución | Escenario de facturación basado en la estructura interna del componente Facturación | [06_runtime_view.md](06_runtime_view.md) |
| 7. Vista de Despliegue | Diagrama de Despliegue: cliente, servidor cloud y base de datos | [07_deployment_view.md](07_deployment_view.md) |
| 10. Glosario | Términos clave del dominio | [10_glossary.md](10_glossary.md) |

> Las secciones 4 (Estrategia de solución), 8 (Conceptos transversales), 9 (Decisiones de diseño) y 11 (Riesgos y deuda técnica) de la plantilla arc42 no forman parte del alcance de este taller y quedan pendientes para una futura iteración del proyecto.

## Documentos de contexto del proyecto

| Documento | Descripción | Enlace |
|---|---|---|
| Alcance | Triple restricción (tiempo, costo, alcance) del taller | [../alcance.md](../alcance.md) |
| Antecedentes | Comparación contra 3 DMS del mercado (CDK Global, Shift Industry, Autologica Sky) | [../antecedentes.md](../antecedentes.md) |
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
