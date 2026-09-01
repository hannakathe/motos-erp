# Requisitos funcionales — ERP Concesionario de Motos Andina Motors

Árbol de descomposición de requisitos funcionales, con hasta 4 niveles.

> **Sincronización con Jira (2026-09-01).** Este documento se actualizó para reflejar el
> backlog vigente del proyecto **SCRUM** en Jira
> (`https://marlondelga43.atlassian.net/jira/software/projects/SCRUM`). El backlog creció
> de 22 a **54 historias de usuario**, organizadas en 6 épicas (una por módulo), con un
> nuevo esquema de códigos por módulo:
>
> | Épica (módulo) | Épica Jira | Rango de HU |
> |---|---|---|
> | Compras | SCRUM-11 | HU-01 … HU-09 |
> | Facturación | SCRUM-18 | HU-F01 … HU-F09 |
> | Stock/Costos (Inventario) | SCRUM-19 | HU-S01 … HU-S09 |
> | Activos Fijos | SCRUM-20 | HU-A01 … HU-A09 |
> | Empleados | SCRUM-21 | HU-E01 … HU-E09 |
> | EIS | SCRUM-22 | HU-EIS01 … HU-EIS09 |
>
> Cada requisito de nivel 2 se referencia con su HU y su issue Jira (SCRUM-x). Las
> descripciones de detalle se derivan del título de la historia; los criterios de
> aceptación completos viven en cada issue de Jira. Las HU de la línea de base anterior
> (HU-01…HU-22 en [product-backlog.md](../product-backlog.md)) quedan subsumidas por este
> esquema.

## Árbol jerárquico

