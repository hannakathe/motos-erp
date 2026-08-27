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
| Versión del documento | 3.0 (esqueleto arc42 completo, 12 secciones, integrando C4 y Vista 4+1) |
| Fecha | 2026-08-26 |
| Estado | Esqueleto completo — ver estado real de cada sección en la [matriz de cobertura](coverage.md) |
| Fuentes principales | [README.md](../../README.md), [alcance.md](../alcance.md), [antecedentes.md](../antecedentes.md), [requisitos/funcionales.md](../requisitos/funcionales.md), [requisitos/No funcionales.md](<../requisitos/No%20funcionales.md>), [product-backlog.md](../product-backlog.md), [sprint/sprint-1-planning.md](../sprint/sprint-1-planning.md), diagramas PlantUML en [docs/diagramas/plantuml/](../diagramas/plantuml), diagramas C4, carpeta [`Vista de Procesos (4+1)/`](<../../Vista de Procesos (4+1)>) |

**Nota metodológica**: esta documentación se construyó a partir del estado actual del repositorio en la fecha indicada arriba, e integra dos rondas de revisión:

1. **Ronda 1** (versión 2.0): las secciones 1, 2, 3, 5, 6 y 7 ya existían, elaboradas durante el taller de Arquitectura de Software, y se conservaron sin alterar su contenido validado. Las secciones 4, 8, 9, 10 y 11 se completaron para dejar el esqueleto arc42 completo de 12 capítulos. El glosario, originalmente numerado como capítulo 10, se renumeró a 12 para alinearse con la numeración estándar de arc42.
2. **Ronda 2** (versión 3.0, esta): el equipo agregó al repositorio 2 diagramas C4 (contexto y contenedores) y una carpeta [`Vista de Procesos (4+1)/`](<../../Vista de Procesos (4+1)>) (vistas lógica, de procesos, de desarrollo, física y de escenarios), que introducen autenticación (`Usuario`), integración con DIAN (facturación electrónica) e integración con proveedores. Esta ronda integra esa evidencia en las 12 secciones, resuelve varios de los `[POR DEFINIR]` de la ronda 1 (estilo arquitectónico, mecanismo de autenticación, contexto C1) y documenta explícitamente las inconsistencias que la nueva evidencia introduce frente a la documentación original (ver riesgos R-10 a R-14 en la sección 11).

Donde no hay evidencia suficiente, se indica `[POR DEFINIR]` o `[NO EVIDENCIADO EN EL REPOSITORIO]`. Donde se propone una decisión de arquitectura nueva (no evidenciada en ningún diagrama, sino añadida como criterio del arquitecto en esta revisión), se marca explícitamente como "decisión/métrica propuesta en esta revisión".

> ⚠️ **Aviso sobre el despliegue**: el sistema **no está desplegado en ninguna infraestructura cloud**. Para el desarrollo y la demostración académica, el sistema se ejecuta **localmente**. La arquitectura AWS (EC2 + RDS) que aparece en varias secciones de esta documentación (2, 3, 4, 7, 9) es una **propuesta teórica de despliegue futuro**, documentada como ejercicio de arquitectura del taller — no una infraestructura actualmente operativa. No existe URL de producción, instancia EC2, base de datos RDS activa, ni costos de AWS incurridos. Ver el detalle completo en [07. Vista de Despliegue](07_deployment_view.md).

## Índice

Este documento describe la arquitectura del **ERP Andina Motors (AndiMotors ERP)**, un sistema con 6 módulos (Inventario/Stock-Costos, Facturación, Compras, Empleados, EIS, ActivosFijos). Cada sección se desarrolla en un archivo independiente dentro de este directorio:

