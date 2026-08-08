# Requisitos funcionales — ERP Concesionario de Motos

Árbol de descomposición de requisitos funcionales, máximo 4 niveles.

## Árbol jerárquico

```
ERP CONCESIONARIO DE MOTOS
│
├── 1. Gestionar Inventario (Stock/Costos)
│   ├── 1.1 Registrar ingreso de moto nueva
│   │   ├── 1.1.1 Capturar número de VIN/chasis
│   │   └── 1.1.2 Registrar modelo, cilindraje, color, año
│   ├── 1.2 Registrar ingreso de repuestos
│   │   ├── 1.2.1 Asociar repuesto a modelo(s) compatible(s)
│   │   └── 1.2.2 Definir stock mínimo de reorden
│   ├── 1.3 Consultar disponibilidad de unidad
│   │   ├── 1.3.1 Filtrar por modelo/sucursal
│   │   └── 1.3.2 Verificar estado (disponible/reservada/vendida)
│   └── 1.4 Actualizar costos de inventario
│       └── 1.4.1 Registrar costo de importación/flete
│
├── 2. Gestionar Facturación
│   ├── 2.1 Facturar venta de moto
│   │   ├── 2.1.1 Verificar disponibilidad en Stock/Costos
│   │   ├── 2.1.2 Calcular impuestos y accesorios
│   │   └── 2.1.3 Generar comprobante en PDF
│   ├── 2.2 Facturar orden de servicio (taller)
│   │   ├── 2.2.1 Registrar repuestos usados
│   │   │   └── 2.2.1.1 Descontar repuesto de inventario
│   │   └── 2.2.2 Registrar mano de obra del mecánico
│   └── 2.3 Calcular comisión de vendedor
│       └── 2.3.1 Consultar módulo Empleados
│
├── 3. Gestionar Compras
│   ├── 3.1 Generar pedido a fábrica/distribuidor
│   │   ├── 3.1.1 Seleccionar modelos y cantidades
│   │   │   └── 3.1.1.1 Consultar histórico de ventas (EIS)
│   │   └── 3.1.2 Registrar fecha estimada de entrega
│   └── 3.2 Recibir pedido
│       └── 3.2.1 Validar unidades contra orden de compra
│
└── 4. Gestionar Empleados
    ├── 4.1 Registrar personal
    │   ├── 4.1.1 Vendedores (con % de comisión)
    │   └── 4.1.2 Mecánicos (con especialidad)
    └── 4.2 Asignar orden de servicio a mecánico
        └── 4.2.1 Consultar disponibilidad de taller
```

## Detalle de requisitos

### Rama 1 — Gestionar Inventario (Stock/Costos)

| ID | Nivel | Requisito | Descripción |
|---|---|---|---|
| RF-1 | 1 | Gestionar Inventario | El sistema debe controlar el stock de motos y repuestos en tiempo real, por sucursal. |
| RF-1.1 | 2 | Registrar ingreso de moto nueva | Permite dar de alta una unidad nueva en el inventario al recibirla de fábrica/distribuidor. |
| RF-1.1.1 | 3 | Capturar número de VIN/chasis | Registra el identificador único de la unidad, necesario para trazabilidad individual (no se maneja solo por cantidad). |
| RF-1.1.2 | 3 | Registrar modelo, cilindraje, color, año | Guarda los atributos que diferencian variantes de un mismo modelo. |
| RF-1.2 | 2 | Registrar ingreso de repuestos | Permite dar de alta repuestos comprados a proveedores. |
| RF-1.2.1 | 3 | Asociar repuesto a modelo(s) compatible(s) | Vincula cada repuesto con las motos donde puede usarse, para facilitar búsquedas en el taller. |
| RF-1.2.2 | 3 | Definir stock mínimo de reorden | Establece un umbral que dispara una alerta cuando el repuesto está por agotarse. |
| RF-1.3 | 2 | Consultar disponibilidad de unidad | Permite buscar si hay stock disponible de un modelo específico. |
| RF-1.3.1 | 3 | Filtrar por modelo/sucursal | Permite acotar la búsqueda por modelo de moto y por sede del concesionario. |
| RF-1.3.2 | 3 | Verificar estado (disponible/reservada/vendida) | Muestra el estado actual de cada unidad para evitar ofrecer una moto ya comprometida. |
| RF-1.4 | 2 | Actualizar costos de inventario | Permite registrar y ajustar el costo real de cada unidad o repuesto. |
| RF-1.4.1 | 3 | Registrar costo de importación/flete | Suma al costo base los gastos asociados a traer la unidad hasta el concesionario. |

