[← Volver al índice](arc42-template-ES.md)

# 1. Introducción y Metas

## 1.1 Objetivo del sistema

**AndiMotors ERP** es el sistema de planificación de recursos empresariales diseñado a medida para **Andina Motors**, un concesionario de motos. Reemplaza el uso de un DMS (Dealer Management System) genérico de mercado por una solución propia, sin costo de licencia y con menor curva de aprendizaje para el personal (ver comparación completa en [antecedentes.md](../antecedentes.md)).

El sistema cubre 6 módulos:

- **Inventario (Stock/Costos)**: control de stock de motos y repuestos, disponibilidad por sucursal, costos.
- **Facturación**: venta de motos, órdenes de servicio de taller, cálculo de comisiones de vendedores.
- **Compras**: pedidos a fábrica/distribuidor y recepción de mercancía.
- **Empleados**: registro de vendedores y mecánicos, asignación de órdenes de taller.
- **EIS**: reportes e indicadores gerenciales (histórico de ventas).
- **ActivosFijos**: registro de activos y cálculo de depreciación.

## 1.2 Requisitos de negocio

El detalle completo de requisitos funcionales, organizado en un árbol de 4 niveles (RF-1 a RF-4, con sub-requisitos), está en [requisitos/funcionales.md](../requisitos/funcionales.md). Resumen de las 4 ramas principales:

| ID | Requisito | Resumen |
|---|---|---|
| RF-1 | Gestionar Inventario (Stock/Costos) | Controlar el stock de motos y repuestos en tiempo real, por sucursal. |
| RF-2 | Gestionar Facturación | Generar comprobantes de venta y de servicio técnico, con cálculo automático de impuestos. |
| RF-3 | Gestionar Compras | Generar y controlar pedidos a fábrica/distribuidor de motos y repuestos. |
| RF-4 | Gestionar Empleados | Administrar el personal del concesionario: vendedores, mecánicos y administrativos. |

## 1.3 Metas de calidad

Del documento [requisitos/No funcionales.md](<../requisitos/No funcionales.md>):

| ID | Meta de calidad | Descripción |
|---|---|---|
| RNF-1 | Rendimiento | La verificación de disponibilidad de una unidad (RF-2.1.1) debe responder en menos de 1 segundo. |
| RNF-2 | Seguridad | Solo usuarios con rol "Vendedor" pueden facturar ventas; solo "Mecánico" o "Jefe de taller" pueden cerrar órdenes de servicio. |
| RNF-3 | Escalabilidad | El sistema debe soportar múltiples sucursales del concesionario consultando el mismo inventario en tiempo real (RF-1.3.1). |
| RNF-4 | Usabilidad | Un vendedor sin conocimientos técnicos debe poder generar una factura de venta en máximo 3 pasos. |
| RNF-5 | Disponibilidad | El sistema debe garantizar 99% de uptime mensual, considerando que ventas y taller operan en horario comercial extendido. |

## 1.4 Stakeholders

