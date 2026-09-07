import { render, screen, waitFor, fireEvent } from "@testing-library/react";
import { describe, test, expect, vi, beforeEach, afterEach } from "vitest";
import HistoricoVentas from "./HistoricoVentas";

const ventaMock = {
  facturaId: 1,
  numeroFactura: "F-0001",
  fecha: "2026-08-01",
  clienteNombre: "Juan Pérez",
  vendedorNombre: "Ana Gómez",
  itemDescripcion: "Moto AKT 125",
  total: 8500000,
};

beforeEach(() => {
  global.fetch = vi.fn();
});

afterEach(() => {
  vi.resetAllMocks();
});

test("muestra el estado de carga y luego la tabla con datos", async () => {
  global.fetch.mockResolvedValueOnce({
    ok: true,
    json: async () => [ventaMock],
  });

  render(<HistoricoVentas />);

  expect(screen.getByRole("status")).toHaveTextContent(/cargando/i);

  await waitFor(() => expect(screen.getByRole("table")).toBeInTheDocument());
  expect(screen.getByText("F-0001")).toBeInTheDocument();
  expect(screen.getByText("Juan Pérez")).toBeInTheDocument();
});

test("muestra un mensaje cuando no hay ventas para los filtros", async () => {
  global.fetch.mockResolvedValueOnce({ ok: true, json: async () => [] });

  render(<HistoricoVentas />);

  await waitFor(() =>
    expect(screen.getByText(/no hay ventas registradas/i)).toBeInTheDocument()
  );
});

test("muestra error y permite reintentar si la API falla", async () => {
  global.fetch
    .mockResolvedValueOnce({ ok: false, status: 500, json: async () => ({}) })
    .mockResolvedValueOnce({ ok: true, json: async () => [ventaMock] });

  render(<HistoricoVentas />);

  await waitFor(() => expect(screen.getByRole("alert")).toBeInTheDocument());
  expect(screen.getByText(/error del servidor/i)).toBeInTheDocument();

  fireEvent.click(screen.getByText(/reintentar/i));

  await waitFor(() => expect(screen.getByRole("table")).toBeInTheDocument());
  expect(global.fetch).toHaveBeenCalledTimes(2);
});