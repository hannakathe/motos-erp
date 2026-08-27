[← Volver al índice](arc42-template-ES.md)

# 11. Riesgos y Deuda Técnica

> Nota metodológica: esta sección no formaba parte del alcance original del taller (ver nota en [arc42-template-ES.md](arc42-template-ES.md)). Solo se listan riesgos y deuda con evidencia concreta en el repositorio; no se inventan problemas para completar la sección.

## R-01 — Ausencia de implementación (solo documentación y diagramas)

- **Tipo**: riesgo de mantenimiento / de proyecto.
- **Evidencia**: el repositorio no contiene código fuente, archivos de build (`pom.xml`, `package.json`), Dockerfile ni configuración de aplicación — solo `docs/`, la carpeta `Vista de Procesos (4+1)/` y archivos de nivel raíz.
- **Probabilidad**: alta (es el estado actual, no una posibilidad futura).
- **Impacto**: alto si el proyecto debe traducirse a una implementación funcional dentro del tiempo restante del semestre (restricción de tiempo, ver [02. Restricciones](02_architecture_constraints.md#22-restricciones-organizativas)).
- **Mitigación sugerida**: priorizar la implementación siguiendo el orden ya definido en [product-backlog.md](../product-backlog.md) (Must → Should → Could), comenzando por el Sprint 1 ya planificado (épica Compras, ver [sprint-1-planning.md](../sprint/sprint-1-planning.md)); usar la arquitectura en capas de [AD-08](09_architecture_decisions.md#ad-08--arquitectura-en-capas-apidominiopersistencia) como guía de estructura de paquetes del código.

## R-02 — Rol "Jefe de taller" exigido por RNF-2 pero no modelado como clase propia

- **Tipo**: riesgo arquitectónico (inconsistencia entre requisito y modelo).
- **Evidencia**: RNF-2 en `requisitos/No funcionales.md` autoriza a "Mecánico" o "Jefe de taller" a cerrar órdenes de servicio; ni el [diagrama de clases](../diagramas/plantuml/diagrama_clases.plantuml) original ni la Vista Lógica ni la Vista de Escenarios (agregadas después) incluyen una clase o actor `JefeDeTaller`.
- **Probabilidad**: alta (inconsistencia ya presente en ambas generaciones de diagramas).
- **Impacto**: medio — puede generar ambigüedad al implementar el control de acceso de RNF-2.
- **Mitigación aplicada en esta revisión**: se resuelve arquitectónicamente como un valor del atributo `Usuario.rol` (ver [AD-09](09_architecture_decisions.md#ad-09--autenticación-y-autorización-basada-en-la-entidad-usuario) y [01.4](01_introduction_and_goals.md#14-stakeholders)) — **decisión de diseño propuesta en esta revisión, no evidenciada en ningún diagrama del repositorio**; sigue sin existir una clase `JefeDeTaller` dibujada.
- ⚠️ Posible inconsistencia entre diagramas (dos generaciones) y requisito no funcional.

## R-03 — Mecanismo de autenticación/autorización no definido pese a exigirlo RNF-2

- **Tipo**: riesgo de seguridad.
- **Evidencia histórica**: en la primera versión de esta documentación, no existía clase, componente ni decisión documentada sobre autenticación/autorización.
- **Estado actual**: **mitigado a nivel de diseño** — la Vista Lógica agrega `Usuario` (`rol`, `passwordHash`) y la Vista de Desarrollo agrega la capa `API Seguridad` (ver [8.1](08_crosscutting_concepts.md#81-seguridad-y-control-de-acceso) y [AD-09](09_architecture_decisions.md#ad-09--autenticación-y-autorización-basada-en-la-entidad-usuario)).
- **Probabilidad**: media (el diseño existe, pero no hay implementación ni protocolo concreto definido).
- **Impacto**: medio — RNF-2 ahora tiene una estrategia arquitectónica de referencia, pero sigue sin implementación verificable, protocolo de autenticación (JWT/sesiones) ni algoritmo de hashing.
- **Mitigación sugerida**: al implementar, adoptar Spring Security con JWT (consistente con el stack Java/Spring Boot ya seleccionado) y bcrypt para `passwordHash` — **recomendación de esta revisión**, no una decisión ya tomada por el equipo.

## R-04 — Despliegue de nodo único (propuesto) frente a metas de escalabilidad y disponibilidad

- **Tipo**: riesgo arquitectónico.
- **Evidencia**: el [diagrama de despliegue](../diagramas/plantuml/diagrama_despliegue.plantuml) (arquitectura **propuesta**, no desplegada) y `alcance.md` modelan explícitamente **una sola sucursal cliente conectada a un único servidor**, sin balanceo de carga, redundancia ni zonas de disponibilidad documentadas. Sin embargo, RNF-3 exige soportar múltiples sucursales consultando el mismo inventario en tiempo real, y RNF-5 exige 99% de uptime mensual. Esta tensión existe incluso a nivel de propuesta, antes de considerar cualquier despliegue real.
- **Probabilidad**: alta (tensión ya presente entre lo modelado y lo requerido).
- **Impacto**: alto si, en un eventual despliegue futuro, el sistema debe escalar a más de una sucursal o garantizar el uptime declarado sin cambios de infraestructura. No aplica al entorno local actual, que no tiene meta de uptime ni de multi-sucursal declarada.
- **Mitigación aplicada en esta revisión**: la Vista Física agrega un Load Balancer ([AD-12](09_architecture_decisions.md#ad-12--load-balancer-en-la-arquitectura-de-despliegue-propuesta)), que mitiga parcialmente la ausencia de balanceo de carga, pero **sigue sin modelar múltiples sucursales cliente ni zonas de disponibilidad** — el riesgo de fondo (soporte multi-sucursal) permanece abierto.
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
- **Mitigación sugerida**: definir explícitamente si ActivosFijos consume `consultarHistoricoVentas()` de EIS (patrón ya usado por Compras) o si permanece aislado por diseño; documentarlo en una futura revisión del diagrama de componentes. No se resuelve en esta revisión por falta de evidencia en cualquiera de las dos generaciones de diagramas (ver [5.1](05_building_block_view.md#51-sistema-general-de-caja-blanca-nivel-1--diagrama-de-componentes)).

## R-06 — Historia de usuario HU-22 (depreciación) marcada "Won't" pero modelada en el diagrama de paquetes

- **Tipo**: deuda de alcance / inconsistencia entre backlog y diagrama.
- **Evidencia**: `product-backlog.md` marca HU-22 ("calcular automáticamente la depreciación de los activos fijos") como **Won't have (este proyecto)**. Sin embargo, el [diagrama de paquetes de ActivosFijos](../diagramas/plantuml/diagrama_paquetes_activosfijos.plantuml) sí incluye el caso de uso `CalcularDepreciacion` dentro del paquete `ControlActivos`.
- **Probabilidad**: alta (inconsistencia ya presente en los documentos).
- **Impacto**: bajo — es una discrepancia de alcance documental, no un defecto de ejecución.
- **Mitigación sugerida**: reclasificar HU-22 a "Could have" o a un backlog de siguiente iteración en vez de "Won't have", dado que `CalcularDepreciacion` ya está modelada como caso de uso — mantenerla como "Won't" mientras el diagrama la sigue incluyendo genera expectativas contradictorias entre el backlog y la arquitectura.
- ⚠️ Posible inconsistencia entre el Product Backlog (HU-22 = Won't) y el diagrama de paquetes de ActivosFijos (incluye `CalcularDepreciacion`).

## R-07 — Contenerización con Docker declarada como tecnología pero sin evidencia de implementación

- **Tipo**: deuda técnica.
- **Evidencia**: `requisitos/No funcionales.md` lista Docker como herramienta de despliegue, pero no existe Dockerfile, `docker-compose.yml` ni configuración de contenedores en el repositorio (ver [AD-05](09_architecture_decisions.md#ad-05--contenerización-del-backend-con-docker)).
- **Probabilidad**: alta.
- **Impacto**: bajo (aún no hay código que contenerizar).
- **Mitigación sugerida**: al iniciar la implementación, crear un `Dockerfile` para el backend y un `docker-compose.yml` que levante backend + PostgreSQL localmente — esto también facilitaría reemplazar la falta de documentación operativa señalada en [07.1](07_deployment_view.md#71-estado-actual).

## R-08 — Nombre de archivo inconsistente para el diagrama de estructura compuesta

- **Tipo**: deuda técnica menor (convención de nombres).
- **Evidencia**: el archivo fuente vive en `docs/diagramas/plantuml/diagrama estructura compuesta`, sin extensión `.plantuml` y con espacios, a diferencia del resto de diagramas que usan `snake_case.plantuml` (ya señalado en el [README.md](../../README.md) raíz).
- **Probabilidad**: alta (ya presente).
- **Impacto**: bajo — puede causar errores al automatizar la generación de imágenes o al referenciar el archivo en herramientas sensibles a rutas con espacios.
- **Mitigación sugerida**: renombrar a `diagrama_estructura_compuesta.plantuml` en una futura limpieza del repositorio, actualizando las referencias en README.md y en esta documentación arc42 — no se aplica en esta revisión para no alterar archivos de diagramas fuera del alcance solicitado.

## R-10 — Variantes de nombre para el mismo rol/entidad entre generaciones de diagramas

- **Tipo**: deuda de documentación / inconsistencia de nomenclatura.
- **Evidencia**: la Vista 4+1 y los diagramas C4 (agregados después de los 11 diagramas UML originales) renombran varios roles y entidades sin conciliar con los nombres originales: `Mecanico` → "Técnico"; "Gerente" (product-backlog.md) → actor `gerente` (C4) → "Administrador" (Vista de Escenarios); "encargado de compras" (product-backlog.md, sprint-1-planning.md) → "Jefe de Compras" (Vista de Escenarios); `Moto` → `Vehiculo`; `Factura`/`DetalleFactura` → `Venta`/`DetalleVenta` + `Factura` (reestructurada); `OrdenTaller` → `OrdenServicio`.
- **Probabilidad**: alta (ya presente en el repositorio).
- **Impacto**: medio — genera ambigüedad sobre cuál es el nombre "oficial" de cada entidad/rol al momento de implementar, y dificulta la trazabilidad entre requisitos (que usan los nombres originales: RF, RNF, HU) y los diagramas más recientes.
- **Mitigación sugerida**: elaborar una tabla de equivalencias única (ya iniciada en [01.4](01_introduction_and_goals.md#14-stakeholders) y [5.4.1](05_building_block_view.md#541-vista-lógica-actualizada-agregada-posteriormente) en esta revisión) y, en una futura iteración, decidir y fijar un único nombre por concepto en todos los documentos.

## R-11 — Integración con DIAN y Proveedores modelada en diagramas nuevos, pero ausente en los diagramas originales

- **Tipo**: riesgo arquitectónico / inconsistencia de alcance.
- **Evidencia**: la Vista Lógica, la Vista de Procesos, la Vista Física y la Vista de Escenarios modelan integración con DIAN (facturación electrónica) y con un sistema de Proveedores (compras B2B) — ver [AD-10](09_architecture_decisions.md#ad-10--integración-con-dian-para-facturación-electrónica) y [4.7](04_solution_strategy.md#47-integración-con-sistemas-externos-dian-y-proveedores). Ni el diagrama de componentes original ni el diagrama de clases original ni el diagrama de despliegue original modelan estos dos sistemas externos. `alcance.md` declaraba ambos explícitamente fuera de alcance (actualizado en esta revisión, ver nota en ese documento).
- **Probabilidad**: alta (inconsistencia ya presente entre las dos generaciones de diagramas).
- **Impacto**: alto — introduce dependencias externas reales (una entidad tributaria y proveedores) sin que el resto de la documentación (requisitos funcionales, diagrama de componentes, plan de pruebas) las contemple; el manejo de fallos de estas integraciones no está modelado en ningún lado.
- **Mitigación sugerida**: actualizar el diagrama de componentes y el diagrama de clases originales para incluir DIAN y Proveedores como componentes/actores externos formales, y agregar requisitos funcionales (RF) y casos de prueba específicos para estas integraciones, siguiendo el formato ya usado en [requisitos/funcionales.md](../requisitos/funcionales.md) y [sprint-1-planning.md](../sprint/sprint-1-planning.md).
- ⚠️ Inconsistencia entre dos generaciones de diagramas del mismo repositorio.

## R-12 — Ambigüedad de proveedor cloud (AWS vs. AWS/Azure) entre diagramas

- **Tipo**: deuda de documentación.
- **Evidencia**: [diagrama_despliegue.plantuml](../diagramas/plantuml/diagrama_despliegue.plantuml) y `requisitos/No funcionales.md` especifican AWS (EC2 + RDS) exclusivamente; la Vista Física ([`4. vista fisica.jpg`](<../../Vista de Procesos (4+1)/4. vista fisica.jpg>)), agregada después, etiqueta el proveedor cloud como "Proveedor Cloud (AWS/Azure)".
- **Probabilidad**: alta (inconsistencia ya presente).
- **Impacto**: bajo — ambas son solo propuestas de diseño, no infraestructura desplegada, pero la ambigüedad debilita la trazabilidad de la decisión tecnológica.
- **Mitigación sugerida**: decidir un único proveedor cloud objetivo y actualizar todas las fuentes (`No funcionales.md`, diagrama de despliegue, Vista Física) para que coincidan — o, si la intención es dejarlo abierto deliberadamente, declararlo explícitamente como una decisión de "proveedor cloud agnóstico" en la sección 9.

## R-13 — La Vista Lógica actualizada simplifica `Vehiculo` y podría perder trazabilidad por VIN

- **Tipo**: riesgo arquitectónico / regresión de requisito.
- **Evidencia**: RF-1.1.1 exige "Capturar número de VIN/chasis... necesario para trazabilidad individual"; el `diagrama_clases.plantuml` original modela `Moto.chasis` explícitamente. La Vista Lógica nueva reemplaza `Moto` por `Vehiculo` (`id`, `modelo`, `marca`, `precio`, `estado`), sin un atributo `chasis`/VIN explícito.
- **Probabilidad**: media — puede ser una omisión del diagrama (asumiendo que `id` cumple ese rol) más que una decisión deliberada de eliminar la trazabilidad por VIN.
- **Impacto**: alto si es una omisión real, porque RF-1.1.1 es un requisito Must (HU-01, ver [product-backlog.md](../product-backlog.md)) y la trazabilidad por VIN es parte de la propuesta de valor del sistema frente a los DMS del mercado (ver [antecedentes.md](../antecedentes.md), columna "Trazabilidad por unidad").
- **Mitigación sugerida**: aclarar con el equipo si `Vehiculo.id` es el VIN/chasis (y renombrarlo explícitamente para que quede claro) o si se agregó por error sin `chasis`; no se asume ninguna de las dos opciones en esta revisión.

## R-14 — Dos modelos de clases incompatibles coexisten sin conciliar

- **Tipo**: deuda técnica / riesgo de mantenimiento.
- **Evidencia**: `diagrama_clases.plantuml` (original, parte de los 11 diagramas UML del taller) y la Vista Lógica nueva (`Vista de Procesos (4+1)/1. vista logica.jpg`) modelan el mismo dominio de negocio con estructuras, nombres y atributos distintos (ver tabla comparativa completa en [5.4.1](05_building_block_view.md#541-vista-lógica-actualizada-agregada-posteriormente)). Ningún documento del repositorio indica cuál reemplaza a cuál, o si ambas deben fusionarse.
- **Probabilidad**: alta (ya presente).
- **Impacto**: alto si el proyecto avanza a implementación — el equipo de desarrollo no tendría un único modelo de datos autoritativo del cual partir.
- **Mitigación sugerida**: en la siguiente sesión del taller, decidir explícitamente cuál diagrama de clases es la fuente de verdad (o fusionar ambos en una sola versión conciliada) antes de iniciar cualquier implementación; documentar esa decisión como un ADR en la sección 9.

## Atributos/riesgos no evidenciados

No se encontró evidencia suficiente en el repositorio para evaluar riesgos de rendimiento en producción, riesgos de dependencias de terceros (librerías), o deuda técnica de código (no existe código fuente). [POR DEFINIR — no se encontró evidencia suficiente en el repositorio.]

---
[← Anterior: Requisitos de Calidad](10_quality_requirements.md) · [Volver al índice](arc42-template-ES.md) · [Siguiente: Glosario →](12_glossary.md)