| Rol | Descripción |
|---|---|
| Equipo de arquitectura (Hanna, Ingrid, Marlon) | Diseña y documenta la arquitectura del sistema como parte del taller académico (ver [alcance.md](../alcance.md)). |
| Cliente | Adquiere motos/repuestos y/o solicita servicio de taller (actor modelado en el [diagrama de clases](../diagramas/plantuml/diagrama_clases.plantuml) y en la Vista de Escenarios). |
| Vendedor | Registra ventas (facturas) a nombre de un cliente; único rol autorizado a facturar ventas (RNF-2). |
| Mecánico / Técnico | Atiende órdenes de taller y registra diagnósticos; autorizado, junto al Jefe de taller, a cerrar órdenes de servicio (RNF-2). Modelado como `Mecanico` en el [diagrama de clases](../diagramas/plantuml/diagrama_clases.plantuml) original y como "Técnico" en la Vista de Escenarios ([`Vista de Procesos (4+1)/Vista de escenarios.jpg`](<../../Vista de Procesos (4+1)/Vista de escenarios.jpg>)) — mismo rol, nombre distinto entre ambas generaciones de diagramas (ver [11. Riesgos — R-10](11_risks_and_technical_debt.md#r-10--variantes-de-nombre-para-el-mismo-rolentidad-entre-generaciones-de-diagramas)). |
| Jefe de taller | Autorizado, junto al Mecánico/Técnico, a cerrar órdenes de servicio (RNF-2). No está modelado como clase propia en ningún diagrama; se resuelve arquitectónicamente como un valor del atributo `rol` de la clase `Usuario` (ver [Vista Lógica actualizada](<../../Vista de Procesos (4+1)/1. vista logica.jpg>) y [09. Decisiones — AD-09](09_architecture_decisions.md#ad-09--autenticación-y-autorización-basada-en-la-entidad-usuario)), junto con Vendedor, Mecánico/Técnico, Administrador/Gerente y Jefe de Compras — **decisión de arquitectura propuesta en esta revisión**, no evidenciada previamente. |
| Administrador / Gerente | Consulta reportes gerenciales (EIS), gestiona activos fijos y administra empleados. Evidenciado como "Gerente" en [product-backlog.md](../product-backlog.md) (HU-05, HU-11, HU-19, HU-20) y como "Administrador / Gerente" (actor `gerente`) y "Administrador" (caso de uso "Generar reportes gerenciales") en los diagramas C4 y la Vista de Escenarios respectivamente — mismo rol, nombre distinto entre fuentes (ver [11. Riesgos — R-10](11_risks_and_technical_debt.md#r-10--variantes-de-nombre-para-el-mismo-rolentidad-entre-generaciones-de-diagramas)). |
| Jefe de Compras | Registra compras a proveedor (RF-3). Aparece como "Jefe de Compras" en la Vista de Escenarios y como "encargado de compras"/"encargado de bodega" en [product-backlog.md](../product-backlog.md) y [sprint-1-planning.md](../sprint/sprint-1-planning.md) — mismo rol, nombre distinto entre fuentes. |
| Proveedor / Fábrica-Distribuidor | Actor externo que recibe pedidos de compra y despacha mercancía. Modelado como `Proveedor` (clase, con `id`, `nombre`, `nit`) en la Vista Lógica, como actor "Proveedor" en la Vista de Escenarios, y como sistema externo `System_Ext(fabrica, "Fábrica / Distribuidor", ...)` en el [diagrama C4 de contexto](../diagramas/plantuml/diagrama_c4_contexto.plantuml). No modelado en el diagrama de componentes original ni en `diagrama_clases.plantuml` — ver [11. Riesgos — R-11](11_risks_and_technical_debt.md#r-11--integración-con-dian-y-proveedores-modelada-en-diagramas-nuevos-pero-ausente-en-los-diagramas-originales). |
| DIAN | Entidad tributaria externa (Colombia) que recibe la solicitud de facturación electrónica y devuelve el CUFE de confirmación. Modelada como actor externo en la Vista de Escenarios, la Vista de Procesos y la Vista Física ("Sistema Externo DIAN"); no modelada en `alcance.md` como actor dentro de alcance hasta esta revisión (ver nota en [alcance.md](../alcance.md)). |

> Nota: los nombres de rol difieren entre las dos generaciones de diagramas del repositorio (los 11 diagramas UML originales vs. la Vista 4+1 agregada posteriormente en [`Vista de Procesos (4+1)/`](<../../Vista de Procesos (4+1)>) y los diagramas C4). Esta tabla documenta ambos nombres explícitamente en vez de elegir uno solo, y la inconsistencia se registra formalmente en [11. Riesgos y Deuda Técnica](11_risks_and_technical_debt.md).

---
[Volver al índice](arc42-template-ES.md) · [Siguiente: Restricciones de la Arquitectura →](02_architecture_constraints.md)
