[← Volver al índice](arc42-template-ES.md)

# 2. Restricciones de la Arquitectura

## 2.1 Restricciones técnicas

Tecnologías seleccionadas, de [requisitos/No funcionales.md](<../requisitos/No funcionales.md>) y confirmadas por el [diagrama de despliegue](../diagramas/plantuml/diagrama_despliegue.plantuml):

| Categoría | Tecnología | Justificación | Estado |
|---|---|---|---|
| Backend | Java + Spring Boot | Robusto para lógica transaccional (facturación, control de stock). | Tecnología seleccionada para desarrollo local. |
| Frontend | React | Interfaz ágil para vendedores y mecánicos. | Tecnología seleccionada para desarrollo local. |
| Base de datos | PostgreSQL | Relacional, ideal para inventario con trazabilidad por VIN. | Tecnología seleccionada para desarrollo local. |
| Despliegue | AWS (EC2 + RDS) | Permite acceso multi-sucursal en tiempo real. | **Propuesto — no implementado.** No hay infraestructura AWS activa; ver [07. Vista de Despliegue](07_deployment_view.md). |
| Herramientas | Docker | Contenerización del backend para un despliegue reproducible en AWS EC2. | Propuesto — sin evidencia de Dockerfile en el repositorio. |

> **Aclaración**: AWS (EC2 + RDS) es la tecnología de despliegue **propuesta** por el equipo como ejercicio de arquitectura, no una infraestructura actualmente operativa. Para el desarrollo y la demostración académica del sistema, este se ejecuta **localmente**. Esto se traduce en 3 nodos físicos en la arquitectura **propuesta** (ver [Vista de Despliegue](07_deployment_view.md)): cliente en la sucursal (React), servidor cloud (API Spring Boot en AWS EC2) y servidor de base de datos (PostgreSQL en AWS RDS), comunicados por HTTPS y TCP/5432 respectivamente — ninguno de estos nodos cloud está actualmente desplegado.

## 2.1.1 Restricciones técnicas adicionales (Vista 4+1 y C4)

La carpeta [`Vista de Procesos (4+1)/`](<../../Vista de Procesos (4+1)>) y los diagramas [C4 de contexto](../diagramas/plantuml/diagrama_c4_contexto.plantuml)/[C4 de contenedores](../diagramas/plantuml/diagrama_c4_contenedores.plantuml), agregados por el equipo después de los 11 diagramas UML originales, introducen restricciones/decisiones técnicas adicionales que no estaban en `requisitos/No funcionales.md`:

| Elemento nuevo | Evidencia | Estado |
|---|---|---|
| Arquitectura en capas (API / Dominio / Persistencia) | [`3. vista de desarrollo.jpg`](<../../Vista de Procesos (4+1)/3. vista de desarrollo.jpg>) | Documentada — ver [4.2](04_solution_strategy.md#42-estilo-arquitectónico) y [AD-08](09_architecture_decisions.md#ad-08--arquitectura-en-capas-apidominiopersistencia). |
| Integración con DIAN (facturación electrónica, Colombia) | [`4. vista fisica.jpg`](<../../Vista de Procesos (4+1)/4. vista fisica.jpg>), Vista Lógica, Vista de Procesos, Vista de Escenarios | Propuesta — ver [AD-10](09_architecture_decisions.md#ad-10--integración-con-dian-para-facturación-electrónica). |
| App móvil para vendedores | [`4. vista fisica.jpg`](<../../Vista de Procesos (4+1)/4. vista fisica.jpg>) | Propuesta — contradice `alcance.md` original (ver [11. Riesgos — R-11](11_risks_and_technical_debt.md#r-11--integración-con-dian-y-proveedores-modelada-en-diagramas-nuevos-pero-ausente-en-los-diagramas-originales)). |
| Load Balancer | [`4. vista fisica.jpg`](<../../Vista de Procesos (4+1)/4. vista fisica.jpg>) | Propuesta — resuelve parcialmente el riesgo [R-04](11_risks_and_technical_debt.md#r-04--despliegue-de-nodo-único-propuesto-frente-a-metas-de-escalabilidad-y-disponibilidad). |
| Proveedor cloud "AWS/Azure" (en vez de solo AWS) | [`4. vista fisica.jpg`](<../../Vista de Procesos (4+1)/4. vista fisica.jpg>): "Proveedor Cloud (AWS/Azure)" | Inconsistente con `diagrama_despliegue.plantuml` y `No funcionales.md`, que especifican solo AWS — ver [11. Riesgos — R-12](11_risks_and_technical_debt.md#r-12--ambigüedad-de-proveedor-cloud-aws-vs-awsazure-entre-diagramas). |
| Integración con proveedores (Compras B2B) | [`4. vista fisica.jpg`](<../../Vista de Procesos (4+1)/4. vista fisica.jpg>), C4 contexto | Propuesta — contradice `alcance.md` original. |

## 2.2 Restricciones organizativas

De la triple restricción definida en [alcance.md](../alcance.md):

- **Tiempo**: el proyecto se desarrolla dentro de la duración del semestre académico, siguiendo el cronograma del taller de arquitectura de software (conceptos de arquitectura y modelo 4+1, elaboración de los 11 diagramas UML, entrega final con presentación de 10-15 minutos en inglés).
- **Costo**: proyecto académico sin presupuesto monetario real; el "costo" se mide en horas/persona del equipo (Hanna, Ingrid y Marlon), con reparto de tareas ya definido por integrante.
- **Alcance**: acotado a lo ya modelado en los 11 diagramas UML del equipo (ver detalle en [alcance.md](../alcance.md)); el desglose interno de la API REST en componentes más finos (Nivel 2/3 de C4) y la integración en tiempo real con proveedores o fábrica quedan fuera de este taller.

## 2.3 Convenciones

No hay ninguna convención de código, nomenclatura o estilo documentada explícitamente en el repositorio. Como parte de esta revisión, se propone la siguiente convención (**decisión de arquitectura propuesta, no impuesta previamente por el equipo**), alineada con las convenciones estándar de Java/Spring Boot y React ya seleccionadas ([2.1](#21-restricciones-técnicas)):

| Elemento | Convención propuesta | Ejemplo |
|---|---|---|
| Clases de dominio / Java | PascalCase | `OrdenServicio`, `DetalleVenta` |
| Métodos / atributos Java | camelCase | `verificarDisponibilidad()`, `stockMinimo` |
| Componentes React | PascalCase | `UIVentas`, `UIInventario` |
| Paquetes Java | minúsculas, por capa y módulo | `com.andimotors.ventas.api`, `com.andimotors.ventas.dominio` |
| Nombres de módulo/componente en diagramas | PascalCase en español, sin espacios (ya usado consistentemente en `diagrama_componentes.plantuml` y los paquetes) | `Inventario`, `ActivosFijos` |
| Archivos de diagrama fuente | `snake_case.plantuml` (ya usado, salvo la excepción documentada en [11. Riesgos — R-08](11_risks_and_technical_debt.md#r-08--nombre-de-archivo-inconsistente-para-el-diagrama-de-estructura-compuesta)) | `diagrama_c4_contexto.plantuml` |

Esta tabla es una propuesta de esta revisión, no una convención ya aplicada de forma verificable en código (no existe código fuente en el repositorio).

---
[← Anterior: Introducción y Metas](01_introduction_and_goals.md) · [Volver al índice](arc42-template-ES.md) · [Siguiente: Alcance y Contexto del Sistema →](03_system_scope_and_context.md)
