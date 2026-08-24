package com.utn.cafeteria.vista;

import com.utn.cafeteria.modelo.Factura;
import com.utn.cafeteria.modelo.MetodoPago;
import com.utn.cafeteria.util.FormatoUtil;
import com.utn.cafeteria.util.Mensajes;
import com.utn.cafeteria.util.Validador;
import javax.swing.JOptionPane;

/**
 * Dialoga con la persona usuaria durante la facturacion: pide el metodo de
 * pago y muestra la factura final.
 */
public final class VistaFactura {

    private VistaFactura() {
    }

    /**
     * Pide el metodo de pago con el que se cerrara la factura.
     *
     * @return el {@link MetodoPago} elegido, o {@code null} si la persona usuaria cancela.
     */
    public static MetodoPago pedirMetodoPago() {
        String mensaje = "Seleccione el metodo de pago:\n  1. Efectivo\n  2. Tarjeta";
        while (true) {
            String texto = JOptionPane.showInputDialog(null, mensaje, Mensajes.TITULO_METODO_PAGO,
                    JOptionPane.QUESTION_MESSAGE);
            if (texto == null) {
                return null;
            }
            String recortado = texto.trim();
            if (Validador.esEnteroValido(recortado)) {
                MetodoPago mp = MetodoPago.desdeOpcion(Integer.parseInt(recortado));
                if (mp != null) {
                    return mp;
                }
            }
            JOptionPane.showMessageDialog(null, Mensajes.METODO_PAGO_INVALIDO, Mensajes.TITULO_ERROR,
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Muestra la factura final, ya cerrada con su metodo de pago.
     *
     * @param f factura a mostrar.
     */
    public static void mostrarFactura(Factura f) {
        JOptionPane.showMessageDialog(null, FormatoUtil.enPanelMonoespaciado(f.toString(), 22, 60),
                Mensajes.TITULO_FACTURA, JOptionPane.INFORMATION_MESSAGE);
    }
}