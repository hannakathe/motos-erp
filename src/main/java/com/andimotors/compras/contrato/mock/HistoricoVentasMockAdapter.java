package com.andimotors.compras.contrato.mock;

import com.andimotors.compras.contrato.HistoricoVentasContrato;
import com.andimotors.compras.contrato.HistoricoVentasPort;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.YearMonth;
import java.util.Map;

/**
 * MOCK &mdash; reemplazar cuando HU-13 / el modulo EIS esten integrados.
 *
 * <p>Simula {@code EIS.consultarHistoricoVentas()}. Devuelve un total sembrado por
 * producto, sin depender del rango de meses (suficiente para PT-13.1 y PT-13.2).</p>
 */
@Component
@Profile("mock")
public class HistoricoVentasMockAdapter implements HistoricoVentasPort {

    /** productoId &rarr; unidades vendidas sembradas. Productos ausentes: 0 ventas. */
    private static final Map<Long, Long> VENTAS_SEMILLA = Map.of(
            500L, 42L,   // "Casco MX Pro": producto con ventas (PT-13.1)
            501L, 18L,
            502L, 7L
    );

    @Override
    public HistoricoVentasContrato consultarHistoricoVentas(Long productoId, YearMonth desde, YearMonth hasta) {
        long unidades = VENTAS_SEMILLA.getOrDefault(productoId, 0L);
        return new HistoricoVentasContrato(productoId, desde, hasta, unidades);
    }
}
