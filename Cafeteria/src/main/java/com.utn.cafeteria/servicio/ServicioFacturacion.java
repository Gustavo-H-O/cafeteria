package com.utn.cafeteria.servicio;

import com.utn.cafeteria.modelo.Factura;
import com.utn.cafeteria.modelo.Inventario;
import com.utn.cafeteria.modelo.LineaDetalle;
import com.utn.cafeteria.modelo.MetodoPago;
import com.utn.cafeteria.modelo.Producto;
import com.utn.cafeteria.util.Mensajes;

//Coordina el armado de una factura: valida productos y existencias
public class ServicioFacturacion {

    /** Tasa de impuesto sobre el subtotal de la factura (13 %). */
    public static final double IVA = 0.13;
    /** Porcentaje de descuento aplicado cuando el pago es en efectivo (5 %). */
    public static final double DESCUENTO_EFECTIVO = 0.05;
    /** Tolerancia aceptada al comparar montos declarados contra los registrados en caja. */
    public static final double TOLERANCIA_ARQUEO = 0.01;

    //Valida y agrega una linea a la factura en curso
    public String agregarLinea(Factura f, Inventario inv, int codigo, int cantidad) {
        Producto p = inv.buscarPorCodigo(codigo);
        if (p == null) {
            return Mensajes.CODIGO_INEXISTENTE;
        }

        LineaDetalle existente = f.buscarLinea(codigo);
        int yaEnPedido = existente != null ? existente.getCantidad() : 0;
        int totalSolicitado = yaEnPedido + cantidad;

        if (!p.hayStock(totalSolicitado)) {
            return String.format(Mensajes.STOCK_INSUFICIENTE, p.getStock(), p.getNombre());
        }

        if (!f.agregarLinea(p, cantidad)) {
            return Mensajes.FACTURA_LLENA;
        }
        return null;
    }

    //Cierra la factura asignandole el metodo de pago elegido.
    public void cerrarFactura(Factura f, MetodoPago mp) {
        f.setMetodoPago(mp);
    }
}
