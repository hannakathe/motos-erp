# Cuadro de antecedentes — ERP-Andina Motors- Concesionario de Motos

Este documento compara nuestro ERP propio contra 3 aplicaciones existentes en el mercado, para justificar por qué vale la pena construir un sistema a medida en vez de usar una solución ya disponible.

Cada integrante investigó una app:

- **APP1** — CDK Global (Hanna)
- **APP2** — Shift Industry (Ingrid)
- **APP3** — Autologica Sky DMS (Marlon)


## APP1 — CDK Global (Hanna)

CDK Global es un DMS (Dealer Management System) usado por más de 15.000 concesionarios, orientado a vehículos automotores en general (autos, camiones, motos y equipos), no exclusivo del sector motos.

| Criterio | APP1 — CDK Global | Andina Motors |
|---|---|---|
| Especialización | Genérico para concesionarios de vehículos (autos, camiones, motos), orientado a grupos grandes y multisede | Diseñado específicamente para nuestro concesionario de motos |
| Costo | No publica precio fijo; funciona por cotización y demo | Sin costo de licencia (desarrollo propio) |
| Módulos | Inventario, contabilidad, CRM, servicio/taller, financiación (F&I), reportes personalizables, en 6 suites conectadas | Compras, Facturación, Stock/Costos, Empleados, EIS |
| Trazabilidad por unidad | Sí, orientado a inventario de vehículos en general | Sí (por VIN, ya modelado en `Moto`) |
| Curva de aprendizaje | Alta — usuarios reportan interfaz anticuada y configuración inicial compleja | Baja (a medida, pensado para nuestra operación) |

Fuente: [capterra.com/p/122988/CDK-Global](https://www.capterra.com/p/122988/CDK-Global/), [getapp.com CDK Global](https://www.getapp.com/retail-consumer-services-software/a/cdk-global/)

## APP2 — Shift Industry (Ingrid)

Shift Industry es un DMS orientado específicamente a concesionarios y talleres de motocicletas, con foco fuerte en el área de servicio/taller.

| Criterio | APP2 — Shift Industry | Andina Motors |
|---|---|---|
| Especialización | Diseñado específicamente para concesionarios y talleres de motocicletas | Diseñado específicamente para nuestro concesionario de motos |
| Costo | No publica precio fijo; funciona por cotización | Sin costo de licencia (desarrollo propio) |
| Módulos | Inventario en tiempo real de repuestos (con alertas de stock bajo), facturación y presupuestos de taller, gestión de ventas e inventario, mensajería con clientes (SMS/correo), integración contable con QuickBooks, financiación (cálculo de cuotas) | Compras, Facturación, Stock/Costos, Empleados, EIS |
| Trazabilidad por unidad | Sí — seguimiento de piezas por UPC, SKU y MPN, con alertas automáticas de stock bajo | Sí (por VIN, ya modelado en `Moto`) |
| Curva de aprendizaje | Media — enfocado en flujo de taller, pensado para uso diario del personal de servicio | Baja (a medida, pensado para nuestra operación) |

Fuente: [shiftindustry.com/es-es/motorcycle-dealer-shop-software](https://www.shiftindustry.com/es-es/motorcycle-dealer-shop-software)

## APP3 — Autologica Sky DMS (Marlon)

Software DMS especializado específicamente en concesionarios de motocicletas — no es un ERP genérico adaptado, sino diseñado para el sector exacto.

| Criterio | APP3 — Autologica Sky DMS | Andina Motors |
|---|---|---|
| Especialización | Diseñado específicamente para concesionarios de motos/vehículos | Diseñado específicamente para nuestro concesionario de motos |
| Costo | Cotización personalizada (no publica precios públicos, común en software B2B especializado) | Sin costo de licencia (desarrollo propio) |
| Módulos | Repuestos (sugerencia de pedidos, reserva de piezas), financiación/contabilidad bimonetaria, CRM, paneles de KPIs gerenciales (EIS), servicio/taller con turnos online | Compras, Facturación, Stock/Costos, Empleados, EIS |
| Trazabilidad por unidad | Sí (por chasis/bastidor) | Sí (por VIN, ya modelado en `Moto`) |
| Curva de aprendizaje | Media (sistema robusto, orientado a concesionarios grandes o multisede) | Baja (a medida, pensado para nuestra operación) |

Fuente: [autologica.com/es/industria-motos](https://www.autologica.com/es/industria-motos/)

## Cuadro comparativo consolidado

| Criterio | APP1 — CDK Global | APP2 — Shift Industry | APP3 — Autologica Sky DMS | Andina Motors |
|---|---|---|---|---|
| Especialización | Genérico multivehículo | Motos y talleres de motos | Motos/vehículos | Concesionario de motos propio |
| Costo | Por cotización | Por cotización | Cotización personalizada | Sin costo de licencia |
| Módulos clave | Inventario, contabilidad, CRM, taller, financiación | Inventario en tiempo real, taller, mensajería, QuickBooks | Repuestos, financiación, CRM, EIS, taller | Compras, Facturación, Stock/Costos, Empleados, EIS |
| Trazabilidad por unidad | Sí (general) | Sí (por SKU/UPC/MPN) | Sí (por chasis) | Sí (por VIN) |
| Curva de aprendizaje | Alta | Media | Media | Baja |

## Conclusión

Las 3 apps investigadas ofrecen funcionalidades sólidas de inventario, facturación y CRM, pero ninguna es gratuita ni tiene precio público (todas funcionan por cotización), y su curva de aprendizaje es mayor por estar pensadas para operaciones grandes o multisede. Nuestro ERP propio, al ser diseñado a la medida del concesionario, no tiene costo de licencia y reduce la curva de aprendizaje del personal — esa es la principal justificación del proyecto frente a estas alternativas.