### Rama 2 — Gestionar Facturación

| ID | Nivel | Requisito | Descripción |
|---|---|---|---|
| RF-2 | 1 | Gestionar Facturación | El sistema debe generar comprobantes de venta y de servicio técnico, con cálculo automático de impuestos. |
| RF-2.1 | 2 | Facturar venta de moto | Genera una factura de venta de unidad, validando disponibilidad antes de emitirla. |
| RF-2.1.1 | 3 | Verificar disponibilidad en Stock/Costos | Antes de facturar, consulta si la unidad (por VIN) sigue disponible, evitando vender una moto ya reservada. |
| RF-2.1.2 | 3 | Calcular impuestos y accesorios | Aplica automáticamente el impuesto correspondiente y suma accesorios agregados a la venta. |
| RF-2.1.3 | 3 | Generar comprobante en PDF | Produce el archivo PDF con los datos de la venta, listo para entregar al cliente. |
| RF-2.2 | 2 | Facturar orden de servicio (taller) | Genera una factura por trabajo de taller, incluyendo repuestos y mano de obra. |
| RF-2.2.1 | 3 | Registrar repuestos usados | Lista los repuestos consumidos durante la orden de servicio. |
| RF-2.2.1.1 | 4 | Descontar repuesto de inventario | Al registrar el repuesto usado, se descuenta automáticamente del stock en Stock/Costos. |
| RF-2.2.2 | 3 | Registrar mano de obra del mecánico | Agrega el costo de la mano de obra del mecánico asignado. |
| RF-2.3 | 2 | Calcular comisión de vendedor | Calcula la comisión correspondiente al vendedor tras cerrar una venta. |
| RF-2.3.1 | 3 | Consultar módulo Empleados | Obtiene el porcentaje de comisión configurado para ese vendedor. |

### Rama 3 — Gestionar Compras

| ID | Nivel | Requisito | Descripción |
|---|---|---|---|
| RF-3 | 1 | Gestionar Compras | El sistema debe permitir generar y controlar pedidos a fábrica/distribuidor de motos y repuestos. |
| RF-3.1 | 2 | Generar pedido a fábrica/distribuidor | Crea una orden de compra con los modelos y cantidades solicitadas. |
| RF-3.1.1 | 3 | Seleccionar modelos y cantidades | Permite elegir qué modelos pedir y en qué cantidad. |
| RF-3.1.1.1 | 4 | Consultar histórico de ventas (EIS) | Muestra las ventas pasadas por modelo para apoyar la decisión de cuánto pedir. |
| RF-3.1.2 | 3 | Registrar fecha estimada de entrega | Guarda cuándo se espera recibir el pedido, para planeación de inventario. |
| RF-3.2 | 2 | Recibir pedido | Permite confirmar el ingreso físico de las unidades pedidas. |
| RF-3.2.1 | 3 | Validar unidades contra orden de compra | Compara lo recibido con lo solicitado, para detectar faltantes o errores. |

### Rama 4 — Gestionar Empleados

| ID | Nivel | Requisito | Descripción |
|---|---|---|---|
| RF-4 | 1 | Gestionar Empleados | El sistema debe administrar el personal del concesionario: vendedores, mecánicos y administrativos. |
| RF-4.1 | 2 | Registrar personal | Permite dar de alta a un nuevo empleado en el sistema. |
| RF-4.1.1 | 3 | Vendedores (con % de comisión) | Registra el porcentaje de comisión que gana el vendedor por venta cerrada. |
| RF-4.1.2 | 3 | Mecánicos (con especialidad) | Registra la especialidad del mecánico (motor, eléctrico, general) para asignación de órdenes. |
| RF-4.2 | 2 | Asignar orden de servicio a mecánico | Vincula una orden de taller con el mecánico que la va a atender. |
| RF-4.2.1 | 3 | Consultar disponibilidad de taller | Verifica qué mecánicos están libres antes de asignar la orden. |
