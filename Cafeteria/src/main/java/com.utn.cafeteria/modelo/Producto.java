package com.utn.cafeteria.modelo;

import com.utn.cafeteria.util.FormatoUtil;

public class Producto {

    private final int codigo;
    private final String nombre;
    private final double precioUnitario;
    private int stock;

    //Crea un producto con sus existencias iniciales.
    public Producto(int codigo, String nombre, double precioUnitario, int stockInicial) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precioUnitario = precioUnitario;
        this.stock = stockInicial;
    }

    //Obtiene el codigo del producto.
    public int getCodigo() {
        return codigo;
    }

    //Obtiene el nombre del producto
    public String getNombre() {
        return nombre;
    }

    //Obtiene las existencias actuales del producto.
    public int getStock() {
        return stock;
    }

    //Indica si hay existencias suficientes para vender la cantidad solicitada.
    public boolean hayStock(int cantidad) {
        return cantidad > 0 && cantidad <= stock;
    }

    //Descuenta unidades del stock disponible.
    public void rebajarStock(int cantidad) {
        if (!hayStock(cantidad)) {
            throw new IllegalArgumentException(
                    "No hay existencias suficientes de " + nombre + " para rebajar " + cantidad + " unidad(es).");
        }
        stock -= cantidad;
    }

    //Devuelve unidades al stock, por ejemplo al cancelar una operacion.
    public void reintegrarStock(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad a reintegrar debe ser mayor que cero.");
        }
        stock += cantidad;
    }

    //Genera la fila formateada de este producto para el listado de inventario.
    @Override
    public String toString() {
        return String.format("%3d   %-21s%11s%13d",
                codigo, nombre, FormatoUtil.moneda(precioUnitario), stock);
    }

}