```
ERP CONCESIONARIO DE MOTOS
│
├── 1. Gestionar Inventario (Stock/Costos)          [épica SCRUM-19]
│   ├── 1.1 Registrar ingreso de moto nueva                    (HU-S06)
│   │   ├── 1.1.1 Capturar número de VIN/chasis
│   │   ├── 1.1.2 Registrar modelo, cilindraje, color, año
│   │   └── 1.1.3 Asignar sucursal de ingreso
│   ├── 1.2 Registrar ingreso de repuestos                     (HU-S07)
│   │   ├── 1.2.1 Asociar repuesto a modelo(s) compatible(s)
│   │   ├── 1.2.2 Definir stock mínimo de reorden
│   │   └── 1.2.3 Registrar lote y ubicación en bodega
│   ├── 1.3 Registrar movimientos de inventario               (HU-S01)
│   │   ├── 1.3.1 Registrar entradas (compra, devolución, traslado)
│   │   ├── 1.3.2 Registrar salidas (venta, consumo de taller, baja)
│   │   ├── 1.3.3 Registrar traslados entre sucursales
│   │   └── 1.3.4 Mantener trazabilidad (usuario, fecha, documento origen)
│   ├── 1.4 Consultar stock en tiempo real                    (HU-S02)
│   │   ├── 1.4.1 Consultar existencias por producto
│   │   └── 1.4.2 Consultar kardex/histórico de movimientos
│   ├── 1.5 Consultar disponibilidad por sucursal             (HU-S08)
│   │   ├── 1.5.1 Filtrar por modelo/sucursal
│   │   └── 1.5.2 Verificar estado (disponible/reservada/vendida)
│   ├── 1.6 Calcular costo promedio                           (HU-S03)
│   │   ├── 1.6.1 Recalcular costo promedio ponderado en cada ingreso
│   │   └── 1.6.2 Valorizar el inventario a costo promedio
│   ├── 1.7 Registrar costos de importación                   (HU-S09)
│   │   ├── 1.7.1 Registrar costo de importación/flete/nacionalización
│   │   └── 1.7.2 Prorratear costos indirectos sobre las unidades del embarque
│   ├── 1.8 Gestionar alertas de stock mínimo                 (HU-S04)
│   │   ├── 1.8.1 Comparar existencias contra umbral de reorden
│   │   └── 1.8.2 Notificar a Compras cuando se alcanza el mínimo
│   └── 1.9 Realizar ajustes manuales de inventario           (HU-S05)
│       ├── 1.9.1 Registrar ajuste por conteo físico / faltante / sobrante
│       └── 1.9.2 Exigir motivo y autorización del ajuste
│
├── 2. Gestionar Facturación                        [épica SCRUM-18]
│   ├── 2.1 Registrar clientes                                (HU-F02)
│   │   ├── 2.1.1 Capturar datos fiscales (identificación, régimen, contacto)
│   │   └── 2.1.2 Validar unicidad por número de identificación
│   ├── 2.2 Facturar venta de moto                            (HU-F01)
│   │   ├── 2.2.1 Verificar disponibilidad en Stock/Costos    (HU-F07)
│   │   │   ├── 2.2.1.1 Consultar unidad por VIN
│   │   │   └── 2.2.1.2 Reservar/descontar la unidad al emitir
│   │   ├── 2.2.2 Calcular impuestos y accesorios
│   │   └── 2.2.3 Generar comprobante en PDF
│   ├── 2.3 Facturar orden de servicio (taller)               (HU-F08)
│   │   ├── 2.3.1 Registrar repuestos usados
│   │   │   └── 2.3.1.1 Descontar repuesto de inventario
│   │   └── 2.3.2 Registrar mano de obra del mecánico
│   ├── 2.4 Emitir notas crédito/débito                       (HU-F03)
│   │   ├── 2.4.1 Asociar la nota a una factura existente
│   │   └── 2.4.2 Ajustar impuestos y saldo de cartera
│   ├── 2.5 Controlar pagos y cartera                         (HU-F04)
│   │   ├── 2.5.1 Registrar pagos totales/parciales
│   │   ├── 2.5.2 Calcular saldo pendiente y días de mora
│   │   └── 2.5.3 Consultar estado de cuenta por cliente
│   ├── 2.6 Enviar factura al sistema contable / DIAN         (HU-F05)
│   │   ├── 2.6.1 Transformar la factura al formato electrónico requerido
│   │   ├── 2.6.2 Transmitir y registrar acuse/estado de recepción
│   │   └── 2.6.3 Reintentar y alertar ante fallo de envío
│   ├── 2.7 Consultar historial de facturación                (HU-F06)
│   │   ├── 2.7.1 Filtrar por fecha/cliente/estado/tipo
│   │   └── 2.7.2 Ver detalle y reimprimir comprobante
│   └── 2.8 Calcular comisión de vendedor                     (HU-F09)
│       ├── 2.8.1 Consultar módulo Empleados (% de comisión)
│       └── 2.8.2 Calcular comisión sobre venta neta y acumular por periodo
│
├── 3. Gestionar Compras                            [épica SCRUM-11]
│   ├── 3.1 Registrar productos                               (HU-01)
│   │   ├── 3.1.1 Capturar nombre, descripción, unidad de medida, categoría
│   │   └── 3.1.2 Validar unicidad del código de producto
│   ├── 3.2 Registrar proveedores                             (HU-02)
│   │   ├── 3.2.1 Capturar datos fiscales y de contacto
│   │   └── 3.2.2 Registrar condiciones comerciales (plazo de pago, descuentos)
│   ├── 3.3 Comparar precios y condiciones entre proveedores  (HU-09)
│   │   ├── 3.3.1 Registrar cotizaciones por proveedor
│   │   └── 3.3.2 Comparar precio, plazo de entrega y condiciones de pago
│   ├── 3.4 Generar orden de compra                           (HU-03)
│   │   ├── 3.4.1 Seleccionar modelos/repuestos y cantidades
│   │   │   ├── 3.4.1.1 Consultar histórico de ventas (EIS)   (HU-05)
│   │   │   └── 3.4.1.2 Considerar alertas de stock mínimo (Inventario)
│   │   └── 3.4.2 Registrar fecha estimada de entrega         (HU-06)
│   ├── 3.5 Aprobar orden de compra                           (HU-04)
│   │   ├── 3.5.1 Validar el monto contra el límite de aprobación del rol
│   │   └── 3.5.2 Registrar aprobación/rechazo con responsable y fecha
│   ├── 3.6 Recibir y validar pedido                          (HU-07)
│   │   ├── 3.6.1 Validar unidades/cantidades contra la orden de compra
│   │   ├── 3.6.2 Registrar faltantes, sobrantes y novedades
│   │   └── 3.6.3 Generar el ingreso a inventario de lo recibido conforme
│   └── 3.7 Hacer seguimiento y cancelar órdenes              (HU-08)
│       ├── 3.7.1 Consultar estado de la orden (emitida/aprobada/en tránsito/recibida)
│       └── 3.7.2 Cancelar una orden no recibida con motivo y autorización
│
├── 4. Gestionar Empleados                          [épica SCRUM-21]
│   ├── 4.1 Registrar personal                                (HU-E01)
│   │   ├── 4.1.1 Capturar datos personales y cargo
│   │   ├── 4.1.2 Registrar vendedores con % de comisión     (HU-E06)
│   │   └── 4.1.3 Registrar mecánicos con especialidad       (HU-E07)
│   ├── 4.2 Gestionar contratos                               (HU-E02)
│   │   ├── 4.2.1 Registrar tipo de contrato, salario y vigencia
│   │   └── 4.2.2 Alertar vencimiento/renovación de contrato
│   ├── 4.3 Controlar ausentismos y vacaciones                (HU-E03)
│   │   ├── 4.3.1 Registrar incapacidades, permisos y vacaciones
│   │   └── 4.3.2 Calcular saldo de días de vacaciones
│   ├── 4.4 Generar nómina básica                             (HU-E04)
│   │   ├── 4.4.1 Calcular devengados (salario, comisiones, horas extra)
│   │   ├── 4.4.2 Calcular deducciones (seguridad social, retenciones)
│   │   └── 4.4.3 Generar comprobante de pago (colilla)
│   ├── 4.5 Consultar histórico laboral del empleado          (HU-E05)
│   │   └── 4.5.1 Ver contratos, cargos y novedades previas
│   └── 4.6 Asignar orden de servicio a mecánico              (HU-E09)
│       ├── 4.6.1 Consultar disponibilidad de taller/mecánicos (HU-E08)
│       └── 4.6.2 Asignar según especialidad y carga de trabajo
│
├── 5. Gestionar Activos Fijos                      [épica SCRUM-20]
│   ├── 5.1 Registrar activos fijos                           (HU-A01)
│   │   ├── 5.1.1 Capturar descripción, fecha de adquisición, costo y vida útil
│   │   └── 5.1.2 Asignar código/placa de inventario único
│   ├── 5.2 Clasificar activos por categoría                  (HU-A02)
│   │   └── 5.2.1 Asociar categoría contable y método de depreciación
│   ├── 5.3 Calcular depreciación automáticamente             (HU-A03)
│   │   ├── 5.3.1 Calcular depreciación periódica según método y vida útil
│   │   └── 5.3.2 Acumular depreciación y actualizar valor en libros
│   ├── 5.4 Trasladar activos entre áreas                     (HU-A04)
│   │   └── 5.4.1 Registrar área origen/destino, responsable y fecha
│   ├── 5.5 Asignar activo fijo a un empleado/área            (HU-A09)
│   │   └── 5.5.1 Registrar el responsable (custodia) del activo
│   ├── 5.6 Registrar mantenimientos de activos               (HU-A07)
│   │   └── 5.6.1 Registrar mantenimiento preventivo/correctivo y su costo
│   ├── 5.7 Dar de baja activos fijos                         (HU-A05)
│   │   ├── 5.7.1 Registrar el motivo de baja (venta, obsolescencia, pérdida)
│   │   └── 5.7.2 Registrar el resultado en libros (utilidad/pérdida en baja)
│   ├── 5.8 Consultar inventario de activos                   (HU-A06)
│   │   └── 5.8.1 Filtrar por categoría/área/responsable/estado
│   └── 5.9 Generar reporte de valor en libros y depreciación (HU-A08)
│       └── 5.9.1 Mostrar costo, depreciación acumulada y valor neto por activo/categoría
│
└── 6. Gestionar Información Ejecutiva (EIS)         [épica SCRUM-22]
    ├── 6.1 Visualizar dashboard ejecutivo consolidado        (HU-EIS01)
    │   ├── 6.1.1 Consolidar indicadores de ventas, compras, taller y cartera
    │   └── 6.1.2 Actualizar el panel con los datos operativos vigentes
    ├── 6.2 Calcular indicadores (KPIs) de compras y ventas    (HU-EIS02)
    │   ├── 6.2.1 Calcular ventas por modelo/periodo/sucursal/vendedor
    │   └── 6.2.2 Calcular cumplimiento de compras (plazo y costo)
    ├── 6.3 Generar reportes consolidados exportables          (HU-EIS03)
    │   └── 6.3.1 Exportar a PDF/hoja de cálculo
    ├── 6.4 Aplicar filtros y segmentación de reportes         (HU-EIS05)
    │   └── 6.4.1 Filtrar por fecha, sucursal, línea de producto y responsable
    ├── 6.5 Generar reporte de histórico de ventas             (HU-EIS06)
    │   └── 6.5.1 Exponer ventas históricas por modelo para Compras (RF-3.4.1.1)
    ├── 6.6 Generar reporte de comisiones y desempeño          (HU-EIS07)
    │   └── 6.6.1 Mostrar comisiones causadas y ranking de vendedores
    ├── 6.7 Generar reporte de rentabilidad                    (HU-EIS08)
    │   └── 6.7.1 Cruzar ingresos y costos por línea/sucursal/periodo
    ├── 6.8 Gestionar alertas de desviación de metas           (HU-EIS04)
    │   └── 6.8.1 Comparar indicador real contra meta y notificar la desviación
    └── 6.9 Programar exportación y envío de reportes          (HU-EIS09)
        ├── 6.9.1 Definir periodicidad y destinatarios
        └── 6.9.2 Ejecutar y enviar el reporte de forma automática
```

