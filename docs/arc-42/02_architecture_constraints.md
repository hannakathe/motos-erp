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

## 2.2 Restricciones organizativas

De la triple restricción definida en [alcance.md](../alcance.md):

- **Tiempo**: el proyecto se desarrolla dentro de la duración del semestre académico, siguiendo el cronograma del taller de arquitectura de software (conceptos de arquitectura y modelo 4+1, elaboración de los 11 diagramas UML, entrega final con presentación de 10-15 minutos en inglés).
- **Costo**: proyecto académico sin presupuesto monetario real; el "costo" se mide en horas/persona del equipo (Hanna, Ingrid y Marlon), con reparto de tareas ya definido por integrante.
- **Alcance**: acotado a lo ya modelado en los 11 diagramas UML del equipo (ver detalle en [alcance.md](../alcance.md)); el desglose interno de la API REST en componentes más finos (Nivel 2/3 de C4) y la integración en tiempo real con proveedores o fábrica quedan fuera de este taller.

## 2.3 Convenciones

[PENDIENTE: no hay ninguna convención de código, nomenclatura o estilo documentada en el repositorio más allá de los nombres ya usados en los diagramas UML.]

---
[← Anterior: Introducción y Metas](01_introduction_and_goals.md) · [Volver al índice](arc42-template-ES.md) · [Siguiente: Alcance y Contexto del Sistema →](03_system_scope_and_context.md)
