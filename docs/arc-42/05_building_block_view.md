[← Volver al índice](../arc42-template-ES.md)

# 5. Vista de Bloques

## 5.1 Sistema General de Caja Blanca (Nivel 1)

![Diagrama de Contenedores](./images/diagrama_contenedores.png)

**Motivación**: el Sistema ERP está compuesto por tres contenedores principales, siguiendo una arquitectura monolítica simple.

**Bloques de construcción contenidos**: Aplicación Web (React), API REST (Spring Boot) y Base de Datos (PostgreSQL).

**Interfases importantes**: Aplicación Web ↔ API REST vía HTTPS/JSON; API REST ↔ Base de Datos vía JPA/JDBC; API REST ↔ Servicio de Correo externo.

### Aplicación Web (React)

- **Propósito/Responsabilidad**: interfaz de usuario para todos los roles del ERP (Gestor de Inventario, Administrador de Compras, Encargado de Almacén, Contador, Gerente).
- **Interfase(s)**: consume la API REST vía HTTPS/JSON.
- **Ubicación**: se ejecuta en el navegador del usuario (SPA).

### API REST (Spring Boot)

- **Propósito/Responsabilidad**: concentra toda la lógica de negocio del ERP (validaciones, reglas de compras, cálculo de estados de órdenes, etc.).
- **Interfase(s)**: expone endpoints REST consumidos por la Aplicación Web; se comunica con la Base de Datos vía JPA/JDBC; envía notificaciones al Servicio de Correo externo.

### Base de Datos (PostgreSQL)

- **Propósito/Responsabilidad**: almacenamiento persistente de toda la información del ERP (productos, proveedores, órdenes de compra, recepciones, facturación, etc.).
- **Interfase(s)**: accedida exclusivamente por la API REST.

## 5.2 Modelo de Datos (MER) — Módulo de Compras

![Modelo Entidad-Relación - Compras](./images/diagrama_MER.png)

- **Producto**: catálogo de artículos que pueden comprarse.
- **Proveedor**: catálogo de proveedores habilitados para recibir órdenes de compra.
- **Orden_Compra**: encabezado de una solicitud de abastecimiento a un proveedor, con su estado y fecha.
- **Detalle_Orden**: líneas de producto y cantidad solicitadas dentro de una orden de compra.
- **Recepcion**: registra un evento de llegada de mercancía asociado a una orden de compra, ya que una orden puede recibirse en una o varias entregas parciales.
- **Recepcion_Detalle**: cantidad efectivamente recibida por cada línea de la orden en una recepción determinada.

## 5.3 Vista Lógica (Diagrama de Clases y Objetos)

*(Sección en construcción: el diagrama de clases y el de objetos ya están modelados; falta reconciliar su dominio con el resto de la Vista de Bloques una vez se completen los diagramas de Contexto, Contenedores, Secuencia y MER pendientes.)*

[diagrama_clases.plantuml](../diagramas/plantuml/diagrama_clases.plantuml) — [diagrama_objetos.plantuml](../diagramas/plantuml/diagrama_objetos.plantuml)

**Clases principales**: `Cliente`, `Vendedor`, `ItemVendible` (abstracta), `Moto`, `Repuesto`, `Factura`, `DetalleFactura`, `OrdenTaller`, `Mecanico`.

- **Cliente**: persona que adquiere motos/repuestos y/o solicita servicio de taller.
- **Vendedor**: registra ventas (facturas) a nombre de un cliente.
- **ItemVendible**: clase abstracta que agrupa lo que una línea de factura puede referenciar (atributo común `precio`); `Moto` y `Repuesto` heredan de ella. Existe para que `DetalleFactura` tenga una única asociación obligatoria (a `ItemVendible`) en vez de dos asociaciones obligatorias simultáneas a `Moto` y a `Repuesto`, que exigirían ambas presentes en cada línea.
- **Moto**: unidad de inventario vendible, identificada por su chasis.
- **Repuesto**: ítem de inventario usado tanto en ventas directas como en órdenes de taller.
- **Factura**: comprobante de una venta, compuesto por una o más líneas (`DetalleFactura`).
- **DetalleFactura**: línea de factura que referencia un `ItemVendible` (una `Moto` o un `Repuesto`).
- **OrdenTaller**: solicitud de servicio de un cliente, atendida por un `Mecanico` y que puede consumir `Repuesto`.
- **Mecanico**: atiende órdenes de taller y registra diagnósticos.

El diagrama de objetos ilustra una instancia concreta de este modelo: un cliente que compra una moto a través de un vendedor, documentado en una factura con su detalle.

## 5.4 Nivel 2 y Nivel 3

*(No aplica para el alcance de este taller — se documentaría aquí el desglose interno de la API REST en componentes, si el proyecto avanzara a ese nivel de detalle.)*

---
[← Anterior: Alcance y Contexto del Sistema](03_system_scope_and_context.md) · [Volver al índice](../arc42-template-ES.md) · [Siguiente: Vista de Ejecución →](06_runtime_view.md)