## Detalle de los requisitos funcionales

### Rama 1 — Gestionar Inventario (Stock/Costos) · épica SCRUM-19

| ID | Nivel | Requisito | Descripción | HU / Issue |
|---|---|---|---|---|
| RF-1 | 1 | Gestionar Inventario | El sistema debe controlar el stock de motos y repuestos en tiempo real, por sucursal, con trazabilidad de cada movimiento y valorización a costo promedio. | SCRUM-19 |
| RF-1.1 | 2 | Registrar ingreso de moto nueva | Da de alta una unidad nueva en el inventario al recibirla de fábrica/distribuidor. | HU-S06 / SCRUM-66 |
| RF-1.1.1 | 3 | Capturar número de VIN/chasis | Registra el identificador único de la unidad, necesario para trazabilidad individual (no se maneja solo por cantidad). | HU-S06 |
| RF-1.1.2 | 3 | Registrar modelo, cilindraje, color, año | Guarda los atributos que diferencian variantes de un mismo modelo. | HU-S06 |
| RF-1.1.3 | 3 | Asignar sucursal de ingreso | Indica en qué sede queda físicamente disponible la unidad. | HU-S06 |
| RF-1.2 | 2 | Registrar ingreso de repuestos | Da de alta repuestos comprados a proveedores. | HU-S07 / SCRUM-67 |
| RF-1.2.1 | 3 | Asociar repuesto a modelo(s) compatible(s) | Vincula cada repuesto con las motos donde puede usarse, para facilitar búsquedas en el taller. | HU-S07 |
| RF-1.2.2 | 3 | Definir stock mínimo de reorden | Establece el umbral que dispara la alerta de reabastecimiento (RF-1.8). | HU-S07 |
| RF-1.2.3 | 3 | Registrar lote y ubicación en bodega | Guarda el lote y la posición física del repuesto para su localización. | HU-S07 |
| RF-1.3 | 2 | Registrar movimientos de inventario | Registra toda entrada, salida, traslado y ajuste, con su documento origen. | HU-S01 / SCRUM-37 |
| RF-1.3.1 | 3 | Registrar entradas | Ingresos por compra, devolución de cliente o traslado desde otra sucursal. | HU-S01 |
| RF-1.3.2 | 3 | Registrar salidas | Egresos por venta, consumo en orden de taller o baja. | HU-S01 |
| RF-1.3.3 | 3 | Registrar traslados entre sucursales | Mueve existencias de una sede a otra, descontando en origen y sumando en destino. | HU-S01 |
| RF-1.3.4 | 3 | Mantener trazabilidad del movimiento | Cada movimiento guarda usuario, fecha/hora y documento origen; el registro no es editable (ver RNF-7). | HU-S01 |
| RF-1.4 | 2 | Consultar stock en tiempo real | Muestra las existencias actualizadas al instante tras cada movimiento. | HU-S02 / SCRUM-38 |
| RF-1.4.1 | 3 | Consultar existencias por producto | Cantidad disponible de una moto o repuesto en cada sucursal. | HU-S02 |
| RF-1.4.2 | 3 | Consultar kardex/histórico de movimientos | Lista cronológica de entradas y salidas de un producto. | HU-S02 |
| RF-1.5 | 2 | Consultar disponibilidad por sucursal | Permite buscar si hay stock disponible de un modelo específico por sede. | HU-S08 / SCRUM-68 |
| RF-1.5.1 | 3 | Filtrar por modelo/sucursal | Acota la búsqueda por modelo de moto y por sede del concesionario. | HU-S08 |
| RF-1.5.2 | 3 | Verificar estado (disponible/reservada/vendida) | Muestra el estado actual de cada unidad para no ofrecer una moto ya comprometida. | HU-S08 |
| RF-1.6 | 2 | Calcular costo promedio | Mantiene el costo promedio ponderado de cada producto. | HU-S03 / SCRUM-39 |
| RF-1.6.1 | 3 | Recalcular costo promedio ponderado en cada ingreso | Ajusta el costo unitario al promediar existencias previas con el nuevo ingreso. | HU-S03 |
| RF-1.6.2 | 3 | Valorizar el inventario a costo promedio | Calcula el valor total del inventario con el costo promedio vigente. | HU-S03 |
| RF-1.7 | 2 | Registrar costos de importación | Suma al costo del producto los gastos de traerlo hasta el concesionario. | HU-S09 / SCRUM-69 |
| RF-1.7.1 | 3 | Registrar costo de importación/flete/nacionalización | Captura los gastos asociados al embarque. | HU-S09 |
| RF-1.7.2 | 3 | Prorratear costos indirectos sobre las unidades del embarque | Distribuye los gastos comunes entre las unidades recibidas. | HU-S09 |
| RF-1.8 | 2 | Gestionar alertas de stock mínimo | Avisa cuando un producto llega a su umbral de reorden. | HU-S04 / SCRUM-40 |
| RF-1.8.1 | 3 | Comparar existencias contra umbral de reorden | Evalúa el stock actual frente al mínimo definido en RF-1.2.2. | HU-S04 |
| RF-1.8.2 | 3 | Notificar a Compras cuando se alcanza el mínimo | Genera la alerta que alimenta la selección de cantidades en RF-3.4.1.2. | HU-S04 |
| RF-1.9 | 2 | Realizar ajustes manuales de inventario | Corrige existencias por diferencias de conteo físico. | HU-S05 / SCRUM-41 |
| RF-1.9.1 | 3 | Registrar ajuste por conteo físico / faltante / sobrante | Registra la diferencia entre el stock del sistema y el conteo real. | HU-S05 |
| RF-1.9.2 | 3 | Exigir motivo y autorización del ajuste | Todo ajuste requiere justificación y aprobación de un rol autorizado (ver RNF-7, RNF-11). | HU-S05 |

