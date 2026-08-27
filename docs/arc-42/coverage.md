[← Volver al índice](arc42-template-ES.md)

# Matriz de Cobertura

Estado de completitud de cada sección arc42, evaluado según la evidencia disponible en el repositorio a la fecha de esta documentación (2026-08-26).

| Sección | Estado | Evidencia | Pendientes |
|---|---|---|---|
| 1. Introducción y Metas | Completa | [01_introduction_and_goals.md](01_introduction_and_goals.md), README.md, alcance.md, requisitos/funcionales.md, requisitos/No funcionales.md, C4 contexto, Vista de Escenarios | Rol "Jefe de taller" resuelto como valor de `Usuario.rol` (decisión propuesta en esta revisión, sin clase dedicada). Variantes de nombre entre generaciones de diagramas documentadas (R-10). |
| 2. Restricciones de Arquitectura | Completa | [02_architecture_constraints.md](02_architecture_constraints.md), requisitos/No funcionales.md, alcance.md, Vista 4+1, C4 | Convenciones de código propuestas en esta revisión (2.3), no verificadas en código real (no existe código fuente). |
| 3. Alcance y Contexto del Sistema | Completa | [03_system_scope_and_context.md](03_system_scope_and_context.md), diagrama de clases, diagrama de despliegue, C4 contexto/contenedores, Vista Física, Vista de Escenarios | C4 de contexto no incluye a DIAN (solo Fábrica/Distribuidor) — inconsistencia entre fuentes del propio repositorio (R-11). |
| 4. Estrategia de Solución | Completa | [04_solution_strategy.md](04_solution_strategy.md), diagrama de despliegue, diagrama de componentes, requisitos/No funcionales.md, Vista de Desarrollo | Estrategia de rendimiento (RNF-1) y usabilidad (RNF-4) siguen [POR DEFINIR] — ningún diagrama nuevo las cubre. |
| 5. Vista de Bloques | Completa | [05_building_block_view.md](05_building_block_view.md), diagrama de componentes, 6 diagramas de paquetes, estructura compuesta, diagrama de clases/objetos, C4, Vista de Desarrollo, Vista Lógica actualizada | Interfaces de ActivosFijos con otros componentes siguen sin modelar en ninguna de las dos generaciones de diagramas. Dos modelos de clases incompatibles coexisten sin conciliar (R-14). |
| 6. Vista de Ejecución | Completa | [06_runtime_view.md](06_runtime_view.md), diagrama de estructura compuesta, diagrama de componentes, Vista de Procesos | Compras, EIS y ActivosFijos siguen sin escenario de secuencia propio (ni en la Vista de Procesos, que solo cubre Ventas/Facturación). |
| 7. Vista de Despliegue | Completa | [07_deployment_view.md](07_deployment_view.md), diagrama de despliegue, Vista Física, C4 contenedores | Sin dimensionamiento de infraestructura (tipo de instancia, réplicas, zonas de disponibilidad). Ambigüedad AWS vs. AWS/Azure entre diagramas (R-12). Sigue sin procedimiento documentado paso a paso del entorno local. |
| 8. Conceptos Transversales | Completa | [08_crosscutting_concepts.md](08_crosscutting_concepts.md), requisitos/No funcionales.md, sprint-1-planning.md, Vista Lógica actualizada, Vista de Desarrollo | Logging, auditoría y configuración siguen sin evidencia — [POR DEFINIR]. Protocolo de autenticación concreto (JWT/sesiones) y algoritmo de hashing sin definir. |
| 9. Decisiones de Arquitectura | Completa | [09_architecture_decisions.md](09_architecture_decisions.md) | No hay ADRs históricos en el repositorio; todas las decisiones fueron reconstruidas (documentadas o inferidas) o propuestas en esta revisión. AD-10 y AD-11 (DIAN, app móvil) contradicen el alcance original — documentado como tal. |
| 10. Requisitos de Calidad | Completa | [10_quality_requirements.md](10_quality_requirements.md), requisitos/No funcionales.md | Métricas de RNF-2 y RNF-3 son propuestas de esta revisión, explícitamente marcadas como no evidenciadas en la fuente original. |
| 11. Riesgos y Deuda Técnica | Completa | [11_risks_and_technical_debt.md](11_risks_and_technical_debt.md) | 14 riesgos documentados con mitigación sugerida cada uno; ninguna mitigación fue ejecutada (son recomendaciones, no cambios de código). |
| 12. Glosario | Completa | [12_glossary.md](12_glossary.md), diagrama de clases, diagrama de componentes, product-backlog.md, antecedentes.md, Vista Lógica actualizada, C4 | — |

## Leyenda de estados

- **Completa**: la sección cubre todo lo que el repositorio puede evidenciar para ese capítulo arc42, sin vacíos relevantes conocidos.
- **Parcial**: la sección tiene contenido evidenciado, pero incluye partes marcadas `[POR DEFINIR]` o `[NO EVIDENCIADO EN EL REPOSITORIO]`.
- **Por definir**: no hay evidencia suficiente para desarrollar la sección más allá de una nota.
- **No aplicable**: no aplica a este proyecto (no se usó este estado en esta matriz).

---
[Volver al índice](arc42-template-ES.md)
