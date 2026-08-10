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
| Cliente | Adquiere motos/repuestos y/o solicita servicio de taller (actor modelado en el [diagrama de clases](../diagramas/plantuml/diagrama_clases.plantuml)). |
| Vendedor | Registra ventas (facturas) y factura a nombre de un cliente; único rol autorizado a facturar ventas (RNF-2). |
| Mecánico | Atiende órdenes de taller y registra diagnósticos; autorizado, junto al Jefe de taller, a cerrar órdenes de servicio (RNF-2). |
| Jefe de taller | Autorizado a cerrar órdenes de servicio (RNF-2). [PENDIENTE: no está modelado como clase en el diagrama de clases actual — solo aparece mencionado en el requisito de seguridad RNF-2]. |

---
[Volver al índice](arc42-template-ES.md) · [Siguiente: Restricciones de la Arquitectura →](02_architecture_constraints.md)