### Rama 2 — Gestionar Facturación · épica SCRUM-18

| ID | Nivel | Requisito | Descripción | HU / Issue |
|---|---|---|---|---|
| RF-2 | 1 | Gestionar Facturación | El sistema debe generar comprobantes de venta y de servicio técnico, con cálculo automático de impuestos, control de cartera y envío al sistema contable. | SCRUM-18 |
| RF-2.1 | 2 | Registrar clientes | Da de alta al cliente con sus datos fiscales antes de facturarle. | HU-F02 / SCRUM-31 |
| RF-2.1.1 | 3 | Capturar datos fiscales | Identificación, régimen tributario, dirección y contacto. | HU-F02 |
| RF-2.1.2 | 3 | Validar unicidad por número de identificación | Impide duplicar un cliente ya registrado. | HU-F02 |
| RF-2.2 | 2 | Facturar venta de moto | Genera una factura de venta de unidad, validando disponibilidad antes de emitirla. | HU-F01 / SCRUM-30 |
| RF-2.2.1 | 3 | Verificar disponibilidad en Stock/Costos | Antes de facturar, consulta si la unidad sigue disponible, evitando vender una moto ya reservada. | HU-F07 / SCRUM-63 |
| RF-2.2.1.1 | 4 | Consultar unidad por VIN | Localiza la unidad concreta en inventario. | HU-F07 |
| RF-2.2.1.2 | 4 | Reservar/descontar la unidad al emitir | Marca la unidad como vendida al confirmar la factura (movimiento en RF-1.3.2). | HU-F07 |
| RF-2.2.2 | 3 | Calcular impuestos y accesorios | Aplica automáticamente los impuestos y suma accesorios de la venta. | HU-F01 |
| RF-2.2.3 | 3 | Generar comprobante en PDF | Produce el archivo PDF de la venta, listo para entregar al cliente. | HU-F01 |
| RF-2.3 | 2 | Facturar orden de servicio (taller) | Genera una factura por trabajo de taller, incluyendo repuestos y mano de obra. | HU-F08 / SCRUM-64 |
| RF-2.3.1 | 3 | Registrar repuestos usados | Lista los repuestos consumidos durante la orden de servicio. | HU-F08 |
| RF-2.3.1.1 | 4 | Descontar repuesto de inventario | Al registrar el repuesto usado se descuenta automáticamente del stock (RF-1.3.2). | HU-F08 |
| RF-2.3.2 | 3 | Registrar mano de obra del mecánico | Agrega el costo de la mano de obra del mecánico asignado. | HU-F08 |
| RF-2.4 | 2 | Emitir notas crédito/débito | Ajusta una factura ya emitida por devolución, error o cargo adicional. | HU-F03 / SCRUM-32 |
| RF-2.4.1 | 3 | Asociar la nota a una factura existente | Vincula la nota con la factura que corrige. | HU-F03 |
| RF-2.4.2 | 3 | Ajustar impuestos y saldo de cartera | Recalcula impuestos y actualiza el saldo del cliente (RF-2.5). | HU-F03 |
| RF-2.5 | 2 | Controlar pagos y cartera | Lleva el saldo pendiente de cada cliente y su antigüedad. | HU-F04 / SCRUM-33 |
| RF-2.5.1 | 3 | Registrar pagos totales/parciales | Aplica los abonos recibidos a las facturas del cliente. | HU-F04 |
| RF-2.5.2 | 3 | Calcular saldo pendiente y días de mora | Determina cuánto y desde cuándo debe el cliente. | HU-F04 |
| RF-2.5.3 | 3 | Consultar estado de cuenta por cliente | Muestra las facturas, notas y pagos de un cliente. | HU-F04 |
| RF-2.6 | 2 | Enviar factura al sistema contable / DIAN | Transmite la factura al sistema contable / a la DIAN como factura electrónica. *(Historia nueva del backlog SCRUM; alineada con [AD-10](../arc-42/09_architecture_decisions.md) — arquitectura propuesta, no implementada; ver [alcance.md](../alcance.md).)* | HU-F05 / SCRUM-34 |
| RF-2.6.1 | 3 | Transformar la factura al formato electrónico requerido | Genera el documento en el formato exigido (p. ej. XML UBL con CUFE). | HU-F05 |
| RF-2.6.2 | 3 | Transmitir y registrar acuse/estado de recepción | Envía el documento y guarda la respuesta de la entidad receptora. | HU-F05 |
| RF-2.6.3 | 3 | Reintentar y alertar ante fallo de envío | Reintenta automáticamente y notifica si el envío es rechazado (ver RNF-8). | HU-F05 |
| RF-2.7 | 2 | Consultar historial de facturación | Permite buscar y revisar comprobantes emitidos. | HU-F06 / SCRUM-35 |
| RF-2.7.1 | 3 | Filtrar por fecha/cliente/estado/tipo | Acota la búsqueda de comprobantes. | HU-F06 |
| RF-2.7.2 | 3 | Ver detalle y reimprimir comprobante | Abre la factura y permite volver a generar su PDF. | HU-F06 |
| RF-2.8 | 2 | Calcular comisión de vendedor | Calcula la comisión del vendedor tras cerrar una venta. | HU-F09 / SCRUM-65 |
| RF-2.8.1 | 3 | Consultar módulo Empleados | Obtiene el porcentaje de comisión configurado para ese vendedor (RF-4.1.2). | HU-F09 |
| RF-2.8.2 | 3 | Calcular comisión sobre venta neta y acumular por periodo | Aplica el porcentaje y acumula el valor para la nómina (RF-4.4.1). | HU-F09 |

