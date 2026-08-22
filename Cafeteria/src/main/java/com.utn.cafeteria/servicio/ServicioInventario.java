package com.utn.cafeteria.servicio;

import com.utn.cafeteria.modelo.Factura;
import com.utn.cafeteria.modelo.Inventario;
import com.utn.cafeteria.modelo.LineaDetalle;
import com.utn.cafeteria.modelo.Producto;
import com.utn.cafeteria.util.Mensajes;

// Coordina las consultas y actualizaciones de existencias sobre el inventario de la cafeteria.

public class ServicioInventario {

    private final Inventario inventario;

    //Crea el servicio a partir del inventario que administrara.
    public ServicioInventario(Inventario inventario) {
        this.inventario = inventario;
    }

    //Indica si hay existencias suficientes de un producto.
    public boolean hayDisponibilidad(int codigo, int cantidad) {
        Producto p = inventario.buscarPorCodigo(codigo);
        return p != null && p.hayStock(cantidad);
    }

    //Revalida cada linea de una factura contra el stock actual del inventario
    public String validarLineasContra(Factura f) {
        LineaDetalle[] lineas = f.getLineas();
        for (int i = 0; i < lineas.length; i++) {
            Producto p = lineas[i].getProducto();
            if (!p.hayStock(lineas[i].getCantidad())) {
                return String.format(Mensajes.STOCK_INSUFICIENTE, p.getStock(), p.getNombre());
            }
        }
        return null;
    }

    //Descarga del inventario las cantidades vendidas en una factura.
    public void descargar(Factura f) {
        LineaDetalle[] lineas = f.getLineas();
        for (int i = 0; i < lineas.length; i++) {
            lineas[i].getProducto().rebajarStock(lineas[i].getCantidad());
        }
    }

    //Genera el reporte de texto del inventario actual.
    public String generarReporte() {
        return inventario.listarComoTexto();
    }

    //Valida y da de alta un producto nuevo en el inventario.
    public String agregarProducto(String nombre, double precio, int stockInicial) {
        if (nombre == null || nombre.isBlank() || nombre.length() > 30) {
            return Mensajes.NOMBRE_PRODUCTO_VACIO;
        }
        if (precio <= 0) {
            return Mensajes.PRECIO_INVALIDO;
        }
        if (stockInicial < 0) {
            return Mensajes.EXISTENCIAS_NEGATIVAS;
        }
        if (!inventario.hayEspacio()) {
            return Mensajes.INVENTARIO_LLENO;
        }
        Producto creado = inventario.agregarProducto(nombre, precio, stockInicial);
        System.out.println("[INFO] Producto agregado: codigo " + creado.getCodigo() + ", " + creado.getNombre());
        return null;
    }

/**
 * Aplica las tres reglas de bloqueo antes de eliminar un producto: que el
 * codigo exista, que el producto no forme parte del pedido en curso, y
 * que el catalogo no quede por debajo del minimo permitido.
 */
public String eliminarProducto(int codigo, Factura pedidoEnCurso) {
    Producto p = inventario.buscarPorCodigo(codigo);
    if (p == null) {
        return Mensajes.CODIGO_INEXISTENTE;
    }
    if (pedidoEnCurso != null && pedidoEnCurso.contieneProducto(codigo)) {
        return String.format(Mensajes.PRODUCTO_EN_PEDIDO, p.getNombre());
    }
    if (inventario.getCantidadProductos() - 1 < Inventario.MINIMO_PRODUCTOS) {
        return Mensajes.MINIMO_PRODUCTOS;
    }
    inventario.eliminarProducto(codigo);
    return null;
}

    //Suma unidades a las existencias actuales de un producto.
    public String reabastecer(int codigo, int cantidad) {
        Producto p = inventario.buscarPorCodigo(codigo);
        if (p == null) {
            return Mensajes.CODIGO_INEXISTENTE;
        }
        if (cantidad <= 0) {
            return Mensajes.CANTIDAD_REABASTECER_INVALIDA;
        }
        p.reintegrarStock(cantidad);
        return null;
    }
}
