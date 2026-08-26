[← Volver al índice](arc42-template-ES.md)

# 9. Decisiones de Arquitectura

> Nota metodológica: esta sección no formaba parte del alcance original del taller (ver nota en [arc42-template-ES.md](arc42-template-ES.md)). El repositorio no contiene ADRs (Architecture Decision Records) históricos ni un registro de decisiones explícito. Las decisiones listadas aquí se reconstruyen a partir de lo que ya está modelado en los diagramas y documentos de requisitos. Cada una se marca como **Documentada** (declarada explícitamente en algún documento fuente) o **Inferida** (deducida de la estructura de un diagrama, sin explicación textual que la respalde).

## AD-01 — Backend único (monolito modular) en vez de microservicios por módulo

- **Contexto**: el sistema tiene 6 módulos de negocio con responsabilidades distintas (Inventario, Facturación, Compras, Empleados, EIS, ActivosFijos).
- **Decisión**: desplegar todos los módulos dentro de un único componente de backend ("API Backend - Spring Boot") sobre un único nodo ("Servidor Cloud (AWS EC2)").
- **Justificación**: [NO EVIDENCIADO EN EL REPOSITORIO — no hay un texto que explique por qué se eligió un backend único en vez de servicios separados por módulo.]
- **Consecuencias**: los módulos se comunican mediante llamadas directas en proceso (ver [8.3](08_crosscutting_concepts.md#83-comunicación-entre-módulos)) en vez de llamadas de red entre servicios independientes.
- **Estado**: Inferida a partir de la implementación modelada en el [diagrama de despliegue](../diagramas/plantuml/diagrama_despliegue.plantuml).

## AD-02 — Base de datos relacional única y compartida (PostgreSQL)

- **Contexto**: el sistema necesita persistir datos de los 6 módulos, con trazabilidad individual de unidades (VIN).
- **Decisión**: usar una única instancia de PostgreSQL (AndinaMotorsDB) en AWS RDS, compartida por todos los módulos.
- **Justificación**: "Relacional, ideal para inventario con trazabilidad por VIN" ([requisitos/No funcionales.md](<../requisitos/No funcionales.md>)).
- **Consecuencias**: no hay aislamiento de datos por módulo o por sucursal a nivel de base de datos, según el modelo actual.
- **Estado**: Documentada (tabla de tecnologías en `No funcionales.md` y [diagrama de despliegue](../diagramas/plantuml/diagrama_despliegue.plantuml)).

## AD-03 — Separación de responsabilidades en 6 componentes de negocio con interfaces explícitas

- **Contexto**: el sistema cubre procesos de negocio distintos (stock, ventas/taller, compras, personal, reportes, activos).
- **Decisión**: modelar el sistema como 6 componentes (Inventario, Facturación, Compras, Empleados, EIS, ActivosFijos), cada uno con paquetes internos y con dependencias explícitas de operación entre componentes.
- **Justificación**: alineación con los módulos identificados desde el README y el alcance del proyecto.
- **Consecuencias**: Facturación queda como el componente con más dependencias entrantes/salientes (Inventario, Empleados); ActivosFijos queda sin dependencias modeladas con otros componentes (ver [11. Riesgos](11_risks_and_technical_debt.md)).
- **Estado**: Documentada ([diagrama de componentes](../diagramas/plantuml/diagrama_componentes.plantuml), [README.md](../../README.md)).

## AD-04 — `ItemVendible` como superclase abstracta de `Moto` y `Repuesto`

- **Contexto**: una línea de factura (`DetalleFactura`) debe poder referenciar indistintamente una moto o un repuesto.
- **Decisión**: introducir la clase abstracta `ItemVendible` (atributo común `precio`), de la que heredan `Moto` y `Repuesto`, y hacer que `DetalleFactura` se asocie a `ItemVendible` en lugar de tener dos asociaciones obligatorias separadas.
- **Justificación**: evita que `DetalleFactura` requiera simultáneamente una `Moto` y un `Repuesto` presentes en cada línea (documentado ya en [5.4 Vista de Bloques](05_building_block_view.md#54-vista-lógica-diagrama-de-clases-y-objetos)).
- **Consecuencias**: simplifica el modelo de facturación a costa de una jerarquía de herencia; el diagrama de clases fuente ([diagrama_clases.plantuml](../diagramas/plantuml/diagrama_clases.plantuml)) no incluye esta justificación como comentario, por lo que la razón de diseño solo consta en la documentación arc42 ya existente, no en el diagrama mismo.
- **Estado**: Inferida a partir del modelo de clases (la justificación fue reconstruida por el equipo al documentar la Vista de Bloques, no proviene de un comentario en el `.plantuml`).

## AD-05 — Contenerización del backend con Docker

- **Contexto**: se busca un despliegue reproducible del backend en AWS EC2.
- **Decisión**: usar Docker para contenerizar el backend.
- **Justificación**: "Contenerización del backend para un despliegue reproducible en AWS EC2" ([requisitos/No funcionales.md](<../requisitos/No funcionales.md>)).
- **Consecuencias**: [NO EVIDENCIADO EN EL REPOSITORIO — no existe Dockerfile, docker-compose ni ninguna configuración de contenedor; la decisión está declarada como tecnología seleccionada pero no tiene evidencia de implementación.]
- **Estado**: Documentada como intención tecnológica; implementación **pendiente/no evidenciada**.

## AD-06 — Mecanismo de autenticación y autorización

- **Contexto**: RNF-2 exige que solo ciertos roles puedan facturar ventas o cerrar órdenes de taller.
- **Decisión**: [POR DEFINIR — no hay una decisión tomada sobre el mecanismo técnico (sesiones, tokens, roles como entidad del sistema, etc.).]
- **Estado**: Pendiente.

---
[← Anterior: Conceptos Transversales](08_crosscutting_concepts.md) · [Volver al índice](arc42-template-ES.md) · [Siguiente: Requisitos de Calidad →](10_quality_requirements.md)