### Rama 3 — Gestionar Compras · épica SCRUM-11

| ID | Nivel | Requisito | Descripción | HU / Issue |
|---|---|---|---|---|
| RF-3 | 1 | Gestionar Compras | El sistema debe permitir mantener el catálogo y los proveedores, y generar, aprobar, recibir y hacer seguimiento de las órdenes de compra a fábrica/distribuidor. | SCRUM-11 |
| RF-3.1 | 2 | Registrar productos | Mantiene el catálogo de productos comprables con su información básica. | HU-01 / SCRUM-24 |
| RF-3.1.1 | 3 | Capturar nombre, descripción, unidad de medida, categoría | Datos mínimos del producto en el catálogo. | HU-01 |
| RF-3.1.2 | 3 | Validar unicidad del código de producto | Impide registrar un producto con un código ya usado. | HU-01 |
| RF-3.2 | 2 | Registrar proveedores | Da de alta a los proveedores de motos y repuestos. | HU-02 / SCRUM-28 |
| RF-3.2.1 | 3 | Capturar datos fiscales y de contacto | Identificación tributaria, dirección y contacto del proveedor. | HU-02 |
| RF-3.2.2 | 3 | Registrar condiciones comerciales | Plazo de pago, descuentos y condiciones de entrega pactadas. | HU-02 |
| RF-3.3 | 2 | Comparar precios y condiciones entre proveedores | Apoya la elección del proveedor para un pedido. | HU-09 / SCRUM-82 |
| RF-3.3.1 | 3 | Registrar cotizaciones por proveedor | Guarda las ofertas recibidas para un mismo producto. | HU-09 |
| RF-3.3.2 | 3 | Comparar precio, plazo de entrega y condiciones de pago | Muestra las cotizaciones lado a lado para decidir. | HU-09 |
| RF-3.4 | 2 | Generar orden de compra | Crea una orden con modelos/repuestos y cantidades solicitadas. | HU-03 / SCRUM-29 |
| RF-3.4.1 | 3 | Seleccionar modelos/repuestos y cantidades | Permite elegir qué pedir y en qué cantidad. | HU-03 |
| RF-3.4.1.1 | 4 | Consultar histórico de ventas (EIS) | Muestra las ventas pasadas por modelo para dimensionar el pedido (RF-6.5). | HU-05 / SCRUM-59 |
| RF-3.4.1.2 | 4 | Considerar alertas de stock mínimo | Incorpora los productos marcados por RF-1.8.2. | HU-05 |
| RF-3.4.2 | 3 | Registrar fecha estimada de entrega | Guarda cuándo se espera recibir el pedido, para planeación de inventario. | HU-06 / SCRUM-60 |
| RF-3.5 | 2 | Aprobar orden de compra | Somete la orden a aprobación antes de enviarla al proveedor. | HU-04 / SCRUM-36 |
| RF-3.5.1 | 3 | Validar el monto contra el límite de aprobación del rol | Verifica que el aprobador esté autorizado para ese valor. | HU-04 |
| RF-3.5.2 | 3 | Registrar aprobación/rechazo con responsable y fecha | Deja constancia de la decisión y de quién la tomó. | HU-04 |
| RF-3.6 | 2 | Recibir y validar pedido | Confirma el ingreso físico de las unidades pedidas. | HU-07 / SCRUM-61 |
| RF-3.6.1 | 3 | Validar unidades/cantidades contra la orden de compra | Compara lo recibido con lo solicitado. | HU-07 |
| RF-3.6.2 | 3 | Registrar faltantes, sobrantes y novedades | Documenta las diferencias frente a la orden. | HU-07 |
| RF-3.6.3 | 3 | Generar el ingreso a inventario de lo recibido conforme | Crea los movimientos de entrada en Stock/Costos (RF-1.3.1). | HU-07 |
| RF-3.7 | 2 | Hacer seguimiento y cancelar órdenes | Permite consultar el estado de las órdenes y cancelarlas. | HU-08 / SCRUM-62 |
| RF-3.7.1 | 3 | Consultar estado de la orden | Emitida / aprobada / en tránsito / recibida / cancelada. | HU-08 |
| RF-3.7.2 | 3 | Cancelar una orden no recibida con motivo y autorización | Anula la orden dejando registro del motivo y del responsable. | HU-08 |

