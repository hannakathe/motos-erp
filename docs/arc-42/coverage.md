[← Volver al índice](arc42-template-ES.md)

# Matriz de Cobertura

Estado de completitud de cada sección arc42, evaluado según la evidencia disponible en el repositorio a la fecha de esta documentación (2026-08-26).

| Sección | Estado | Evidencia | Pendientes |
|---|---|---|---|
| 1. Introducción y Metas | Completa | [01_introduction_and_goals.md](01_introduction_and_goals.md), README.md, alcance.md, requisitos/funcionales.md, requisitos/No funcionales.md | Rol "Jefe de taller" mencionado en RNF-2 pero no modelado como stakeholder/clase formal. |
| 2. Restricciones de Arquitectura | Parcial | [02_architecture_constraints.md](02_architecture_constraints.md), requisitos/No funcionales.md, alcance.md | Sin convenciones de código/nomenclatura documentadas (2.3). |
| 3. Alcance y Contexto del Sistema | Parcial | [03_system_scope_and_context.md](03_system_scope_and_context.md), diagrama de clases, diagrama de despliegue | No hay diagrama de contexto (C1) dedicado; se reconstruye a partir de otros diagramas. Sin sistemas externos modelados. |
| 4. Estrategia de Solución | Parcial | [04_solution_strategy.md](04_solution_strategy.md), diagrama de despliegue, diagrama de componentes, requisitos/No funcionales.md | Estilo arquitectónico y estrategia de rendimiento/usabilidad son inferencias o están [POR DEFINIR]. |
| 5. Vista de Bloques | Completa | [05_building_block_view.md](05_building_block_view.md), diagrama de componentes, 6 diagramas de paquetes, estructura compuesta, diagrama de clases/objetos | Interfaces de ActivosFijos con otros componentes no modeladas. Sin diagrama de clases para Compras, Empleados, EIS, ActivosFijos. |
| 6. Vista de Ejecución | Parcial | [06_runtime_view.md](06_runtime_view.md), diagrama de estructura compuesta, diagrama de componentes | No hay diagrama de secuencia dedicado; los escenarios se narran a partir de otros diagramas. Solo 3 escenarios cubiertos (venta, orden de taller, asignación de mecánico); Compras, EIS y ActivosFijos sin escenario propio. |
| 7. Vista de Despliegue | Parcial | [07_deployment_view.md](07_deployment_view.md), diagrama de despliegue | Sin Nivel 2 (contenedores Docker, balanceo de carga, zonas de disponibilidad). Corregida para distinguir explícitamente estado actual (ejecución local, implementado) de arquitectura AWS propuesta (no implementada); sin procedimiento documentado paso a paso del entorno local. |
| 8. Conceptos Transversales | Parcial | [08_crosscutting_concepts.md](08_crosscutting_concepts.md), requisitos/No funcionales.md, sprint-1-planning.md | Autenticación/autorización, logging, auditoría y configuración sin evidencia — [POR DEFINIR]. |
| 9. Decisiones de Arquitectura | Parcial | [09_architecture_decisions.md](09_architecture_decisions.md) | No hay ADRs históricos en el repositorio; todas las decisiones fueron reconstruidas (documentadas o inferidas) para este esqueleto. Mecanismo de autenticación pendiente. |
| 10. Requisitos de Calidad | Completa | [10_quality_requirements.md](10_quality_requirements.md), requisitos/No funcionales.md | Métricas concretas de RNF-2 y RNF-3 no están definidas en la fuente. |
| 11. Riesgos y Deuda Técnica | Parcial | [11_risks_and_technical_debt.md](11_risks_and_technical_debt.md) | Mitigaciones concretas no definidas (fuera del alcance de esta documentación). |
| 12. Glosario | Completa | [12_glossary.md](12_glossary.md), diagrama de clases, diagrama de componentes, product-backlog.md, antecedentes.md | — |

## Leyenda de estados

- **Completa**: la sección cubre todo lo que el repositorio puede evidenciar para ese capítulo arc42, sin vacíos relevantes conocidos.
- **Parcial**: la sección tiene contenido evidenciado, pero incluye partes marcadas `[POR DEFINIR]` o `[NO EVIDENCIADO EN EL REPOSITORIO]`.
- **Por definir**: no hay evidencia suficiente para desarrollar la sección más allá de una nota.
- **No aplicable**: no aplica a este proyecto (no se usó este estado en esta matriz).

---
[Volver al índice](arc42-template-ES.md)
