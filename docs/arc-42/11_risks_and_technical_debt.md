[← Volver al índice](arc42-template-ES.md)

# 11. Riesgos y Deuda Técnica

> Nota metodológica: esta sección no formaba parte del alcance original del taller (ver nota en [arc42-template-ES.md](arc42-template-ES.md)). Solo se listan riesgos y deuda con evidencia concreta en el repositorio; no se inventan problemas para completar la sección.

## R-01 — Ausencia de implementación (solo documentación y diagramas)

- **Tipo**: riesgo de mantenimiento / de proyecto.
- **Evidencia**: el repositorio no contiene código fuente, archivos de build (`pom.xml`, `package.json`), Dockerfile ni configuración de aplicación — solo `docs/` y archivos de nivel raíz.
- **Probabilidad**: alta (es el estado actual, no una posibilidad futura).
- **Impacto**: alto si el proyecto debe traducirse a una implementación funcional dentro del tiempo restante del semestre (restricción de tiempo, ver [02. Restricciones](02_architecture_constraints.md#22-restricciones-organizativas)).
- **Mitigación sugerida**: [POR DEFINIR — no corresponde a esta documentación proponer un plan de desarrollo; se deja como hallazgo.]

## R-02 — Rol "Jefe de taller" exigido por RNF-2 pero no modelado en el diagrama de clases

- **Tipo**: riesgo arquitectónico (inconsistencia entre requisito y modelo).
- **Evidencia**: RNF-2 en `requisitos/No funcionales.md` autoriza a "Mecánico" o "Jefe de taller" a cerrar órdenes de servicio, pero el [diagrama de clases](../diagramas/plantuml/diagrama_clases.plantuml) no incluye una clase `JefeDeTaller` (ya señalado como pendiente en [01.4](01_introduction_and_goals.md#14-stakeholders) y [03.1](03_system_scope_and_context.md#31-contexto-de-negocio)).
- **Probabilidad**: alta (inconsistencia ya presente).
- **Impacto**: medio — puede generar ambigüedad al implementar el control de acceso de RNF-2.
- **Mitigación sugerida**: [POR DEFINIR]
- ⚠️ Posible inconsistencia entre diagrama de clases y requisito no funcional.

## R-03 — Mecanismo de autenticación/autorización no definido pese a exigirlo RNF-2

- **Tipo**: riesgo de seguridad.
- **Evidencia**: no hay clase, componente ni decisión documentada sobre cómo se autentican los usuarios o cómo se verifica su rol (ver [8.1](08_crosscutting_concepts.md#81-seguridad-y-control-de-acceso) y [AD-06](09_architecture_decisions.md#ad-06--mecanismo-de-autenticación-y-autorización)).
- **Probabilidad**: alta.
- **Impacto**: alto — RNF-2 es una meta de calidad explícita y no tiene una estrategia arquitectónica que la respalde.
- **Mitigación sugerida**: [POR DEFINIR]

## R-04 — Despliegue de nodo único (propuesto) frente a metas de escalabilidad y disponibilidad

- **Tipo**: riesgo arquitectónico.
- **Evidencia**: el [diagrama de despliegue](../diagramas/plantuml/diagrama_despliegue.plantuml) (arquitectura **propuesta**, no desplegada) y `alcance.md` modelan explícitamente **una sola sucursal cliente conectada a un único servidor**, sin balanceo de carga, redundancia ni zonas de disponibilidad documentadas. Sin embargo, RNF-3 exige soportar múltiples sucursales consultando el mismo inventario en tiempo real, y RNF-5 exige 99% de uptime mensual. Esta tensión existe incluso a nivel de propuesta, antes de considerar cualquier despliegue real.
- **Probabilidad**: alta (tensión ya presente entre lo modelado y lo requerido).
- **Impacto**: alto si, en un eventual despliegue futuro, el sistema debe escalar a más de una sucursal o garantizar el uptime declarado sin cambios de infraestructura. No aplica al entorno local actual, que no tiene meta de uptime ni de multi-sucursal declarada.
- **Mitigación sugerida**: [POR DEFINIR]
- ⚠️ Posible inconsistencia entre el diagrama de despliegue propuesto/alcance (una sucursal) y RNF-3 (multi-sucursal).

## R-09 — Costos de infraestructura cloud

- **Tipo**: riesgo de proyecto / económico.
- **Descripción**: un despliegue real en AWS (según la arquitectura propuesta en [AD-07](09_architecture_decisions.md#ad-07--propuesta-de-despliegue-cloud-aws)) podría generar costos asociados al uso de servicios de cómputo (EC2), almacenamiento, base de datos (RDS), transferencia de datos y otros recursos.
- **Evidencia**: `alcance.md` declara explícitamente que el proyecto es académico y no maneja un presupuesto monetario real; el "costo" del taller se mide en horas/persona del equipo, no en gasto de infraestructura.
- **Probabilidad**: baja mientras el proyecto se mantenga en fase académica (el equipo ya decidió no desplegar en AWS); alta si en el futuro se decide materializar la propuesta sin planificación de costos.
- **Impacto**: incremento innecesario del costo del proyecto académico si se desplegara la propuesta sin justificación real de negocio.
- **Mitigación sugerida**: mantener la ejecución local durante el desarrollo y la demostración académica, documentando AWS únicamente como una propuesta teórica de despliegue (ver [07. Vista de Despliegue](07_deployment_view.md)) hasta que exista una necesidad real que justifique el gasto.

## R-05 — Componente ActivosFijos sin interfaces con otros componentes

- **Tipo**: deuda técnica / riesgo de integración.
- **Evidencia**: el [diagrama de componentes](../diagramas/plantuml/diagrama_componentes.plantuml) no modela ninguna dependencia entrante ni saliente entre ActivosFijos y el resto de módulos (ya señalado en [5.1](05_building_block_view.md#51-sistema-general-de-caja-blanca-nivel-1--diagrama-de-componentes)).
- **Probabilidad**: media.
- **Impacto**: bajo-medio — el módulo puede estar efectivamente aislado del resto del ERP, o simplemente no se ha modelado su integración.
- **Mitigación sugerida**: [POR DEFINIR]

## R-06 — Historia de usuario HU-22 (depreciación) marcada "Won't" pero modelada en el diagrama de paquetes

- **Tipo**: deuda de alcance / inconsistencia entre backlog y diagrama.
- **Evidencia**: `product-backlog.md` marca HU-22 ("calcular automáticamente la depreciación de los activos fijos") como **Won't have (este proyecto)**. Sin embargo, el [diagrama de paquetes de ActivosFijos](../diagramas/plantuml/diagrama_paquetes_activosfijos.plantuml) sí incluye el caso de uso `CalcularDepreciacion` dentro del paquete `ControlActivos`.
- **Probabilidad**: alta (inconsistencia ya presente en los documentos).
- **Impacto**: bajo — es una discrepancia de alcance documental, no un defecto de ejecución.
- **Mitigación sugerida**: [POR DEFINIR — decidir si `CalcularDepreciacion` se retira del diagrama de paquetes o si se reclasifica la historia de usuario.]
- ⚠️ Posible inconsistencia entre el Product Backlog (HU-22 = Won't) y el diagrama de paquetes de ActivosFijos (incluye `CalcularDepreciacion`).

## R-07 — Contenerización con Docker declarada como tecnología pero sin evidencia de implementación

- **Tipo**: deuda técnica.
- **Evidencia**: `requisitos/No funcionales.md` lista Docker como herramienta de despliegue, pero no existe Dockerfile, `docker-compose.yml` ni configuración de contenedores en el repositorio (ver [AD-05](09_architecture_decisions.md#ad-05--contenerización-del-backend-con-docker)).
- **Probabilidad**: alta.
- **Impacto**: bajo (aún no hay código que contenerizar).
- **Mitigación sugerida**: [POR DEFINIR]

## R-08 — Nombre de archivo inconsistente para el diagrama de estructura compuesta

- **Tipo**: deuda técnica menor (convención de nombres).
- **Evidencia**: el archivo fuente vive en `docs/diagramas/plantuml/diagrama estructura compuesta`, sin extensión `.plantuml` y con espacios, a diferencia del resto de diagramas que usan `snake_case.plantuml` (ya señalado en el [README.md](../../README.md) raíz).
- **Probabilidad**: alta (ya presente).
- **Impacto**: bajo — puede causar errores al automatizar la generación de imágenes o al referenciar el archivo en herramientas sensibles a rutas con espacios.
- **Mitigación sugerida**: [POR DEFINIR — el README ya documenta la decisión deliberada de no renombrarlo para no modificar archivos de diagramas.]

## Atributos/riesgos no evidenciados

No se encontró evidencia suficiente en el repositorio para evaluar riesgos de rendimiento en producción, riesgos de dependencias de terceros (librerías), o deuda técnica de código (no existe código fuente). [POR DEFINIR — no se encontró evidencia suficiente en el repositorio.]

---
[← Anterior: Requisitos de Calidad](10_quality_requirements.md) · [Volver al índice](arc42-template-ES.md) · [Siguiente: Glosario →](12_glossary.md)