### Rama 4 — Gestionar Empleados · épica SCRUM-21

| ID | Nivel | Requisito | Descripción | HU / Issue |
|---|---|---|---|---|
| RF-4 | 1 | Gestionar Empleados | El sistema debe administrar el personal del concesionario (vendedores, mecánicos, administrativos), sus contratos, novedades y nómina básica. | SCRUM-21 |
| RF-4.1 | 2 | Registrar personal | Da de alta a un nuevo empleado en el sistema. | HU-E01 / SCRUM-46 |
| RF-4.1.1 | 3 | Capturar datos personales y cargo | Identificación, datos de contacto y cargo del empleado. | HU-E01 |
| RF-4.1.2 | 3 | Registrar vendedores con % de comisión | Registra el porcentaje de comisión que gana el vendedor por venta cerrada (usado en RF-2.8.1). | HU-E06 / SCRUM-74 |
| RF-4.1.3 | 3 | Registrar mecánicos con especialidad | Registra la especialidad del mecánico (motor, eléctrico, general) para asignación de órdenes. | HU-E07 / SCRUM-75 |
| RF-4.2 | 2 | Gestionar contratos | Administra el contrato laboral de cada empleado. | HU-E02 / SCRUM-47 |
| RF-4.2.1 | 3 | Registrar tipo de contrato, salario y vigencia | Datos contractuales del empleado. | HU-E02 |
| RF-4.2.2 | 3 | Alertar vencimiento/renovación de contrato | Avisa antes de que expire un contrato a término fijo. | HU-E02 |
| RF-4.3 | 2 | Controlar ausentismos y vacaciones | Registra las novedades de tiempo no laborado. | HU-E03 / SCRUM-48 |
| RF-4.3.1 | 3 | Registrar incapacidades, permisos y vacaciones | Captura el tipo, las fechas y el soporte de cada novedad. | HU-E03 |
| RF-4.3.2 | 3 | Calcular saldo de días de vacaciones | Lleva los días causados y disfrutados por empleado. | HU-E03 |
| RF-4.4 | 2 | Generar nómina básica | Liquida el pago periódico de cada empleado. | HU-E04 / SCRUM-49 |
| RF-4.4.1 | 3 | Calcular devengados | Salario, comisiones (RF-2.8.2) y horas extra. | HU-E04 |
| RF-4.4.2 | 3 | Calcular deducciones | Aportes a seguridad social y retenciones. | HU-E04 |
| RF-4.4.3 | 3 | Generar comprobante de pago (colilla) | Produce el desprendible de nómina del empleado. | HU-E04 |
| RF-4.5 | 2 | Consultar histórico laboral del empleado | Muestra la trayectoria del empleado en la empresa. | HU-E05 / SCRUM-50 |
| RF-4.5.1 | 3 | Ver contratos, cargos y novedades previas | Consolida la información contractual e histórica. | HU-E05 |
| RF-4.6 | 2 | Asignar orden de servicio a mecánico | Vincula una orden de taller con el mecánico que la va a atender. | HU-E09 / SCRUM-77 |
| RF-4.6.1 | 3 | Consultar disponibilidad de taller/mecánicos | Verifica qué mecánicos están libres antes de asignar la orden. | HU-E08 / SCRUM-76 |
| RF-4.6.2 | 3 | Asignar según especialidad y carga de trabajo | Escoge el mecánico adecuado por especialidad (RF-4.1.3) y ocupación. | HU-E09 |

