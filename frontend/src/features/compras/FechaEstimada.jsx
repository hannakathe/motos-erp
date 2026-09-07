import { useState } from "react";

function esFechaValida(fecha) {
  if (!fecha) return "La fecha estimada es obligatoria.";
  const hoy = new Date();
  hoy.setHours(0, 0, 0, 0);
  const ingresada = new Date(`${fecha}T00:00:00`);
  if (ingresada < hoy) {
    return "La fecha estimada no puede ser anterior a hoy.";
  }
  return null;
}

export default function FechaEstimada({ pedidoId, fechaActual = null }) {
  const [fecha, setFecha] = useState(fechaActual ?? "");
  const [errorValidacion, setErrorValidacion] = useState(null);
  const [estado, setEstado] = useState("idle");
  const [errorApi, setErrorApi] = useState(null);
  const [fechaGuardada, setFechaGuardada] = useState(fechaActual);

  async function handleSubmit(e) {
    e.preventDefault();

    const mensajeValidacion = esFechaValida(fecha);
    setErrorValidacion(mensajeValidacion);
    if (mensajeValidacion) return;

    setEstado("loading");
    setErrorApi(null);
    try {
      const resp = await fetch(`/api/pedidos/${pedidoId}/fecha-estimada`, {
        method: "PATCH",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ fechaEstimada: fecha }),
      });
      if (!resp.ok) {
        throw new Error(`Error del servidor (${resp.status})`);
      }
      const data = await resp.json();
      setFechaGuardada(data.fechaEstimada);
      setEstado("success");
    } catch (err) {
      setErrorApi(err.message || "No se pudo guardar la fecha estimada");
      setEstado("error");
    }
  }

  return (
    <section aria-labelledby="titulo-fecha-estimada">
      <h2 id="titulo-fecha-estimada">Fecha estimada de recepción</h2>

      <form onSubmit={handleSubmit}>
        <label htmlFor="fechaEstimada">Fecha estimada</label>
        <input
          id="fechaEstimada"
          type="date"
          value={fecha}
          onChange={(e) => {
            setFecha(e.target.value);
            setErrorValidacion(null);
          }}
        />
        {errorValidacion && <p role="alert">{errorValidacion}</p>}

        <button type="submit" disabled={estado === "loading"}>
          {estado === "loading" ? "Guardando…" : "Guardar fecha estimada"}
        </button>
      </form>

      {estado === "error" && <p role="alert">{errorApi}</p>}

      {fechaGuardada && (
        <p>
          Fecha estimada registrada: <strong>{fechaGuardada}</strong>
        </p>
      )}
    </section>
  );
}