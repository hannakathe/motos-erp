package com.andimotors.compras.contrato;

import java.util.EnumSet;
import java.util.Set;

/**
 * MOCK / CONTRATO TEMPORAL &mdash; reemplazar cuando HU-12/13/14 esten integradas.
 *
 * <p>Estados posibles de una Orden de Compra / Pedido a fabrica-distribuidor.</p>
 *
 * <p>Origen: el codigo transitorio de HU-12 (commit {@code b8375c4}, luego borrado)
 * usaba el enum {@code ['pendiente','aprobado','recibido','cancelado']}. HU-15
 * ("Validar recepcion") necesita distinguir una recepcion conforme de una con
 * diferencias, por lo que agrega {@link #RECIBIDO_CON_DIFERENCIAS}. El equipo de
 * HU-12 debe adoptar este valor adicional al integrar (ver checklist de integracion
 * en docs/arc-42/05_building_block_view.md, seccion 5.7).</p>
 */
public enum EstadoPedido {

    /** Pedido generado (HU-12), aun no aprobado ni recibido. Recepcionable. */
    PENDIENTE,

    /** Pedido aprobado (HU-04, fuera de este Sprint). Recepcionable. */
    APROBADO,

    /** Recepcion validada sin diferencias entre lo solicitado y lo recibido. */
    RECIBIDO,

    /** Recepcion validada con faltantes y/o sobrantes en una o mas lineas. Introducido por HU-15. */
    RECIBIDO_CON_DIFERENCIAS,

    /** Pedido cancelado (HU-08, fuera de este Sprint). No recepcionable. */
    CANCELADO;

    private static final Set<EstadoPedido> RECEPCIONABLES = EnumSet.of(PENDIENTE, APROBADO);

    /**
     * @return {@code true} si un pedido en este estado puede pasar por la validacion
     *         de recepcion de HU-15.
     */
    public boolean esRecepcionable() {
        return RECEPCIONABLES.contains(this);
    }
}
