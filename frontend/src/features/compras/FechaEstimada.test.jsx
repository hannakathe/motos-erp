import { render, screen, waitFor, fireEvent } from "@testing-library/react";
import { describe, test, expect, vi, beforeEach, afterEach } from "vitest";
import FechaEstimada from "./FechaEstimada";

beforeEach(() => {
  global.fetch = vi.fn();
});

afterEach(() => {
  vi.resetAllMocks();
});

test("rechaza una fecha anterior a hoy sin llamar a la API", async () => {
  render(<FechaEstimada pedidoId={1} />);

  fireEvent.change(screen.getByLabelText(/^fecha estimada$/i), {
    target: { value: "2020-01-01" },
  });
  fireEvent.click(screen.getByText(/guardar fecha estimada/i));

  expect(await screen.findByText(/no puede ser anterior a hoy/i)).toBeInTheDocument();
  expect(global.fetch).not.toHaveBeenCalled();
});

test("exige que la fecha sea obligatoria", async () => {
  render(<FechaEstimada pedidoId={1} />);

  fireEvent.click(screen.getByText(/guardar fecha estimada/i));

  expect(await screen.findByText(/es obligatoria/i)).toBeInTheDocument();
  expect(global.fetch).not.toHaveBeenCalled();
});

test("guarda y muestra la fecha estimada cuando es válida", async () => {
  const fechaFutura = "2027-01-15";
  global.fetch.mockResolvedValueOnce({
    ok: true,
    json: async () => ({ fechaEstimada: fechaFutura }),
  });

  render(<FechaEstimada pedidoId={1} />);

  fireEvent.change(screen.getByLabelText(/^fecha estimada$/i), {
    target: { value: fechaFutura },
  });
  fireEvent.click(screen.getByText(/guardar fecha estimada/i));

  await waitFor(() =>
    expect(screen.getByText(/fecha estimada registrada/i)).toBeInTheDocument()
  );
  expect(screen.getByText(fechaFutura)).toBeInTheDocument();
  expect(global.fetch).toHaveBeenCalledWith(
    "/api/pedidos/1/fecha-estimada",
    expect.objectContaining({ method: "PATCH" })
  );
});

test("muestra error si la API falla", async () => {
  global.fetch.mockResolvedValueOnce({ ok: false, status: 500 });

  render(<FechaEstimada pedidoId={1} />);

  fireEvent.change(screen.getByLabelText(/^fecha estimada$/i), {
    target: { value: "2027-01-15" },
  });
  fireEvent.click(screen.getByText(/guardar fecha estimada/i));

  expect(await screen.findByText(/error del servidor/i)).toBeInTheDocument();
});