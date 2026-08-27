[← Volver al índice](arc42-template-ES.md)

# 9. Decisiones de Arquitectura

> Nota metodológica: esta sección no formaba parte del alcance original del taller (ver nota en [arc42-template-ES.md](arc42-template-ES.md)). El repositorio no contiene ADRs (Architecture Decision Records) históricos ni un registro de decisiones explícito. Las decisiones listadas aquí se reconstruyen a partir de lo que ya está modelado en los diagramas y documentos de requisitos. Cada una se marca como **Documentada** (declarada explícitamente en algún documento fuente) o **Inferida** (deducida de la estructura de un diagrama, sin explicación textual que la respalde).

## AD-01 — Backend único (monolito modular) en vez de microservicios por módulo

- **Contexto**: el sistema tiene 6 módulos de negocio con responsabilidades distintas (Inventario, Facturación, Compras, Empleados, EIS, ActivosFijos).
- **Decisión**: agrupar todos los módulos dentro de un único componente de backend ("API Backend - Spring Boot"), que en la arquitectura de despliegue **propuesta** correspondería a un único nodo ("Servidor Cloud (AWS EC2)" — propuesto, no desplegado).
- **Justificación**: [NO EVIDENCIADO EN EL REPOSITORIO — no hay un texto que explique por qué se eligió un backend único en vez de servicios separados por módulo.]
- **Consecuencias**: los módulos se comunican mediante llamadas directas en proceso (ver [8.3](08_crosscutting_concepts.md#83-comunicación-entre-módulos)) en vez de llamadas de red entre servicios independientes.
- **Estado**: Inferida a partir del modelo de componentes y del [diagrama de despliegue](../diagramas/plantuml/diagrama_despliegue.plantuml) — que documenta una arquitectura **propuesta**, no una implementación desplegada (ver [07. Vista de Despliegue](07_deployment_view.md)). El backend único es la estructura con la que se ejecuta el sistema localmente; el nodo "Servidor Cloud (AWS EC2)" en sí es parte de la propuesta, no de un despliegue real.

## AD-02 — Base de datos relacional única y compartida (PostgreSQL)

- **Contexto**: el sistema necesita persistir datos de los 6 módulos, con trazabilidad individual de unidades (VIN).
- **Decisión**: usar una única instancia de PostgreSQL (AndinaMotorsDB), compartida por todos los módulos. En la arquitectura de despliegue **propuesta**, esta instancia se alojaría en AWS RDS.
- **Justificación**: "Relacional, ideal para inventario con trazabilidad por VIN" ([requisitos/No funcionales.md](<../requisitos/No funcionales.md>)).
- **Consecuencias**: no hay aislamiento de datos por módulo o por sucursal a nivel de base de datos, según el modelo actual.
- **Estado**: la elección de PostgreSQL como motor relacional está **documentada** (tabla de tecnologías en `No funcionales.md`) y es la tecnología usada en el entorno local de desarrollo/demostración. Su alojamiento en **AWS RDS** es parte de la arquitectura de despliegue **propuesta — no implementada** (ver [diagrama de despliegue](../diagramas/plantuml/diagrama_despliegue.plantuml) y [AD-07](#ad-07--propuesta-de-despliegue-cloud-aws)); no existe una instancia RDS activa.

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

- **Contexto**: se busca un despliegue reproducible del backend, contemplando eventualmente AWS EC2 como destino.
- **Decisión**: usar Docker para contenerizar el backend.
- **Justificación**: "Contenerización del backend para un despliegue reproducible en AWS EC2" ([requisitos/No funcionales.md](<../requisitos/No funcionales.md>)).
- **Consecuencias**: [NO EVIDENCIADO EN EL REPOSITORIO — no existe Dockerfile, docker-compose ni ninguna configuración de contenedor; la decisión está declarada como tecnología seleccionada pero no tiene evidencia de implementación.]
- **Estado**: **Propuesto / No implementado.** Documentada como intención tecnológica asociada a la arquitectura de despliegue propuesta (AWS); no hay evidencia de que el backend se ejecute actualmente en un contenedor, ni localmente ni en la nube.

## AD-06 — Mecanismo de autenticación y autorización

- **Contexto**: RNF-2 exige que solo ciertos roles puedan facturar ventas o cerrar órdenes de taller.
- **Decisión**: **Resuelta por AD-09** (ver abajo), agregada por el equipo en la Vista Lógica actualizada. Se conserva esta entrada para trazabilidad histórica del hallazgo.
- **Estado**: Resuelta a nivel de diseño propuesto — ver [AD-09](#ad-09--autenticación-y-autorización-basada-en-la-entidad-usuario).

## AD-07 — Propuesta de despliegue cloud (AWS)

- **Estado**: **Propuesto / No implementado.**
- **Contexto**: el sistema podría requerir posteriormente una infraestructura accesible remotamente desde varias sucursales, más allá del entorno local usado para desarrollo y demostración académica (ver [07. Vista de Despliegue](07_deployment_view.md)).
- **Decisión**: se propone AWS (EC2 para el backend, RDS para la base de datos PostgreSQL) como alternativa de despliegue, documentada en el [diagrama de despliegue](../diagramas/plantuml/diagrama_despliegue.plantuml) y en [requisitos/No funcionales.md](<../requisitos/No funcionales.md>).
- **Justificación**: AWS proporciona servicios administrados adecuados para alojar el frontend, el backend y la base de datos, y permitiría en un escenario futuro el acceso multi-sucursal en tiempo real que exige RNF-3.
- **Consecuencia**: permitiría, en un escenario de despliegue futuro, un sistema accesible remotamente desde varias sucursales, pero introduciría costos de infraestructura cloud y complejidad de configuración adicional (ver riesgo asociado en [11. Riesgos y Deuda Técnica](11_risks_and_technical_debt.md#r-09--costos-de-infraestructura-cloud)).
- **Estado actual**: **no implementado**. No existe instancia EC2, base de datos RDS, ni ninguna otra infraestructura AWS activa para este proyecto. El objetivo académico del taller no requiere infraestructura cloud para demostrar el funcionamiento del sistema; la demostración se realiza con ejecución local (ver [07.1](07_deployment_view.md#71-estado-actual)).

> Las siguientes decisiones (AD-08 a AD-13) se reconstruyen a partir de la carpeta [`Vista de Procesos (4+1)/`](<../../Vista de Procesos (4+1)>) y los diagramas C4, agregados por el equipo después de los 11 diagramas UML originales y de la primera versión de este capítulo 9.

## AD-08 — Arquitectura en capas (API/Dominio/Persistencia)

- **Contexto**: el backend único (AD-01) necesitaba una estructura interna explícita más allá de los componentes de negocio del diagrama de componentes original.
- **Decisión**: organizar el backend en 4 capas horizontales — API (REST), Dominio (servicios), Persistencia (repositorios) e Infraestructura (ORM, clientes externos) — documentadas en la Vista de Desarrollo.
- **Justificación**: [NO EVIDENCIADO EN EL REPOSITORIO — no hay texto que explique la elección de una arquitectura en capas frente a alternativas (hexagonal, CQRS, etc.); es un patrón estándar para aplicaciones Spring Boot, consistente con la tecnología ya seleccionada.]
- **Consecuencias**: separa claramente la exposición HTTP, la lógica de negocio y el acceso a datos; introduce una capa `API Seguridad` transversal que resuelve AD-09.
- **Estado**: Documentada ([`3. vista de desarrollo.jpg`](<../../Vista de Procesos (4+1)/3. vista de desarrollo.jpg>)) — diseño propuesto, sin código que lo verifique.

## AD-09 — Autenticación y autorización basada en la entidad `Usuario`

- **Contexto**: RNF-2 exige control de acceso por rol (Vendedor, Mecánico/Jefe de taller); no existía ninguna entidad de sistema para modelarlo (AD-06).
- **Decisión**: agregar una clase `Usuario` (`id`, `rol`, `usuario`, `passwordHash`) en la Vista Lógica, con el atributo `rol` cubriendo todos los roles del sistema, y una capa `API Seguridad` en la Vista de Desarrollo responsable de aplicar la autorización.
- **Justificación**: es la forma estándar de modelar autenticación por credenciales y autorización por rol en una arquitectura en capas tipo Spring Boot (Spring Security).
- **Consecuencias**: resuelve RNF-2 a nivel de diseño; deja pendiente el protocolo concreto (JWT/sesiones) y el algoritmo de hashing — ver [8.1](08_crosscutting_concepts.md#81-seguridad-y-control-de-acceso).
- **Estado**: Documentada ([`1. vista logica.jpg`](<../../Vista de Procesos (4+1)/1. vista logica.jpg>), [`3. vista de desarrollo.jpg`](<../../Vista de Procesos (4+1)/3. vista de desarrollo.jpg>)) — diseño propuesto, no implementado.

## AD-10 — Integración con DIAN para facturación electrónica

- **Contexto**: `alcance.md` declaraba originalmente la facturación electrónica ante DIAN como fuera de alcance del taller.
- **Decisión**: agregar la integración con DIAN como parte del flujo de facturación: la clase `Factura` incorpora `cufe` y `enviarADIAN()`; la capa de Infraestructura agrega un `Cliente DIAN (SOAP/REST)`; la Vista Física agrega un "Sistema Externo DIAN"; la llamada se modela como asíncrona en la Vista de Procesos.
- **Justificación**: [NO EVIDENCIADO EN EL REPOSITORIO — no hay texto que explique por qué se decidió incluir DIAN después de haberla declarado fuera de alcance; posiblemente una ampliación de alcance del equipo entre entregas del taller.]
- **Consecuencias**: introduce una dependencia externa real (DIAN es una entidad tributaria colombiana) al flujo crítico de ventas; el manejo de fallos de esa integración (DIAN no disponible, CUFE rechazado) no está modelado. Contradice el alcance original — ver [11. Riesgos — R-11](11_risks_and_technical_debt.md#r-11--integración-con-dian-y-proveedores-modelada-en-diagramas-nuevos-pero-ausente-en-los-diagramas-originales).
- **Estado**: Documentada en la Vista 4+1 y la Vista de Escenarios — **propuesta de diseño, no implementada**; no está en el diagrama de componentes ni el diagrama de clases originales.

## AD-11 — App móvil para vendedores

- **Contexto**: `alcance.md` declaraba originalmente que el frontend definido era únicamente web (React), y que una app móvil quedaba fuera de alcance.
- **Decisión**: agregar un cliente "App Móvil (Vendedores)" en la Vista Física, como cliente adicional al navegador web, ambos conectados al mismo Load Balancer.
- **Justificación**: [NO EVIDENCIADO EN EL REPOSITORIO — no se especifica tecnología (nativa, híbrida, PWA) ni justificación de negocio.]
- **Consecuencias**: duplica la superficie de cliente sin que exista un contenedor "App Móvil" en el diagrama de contenedores C4 (que solo modela `Aplicación Web (Cliente-Sucursal)`) — inconsistencia entre el C4 y la Vista Física, ambos agregados por el equipo. Contradice el alcance original.
- **Estado**: Documentada solo en la Vista Física — **propuesta de diseño, no implementada**; ausente en el resto de diagramas (C4 contenedores, diagrama de despliegue original, diagrama de componentes).

## AD-12 — Load Balancer en la arquitectura de despliegue propuesta

- **Contexto**: el diagrama de despliegue original no contemplaba balanceo de carga (ver [R-04](11_risks_and_technical_debt.md#r-04--despliegue-de-nodo-único-propuesto-frente-a-metas-de-escalabilidad-y-disponibilidad)).
- **Decisión**: agregar un Load Balancer entre los clientes (navegador y app móvil) y el Servidor de Aplicaciones, en la Vista Física.
- **Justificación**: patrón estándar para distribuir tráfico y habilitar escalado horizontal del backend, relevante para RNF-3 (multi-sucursal) y RNF-5 (99% uptime).
- **Consecuencias**: mitiga parcialmente R-04, pero sin especificar política de balanceo, número de instancias del Servidor de Aplicaciones, ni healthchecks.
- **Estado**: Documentada ([`4. vista fisica.jpg`](<../../Vista de Procesos (4+1)/4. vista fisica.jpg>)) — **propuesta de diseño, no implementada**.

## AD-13 — Separación de un Servidor de Reportes para EIS

- **Contexto**: el módulo EIS (reportes e indicadores gerenciales) no tenía nodo de infraestructura propio en el diagrama de despliegue original.
- **Decisión**: agregar un "Servidor de Reportes" como nodo separado del Servidor de Aplicaciones en la Vista Física, conectado por HTTP interno.
- **Justificación**: [NO EVIDENCIADO EN EL REPOSITORIO — no hay texto que confirme que este nodo corresponde a EIS; es la interpretación más razonable dado que EIS es el único módulo orientado a reportes, pero el nombre del nodo no lo menciona explícitamente.]
- **Consecuencias**: aísla la carga de generación de reportes del servidor transaccional principal; no hay un contenedor "Servidor de Reportes" equivalente en el diagrama C4 de contenedores, que solo modela un único `API Backend`.
- **Estado**: **Inferida** — el nodo está documentado en la Vista Física, pero su asociación con EIS no está declarada explícitamente en ningún documento fuente.

---
[← Anterior: Conceptos Transversales](08_crosscutting_concepts.md) · [Volver al índice](arc42-template-ES.md) · [Siguiente: Requisitos de Calidad →](10_quality_requirements.md)