### Rama 5 — Gestionar Activos Fijos · épica SCRUM-20

| ID | Nivel | Requisito | Descripción | HU / Issue |
|---|---|---|---|---|
| RF-5 | 1 | Gestionar Activos Fijos | El sistema debe llevar el control del patrimonio del concesionario (herramientas, equipos, vehículos), su ubicación, custodia, depreciación y baja. | SCRUM-20 |
| RF-5.1 | 2 | Registrar activos fijos | Da de alta un activo con sus datos de adquisición. | HU-A01 / SCRUM-42 |
| RF-5.1.1 | 3 | Capturar descripción, fecha de adquisición, costo y vida útil | Datos base para la depreciación (RF-5.3). | HU-A01 |
| RF-5.1.2 | 3 | Asignar código/placa de inventario único | Identificador físico del activo. | HU-A01 |
| RF-5.2 | 2 | Clasificar activos por categoría | Agrupa los activos para efectos contables. | HU-A02 / SCRUM-43 |
| RF-5.2.1 | 3 | Asociar categoría contable y método de depreciación | Define cómo se deprecia cada grupo de activos. | HU-A02 |
| RF-5.3 | 2 | Calcular depreciación automáticamente | Calcula y acumula la depreciación de cada activo por periodo. | HU-A03 / SCRUM-44 |
| RF-5.3.1 | 3 | Calcular depreciación periódica según método y vida útil | Aplica el método (p. ej. línea recta) sobre la vida útil restante. | HU-A03 |
| RF-5.3.2 | 3 | Acumular depreciación y actualizar valor en libros | Descuenta la depreciación del periodo del valor neto del activo. | HU-A03 |
| RF-5.4 | 2 | Trasladar activos entre áreas | Registra el cambio de ubicación de un activo. | HU-A04 / SCRUM-45 |
| RF-5.4.1 | 3 | Registrar área origen/destino, responsable y fecha | Deja constancia del movimiento del activo. | HU-A04 |
| RF-5.5 | 2 | Asignar activo fijo a un empleado/área | Define quién responde por el activo. | HU-A09 / SCRUM-83 |
| RF-5.5.1 | 3 | Registrar el responsable (custodia) del activo | Vincula el activo con un empleado o área responsable. | HU-A09 |
| RF-5.6 | 2 | Registrar mantenimientos de activos | Lleva el historial de mantenimiento de cada activo. | HU-A07 / SCRUM-72 |
| RF-5.6.1 | 3 | Registrar mantenimiento preventivo/correctivo y su costo | Captura la fecha, el tipo y el costo del mantenimiento. | HU-A07 |
| RF-5.7 | 2 | Dar de baja activos fijos | Retira un activo del inventario patrimonial. | HU-A05 / SCRUM-70 |
| RF-5.7.1 | 3 | Registrar el motivo de baja | Venta, obsolescencia, pérdida o destrucción. | HU-A05 |
| RF-5.7.2 | 3 | Registrar el resultado en libros | Utilidad o pérdida generada por la baja. | HU-A05 |
| RF-5.8 | 2 | Consultar inventario de activos | Lista los activos con su estado y ubicación. | HU-A06 / SCRUM-71 |
| RF-5.8.1 | 3 | Filtrar por categoría/área/responsable/estado | Acota la consulta del inventario de activos. | HU-A06 |
| RF-5.9 | 2 | Generar reporte de valor en libros y depreciación | Informe contable del estado de los activos. | HU-A08 / SCRUM-73 |
| RF-5.9.1 | 3 | Mostrar costo, depreciación acumulada y valor neto | Por activo y consolidado por categoría. | HU-A08 |

### Rama 6 — Gestionar Información Ejecutiva (EIS) · épica SCRUM-22

