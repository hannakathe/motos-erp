import { useState, useEffect, useCallback } from "react";

const API_URL = "/api/ventas/historico";

function formatCurrency(value) {
  return new Intl.NumberFormat("es-CO", {
    style: "currency",
    currency: "COP",
    maximumFractionDigits: 0,
  }).format(value);
}

export default function HistoricoVentas() {
  const [filtros, setFiltros] = useState({
    fechaInicio: "",
    fechaFin: "",
    vendedorNombre: "",
  });
  const [ventas, setVentas] = useState([]);
  const [estado, setEstado] = useState("idle");
  const [error, setError] = useState(null);

  const buscar = useCallback(async (filtrosActuales) => {
    setEstado("loading");
    setError(null);
    try {
      const params = new URLSearchParams();
      if (filtrosActuales.fechaInicio) params.set("fechaInicio", filtrosActuales.fechaInicio);
      if (filtrosActuales.fechaFin) params.set("fechaFin", filtrosActuales.fechaFin);
      if (filtrosActuales.vendedorNombre) params.set("vendedorNombre", filtrosActuales.vendedorNombre);

      const resp = await fetch(`${API_URL}?${params.toString()}`);
      if (!resp.ok) {
        throw new Error(`Error del servidor (${resp.status})`);
      }
      const data = await resp.json();
      setVentas(data);
      setEstado("success");
    } catch (err) {
      setError(err.message || "No se pudo cargar el histórico de ventas");
      setEstado("error");
    }
  }, []);

  useEffect(() => {
    buscar(filtros);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  function handleChange(e) {
    const { name, value } = e.target;
    setFiltros((prev) => ({ ...prev, [name]: value }));
  }

  function handleSubmit(e) {
    e.preventDefault();
    buscar(filtros);
  }

  return (
    <section aria-labelledby="titulo-historico">
      <h1 id="titulo-historico">Histórico de ventas</h1>

      <form onSubmit={handleSubmit} className="filtros-historico">
        <label>
          Fecha desde
          <input type="date" name="fechaInicio" value={filtros.fechaInicio} onChange={handleChange} />
        </label>
        <label>
          Fecha hasta
          <input type="date" name="fechaFin" value={filtros.fechaFin} onChange={handleChange} />
        </label>
        <label>
          Vendedor
          <input type="text" name="vendedorNombre" value={filtros.vendedorNombre} onChange={handleChange} placeholder="Todos" />
        </label>
        <button type="submit" disabled={estado === "loading"}>Filtrar</button>
      </form>

      {estado === "loading" && <p role="status">Cargando histórico de ventas…</p>}

      {estado === "error" && (
        <div role="alert" className="mensaje-error">
          <p>{error}</p>
          <button onClick={() => buscar(filtros)}>Reintentar</button>
        </div>
      )}

      {estado === "success" && ventas.length === 0 && (
        <p>No hay ventas registradas para los filtros seleccionados.</p>
      )}

      {estado === "success" && ventas.length > 0 && (
        <table aria-label="Tabla de histórico de ventas">
          <thead>
            <tr>
              <th>Fecha</th>
              <th>Factura</th>
              <th>Cliente</th>
              <th>Vendedor</th>
              <th>Ítem vendido</th>
              <th>Total</th>
            </tr>
          </thead>
          <tbody>
            {ventas.map((venta) => (
              <tr key={venta.facturaId}>
                <td>{venta.fecha}</td>
                <td>{venta.numeroFactura}</td>
                <td>{venta.clienteNombre}</td>
                <td>{venta.vendedorNombre}</td>
                <td>{venta.itemDescripcion}</td>
                <td>{formatCurrency(venta.total)}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </section>
  );
}