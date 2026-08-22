package com.utn.cafeteria.modelo;

import com.utn.cafeteria.util.FormatoUtil;

public class LineaDetalle {

    // * Representa una linea de detalle dentro de una factura: un producto,la cantidad solicitada y el precio congelado al momento de la venta.
    private final Producto producto;
    private int cantidad;
    private final double precioAplicado;

    //Crea una linea de detalle congelando el precio actual del producto.
    public LineaDetalle(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        // el precio se congela aqui para que cambios futuros en el inventario
        // no alteren facturas ya iniciadas
        this.precioAplicado = producto.getPrecioUnitario();
    }

    //Obtiene el producto asociado a esta linea.
    public Producto getProducto() {
        return producto;
    }

    //Obtiene la cantidad solicitada en esta linea.
    public int getCantidad() {
        return cantidad;
    }

    //Obtiene el precio unitario congelado al momento de agregar la linea.
    public double getPrecioAplicado() {
        return precioAplicado;
    }

    //Calcula el subtotal de esta linea.
    public double getSubtotal() {
        return cantidad * precioAplicado;
    }

    //Suma unidades adicionales a la cantidad ya registrada en la linea.
    public void sumarCantidad(int adicional) {
        cantidad += adicional;
    }

    //Genera la fila formateada de esta linea para la factura.
    @Override
    public String toString() {
        return String.format("%3d   %-18s%4d%11s%15s",
                producto.getCodigo(), producto.getNombre(), cantidad,
                FormatoUtil.moneda(precioAplicado), FormatoUtil.moneda(getSubtotal()));
    }
}