| Capítulo | Contenido | Enlace |
|---|---|---|
| 1. Introducción y Metas | Objetivo del ERP, requisitos de negocio y metas de calidad | [01_introduction_and_goals.md](01_introduction_and_goals.md) |
| 2. Restricciones de la Arquitectura | Decisiones tecnológicas (Java/Spring Boot, React, PostgreSQL, AWS, Docker) y restricciones organizativas | [02_architecture_constraints.md](02_architecture_constraints.md) |
| 3. Alcance y Contexto del Sistema | Actores del negocio, contexto técnico del sistema y diagrama de contexto C4 | [03_system_scope_and_context.md](03_system_scope_and_context.md) |
| 4. Estrategia de Solución | Decisiones tecnológicas y estructurales de alto nivel (estilo en capas, modularización, persistencia, seguridad, integración con DIAN/Proveedores) | [04_solution_strategy.md](04_solution_strategy.md) |
| 5. Vista de Bloques | Diagrama de Componentes, diagramas de Paquetes por módulo, estructura compuesta de Facturación, vista lógica (clases/objetos), diagramas C4 y Vista de Desarrollo en capas | [05_building_block_view.md](05_building_block_view.md) |
| 6. Vista de Ejecución | Escenarios de facturación y asignación de mecánico, más el escenario de venta con facturación electrónica DIAN (Vista de Procesos) | [06_runtime_view.md](06_runtime_view.md) |
| 7. Vista de Despliegue | Estado actual (ejecución local) y arquitectura de despliegue **propuesta** (no implementada): diagrama original + Vista Física extendida (Load Balancer, app móvil, DIAN, Proveedores) | [07_deployment_view.md](07_deployment_view.md) |
| 8. Conceptos Transversales | Seguridad (resuelta vía `Usuario`/`API Seguridad`), persistencia, comunicación entre módulos, validaciones, integración con sistemas externos | [08_crosscutting_concepts.md](08_crosscutting_concepts.md) |
| 9. Decisiones de Arquitectura | Decisiones documentadas, inferidas y propuestas (backend único, BD compartida, `ItemVendible`, arquitectura en capas, autenticación, DIAN, app móvil, Load Balancer) | [09_architecture_decisions.md](09_architecture_decisions.md) |
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
| Despliegue (arquitectura **propuesta**, no operativa — ver [07](07_deployment_view.md)) | [diagrama_despliegue.plantuml](../diagramas/plantuml/diagrama_despliegue.plantuml) | [diagrama_despliegue.png](../diagramas/img/diagrama_despliegue.png) |
| Estructura compuesta (caja blanca de Facturación) | [diagrama estructura compuesta](<../diagramas/plantuml/diagrama estructura compuesta>) | [Diagrama estructura compuesta.png](<../diagramas/img/Diagrama estructura compuesta.png>) |
| Paquetes — Inventario (Stock/Costos) | [diagrama_paquetes_inventario.plantuml](../diagramas/plantuml/diagrama_paquetes_inventario.plantuml) | [diagrama_paquetes_inventario.png](../diagramas/img/diagrama_paquetes_inventario.png) |
| Paquetes — Facturación | [diagrama_paquetes_facturacion.plantuml](../diagramas/plantuml/diagrama_paquetes_facturacion.plantuml) | [diagrama_paquetes_facturacion.png](../diagramas/img/diagrama_paquetes_facturacion.png) |
| Paquetes — Compras | [diagrama_paquetes_compras.plantuml](../diagramas/plantuml/diagrama_paquetes_compras.plantuml) | [diagrama_paquetes_compras.png](../diagramas/img/diagrama_paquetes_compras.png) |
| Paquetes — Empleados | [diagrama_paquetes_empleados.plantuml](../diagramas/plantuml/diagrama_paquetes_empleados.plantuml) | [diagrama_paquetes_empleados.png](../diagramas/img/diagrama_paquetes_empleados.png) |
| Paquetes — EIS | [diagrama_paquetes_eis.plantuml](../diagramas/plantuml/diagrama_paquetes_eis.plantuml) | [diagrama_paquetes_eis.png](../diagramas/img/diagrama_paquetes_eis.png) |
| Paquetes — ActivosFijos | [diagrama_paquetes_activosfijos.plantuml](../diagramas/plantuml/diagrama_paquetes_activosfijos.plantuml) | [diagrama_paquetes_activosfijos.png](../diagramas/img/diagrama_paquetes_activosfijos.png) |

> El archivo fuente del diagrama de estructura compuesta se llama literalmente `diagrama estructura compuesta` (sin extensión `.plantuml`, con espacios en el nombre), a diferencia del resto que usa `snake_case.plantuml`. Se referencia tal cual está en el repositorio, sin renombrarlo.

### Diagramas C4 (agregados posteriormente)

| Diagrama | Fuente | Imagen |
|---|---|---|
| C4 — Contexto (Nivel 1) | [diagrama_c4_contexto.plantuml](../diagramas/plantuml/diagrama_c4_contexto.plantuml) | [diagrama_c4_contexto.png](../diagramas/img/diagrama_c4_contexto.png) |
| C4 — Contenedores (Nivel 2) | [diagrama_c4_contenedores.plantuml](../diagramas/plantuml/diagrama_c4_contenedores.plantuml) | [diagrama_c4_contenedores.png](../diagramas/img/diagrama_c4_contenedores.png) |

### Vista de Procesos (4+1) — agregada posteriormente

Carpeta [`Vista de Procesos (4+1)/`](<../../Vista de Procesos (4+1)>), en la raíz del repositorio (fuera de `docs/`, tal como el equipo la agregó):

| Vista | Imagen |
|---|---|
| 1. Lógica | [1. vista logica.jpg](<../../Vista de Procesos (4+1)/1. vista logica.jpg>) |
| 2. Procesos | [2. vista de procesos.jpg](<../../Vista de Procesos (4+1)/2. vista de procesos.jpg>) |
| 3. Desarrollo | [3. vista de desarrollo.jpg](<../../Vista de Procesos (4+1)/3. vista de desarrollo.jpg>) |
| 4. Física | [4. vista fisica.jpg](<../../Vista de Procesos (4+1)/4. vista fisica.jpg>) |
| Escenarios | [Vista de escenarios.jpg](<../../Vista de Procesos (4+1)/Vista de escenarios.jpg>) |

⚠️ Esta vista introduce elementos (DIAN, app móvil, integración con proveedores) que `alcance.md` declaraba originalmente fuera de alcance, y renombra varias entidades/roles frente a los 11 diagramas UML y al C4 de contexto. Ver el análisis completo de estas diferencias en [05.4.1](05_building_block_view.md#541-vista-lógica-actualizada-agregada-posteriormente) y los riesgos R-10 a R-14 en [11. Riesgos y Deuda Técnica](11_risks_and_technical_debt.md).
