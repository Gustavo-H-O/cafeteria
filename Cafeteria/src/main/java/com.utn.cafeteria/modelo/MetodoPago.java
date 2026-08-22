package com.utn.cafeteria.modelo;


public enum MetodoPago {

    // Pago en efectivo, con descuento del 5 %.
    EFECTIVO(0.05),
    // Pago con tarjeta, sin descuento.
    TARJETA(0.0);

    private final double porcentajeDescuento;

    MetodoPago(double porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }


     //Obtiene el porcentaje de descuento asociado a este metodo de pago.

    public double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }


    public static MetodoPago desdeOpcion(int opcion) {
        return switch (opcion) {
            case 1 -> EFECTIVO;
            case 2 -> TARJETA;
            default -> null;
        };
    }


    public String getEtiqueta() {
        return this == EFECTIVO ? "EFECTIVO" : "TARJETA";
    }
}