| ID | Nivel | Requisito | Descripción | HU / Issue |
|---|---|---|---|---|
| RF-6 | 1 | Gestionar Información Ejecutiva (EIS) | El sistema debe consolidar la información operativa de los demás módulos en indicadores, tableros y reportes para la toma de decisiones gerenciales. | SCRUM-22 |
| RF-6.1 | 2 | Visualizar dashboard ejecutivo consolidado | Panel único con la situación del negocio. | HU-EIS01 / SCRUM-51 |
| RF-6.1.1 | 3 | Consolidar indicadores de ventas, compras, taller y cartera | Reúne datos de todos los módulos en una sola vista. | HU-EIS01 |
| RF-6.1.2 | 3 | Actualizar el panel con los datos operativos vigentes | Refresca los indicadores con la información más reciente (ver RNF-10). | HU-EIS01 |
| RF-6.2 | 2 | Calcular indicadores (KPIs) de compras y ventas | Calcula las métricas clave del negocio. | HU-EIS02 / SCRUM-52 |
| RF-6.2.1 | 3 | Calcular ventas por modelo/periodo/sucursal/vendedor | Descompone las ventas por las dimensiones de análisis. | HU-EIS02 |
| RF-6.2.2 | 3 | Calcular cumplimiento de compras (plazo y costo) | Compara lo pedido con lo recibido en tiempo y valor. | HU-EIS02 |
| RF-6.3 | 2 | Generar reportes consolidados exportables | Produce reportes que combinan varios módulos. | HU-EIS03 / SCRUM-53 |
| RF-6.3.1 | 3 | Exportar a PDF/hoja de cálculo | Descarga el reporte en un formato de ofimática. | HU-EIS03 |
| RF-6.4 | 2 | Aplicar filtros y segmentación de reportes | Permite acotar cualquier reporte por dimensiones. | HU-EIS05 / SCRUM-55 |
| RF-6.4.1 | 3 | Filtrar por fecha, sucursal, línea de producto y responsable | Segmenta los datos del reporte. | HU-EIS05 |
| RF-6.5 | 2 | Generar reporte de histórico de ventas | Informe de ventas pasadas por modelo. | HU-EIS06 / SCRUM-78 |
| RF-6.5.1 | 3 | Exponer ventas históricas por modelo para Compras | Alimenta la selección de cantidades de la orden de compra (RF-3.4.1.1). | HU-EIS06 |
| RF-6.6 | 2 | Generar reporte de comisiones y desempeño | Informe de comisiones causadas y rendimiento de vendedores. | HU-EIS07 / SCRUM-79 |
| RF-6.6.1 | 3 | Mostrar comisiones causadas y ranking de vendedores | Consolida el resultado comercial por vendedor. | HU-EIS07 |
| RF-6.7 | 2 | Generar reporte de rentabilidad | Informe de margen por línea, sucursal o periodo. | HU-EIS08 / SCRUM-80 |
| RF-6.7.1 | 3 | Cruzar ingresos y costos por línea/sucursal/periodo | Calcula el margen a partir de ventas y costo promedio (RF-1.6). | HU-EIS08 |
| RF-6.8 | 2 | Gestionar alertas de desviación de metas | Avisa cuando un indicador se aleja de su meta. | HU-EIS04 / SCRUM-54 |
| RF-6.8.1 | 3 | Comparar indicador real contra meta y notificar la desviación | Evalúa el cumplimiento y dispara la alerta. | HU-EIS04 |
| RF-6.9 | 2 | Programar exportación y envío de reportes | Automatiza la entrega periódica de reportes. | HU-EIS09 / SCRUM-81 |
| RF-6.9.1 | 3 | Definir periodicidad y destinatarios | Configura cada cuánto y a quién se envía el reporte. | HU-EIS09 |
| RF-6.9.2 | 3 | Ejecutar y enviar el reporte de forma automática | Genera y distribuye el reporte sin intervención manual. | HU-EIS09 |

---

## Trazabilidad con el backlog anterior

El esquema previo de 22 historias ([product-backlog.md](../product-backlog.md)) queda cubierto así:

| HU anterior | Equivale a |
|---|---|
| HU-01, HU-02, HU-03 | RF-1.1 (HU-S06), RF-1.2 (HU-S07), RF-1.5 (HU-S08) |
| HU-04, HU-05 | RF-1.8 (HU-S04), RF-1.7 (HU-S09) |
| HU-06, HU-07, HU-08 | RF-2.2 (HU-F01), RF-2.2.2 (HU-F01), RF-2.2.3 (HU-F01) |
| HU-09, HU-10, HU-11 | RF-2.3.1 (HU-F08), RF-2.3.2 (HU-F08), RF-2.8 (HU-F09) |
| HU-12, HU-13, HU-14, HU-15 | RF-3.4 (HU-03), RF-3.4.1.1 (HU-05), RF-3.4.2 (HU-06), RF-3.6 (HU-07) |
| HU-16, HU-17, HU-18 | RF-4.1.2 (HU-E06), RF-4.1.3 (HU-E07), RF-4.6 (HU-E09) |
| HU-19, HU-20 | RF-6.5 (HU-EIS06), RF-6.1 (HU-EIS01) |
| HU-21, HU-22 | RF-5.1 (HU-A01), RF-5.3 (HU-A03) |

Historias nuevas respecto de esa línea de base: registro de productos y proveedores
(RF-3.1, RF-3.2), comparación de proveedores (RF-3.3), aprobación y seguimiento de OC
(RF-3.5, RF-3.7), movimientos y ajustes de inventario (RF-1.3, RF-1.9), costo promedio
(RF-1.6), registro de clientes (RF-2.1), notas crédito/débito (RF-2.4), cartera (RF-2.5),
envío a sistema contable/DIAN (RF-2.6), historial de facturación (RF-2.7), contratos,
ausentismos, nómina e histórico laboral (RF-4.2 a RF-4.5), y casi todo Activos Fijos y
EIS (RF-5.2, RF-5.4 a RF-5.9; RF-6.2 a RF-6.4, RF-6.6 a RF-6.9).
