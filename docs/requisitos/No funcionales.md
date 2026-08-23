# Requisitos No Funcionales — ERP Andina Motors

## Identidad del producto
- **Nombre:** AndiMotors ERP
- **Colores:** Rojo (#C1272D) y Negro (#1A1A1A), con acentos en gris

## Requisitos No Funcionales

| ID | Requisito | Descripción |
|---|---|---|
| RNF-1 | Rendimiento | La verificación de disponibilidad de una unidad (RF-2.1.1) debe responder en menos de 1 segundo. |
| RNF-2 | Seguridad | Solo usuarios con rol "Vendedor" pueden facturar ventas; solo "Mecánico" o "Jefe de taller" pueden cerrar órdenes de servicio. |
| RNF-3 | Escalabilidad | El sistema debe soportar múltiples sucursales del concesionario consultando el mismo inventario en tiempo real (RF-1.3.1). |
| RNF-4 | Usabilidad | Un vendedor sin conocimientos técnicos debe poder generar una factura de venta en máximo 3 pasos. |
| RNF-5 | Disponibilidad | El sistema debe garantizar 99% de uptime mensual, considerando que ventas y taller operan en horario comercial extendido. |

## Tecnologías Seleccionadas

| Categoría | Tecnología | Justificación |
|---|---|---|
| Backend | Java + Spring Boot | Robusto para lógica transaccional (facturación, control de stock) |
| Frontend | React | Interfaz ágil para vendedores y mecánicos |
| Base de datos | PostgreSQL | Relacional, ideal para inventario con trazabilidad por VIN |
| Despliegue | AWS (EC2 + RDS) | Permite acceso multi-sucursal en tiempo real |
| Herramientas | Docker | Contenerización del backend para un despliegue reproducible en AWS EC2 |