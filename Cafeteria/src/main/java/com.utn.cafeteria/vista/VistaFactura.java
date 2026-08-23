package com.utn.cafeteria.vista;

import com.utn.cafeteria.modelo.Factura;
import com.utn.cafeteria.modelo.MetodoPago;
import com.utn.cafeteria.util.FormatoUtil;
import com.utn.cafeteria.util.Mensajes;
import com.utn.cafeteria.util.Validador;
import com.utn.cafeteria.modelo.MetodoPago;

import javax.swing.JOptionPane;

public final class VistaFactura {
    private VistaFactura(){
    }

    //Solicita el metodo de pago
    public static MetodoPago pedirMetodoPago(){
        String mensaje = "Seleccione el metodo de pago: \n 1. Efectivo\n 2. Tarjeta";
        while (true) {
            String texto = JOptionPane.showInputDialog(null, mensaje, Mensajes.TITULO_METODO_PAGO, JOptionPane.INFORMATION_MESSAGE);
            if (texto == null) {
                return null;
            }
            String cortado = texto.trim();
            if (Validador.esEnteroValido(cortado)) {
                MetodoPago mp = MetodoPago.desdeOpcion(Integer.parseInt(cortado));
                if (mp != null) {
                    return mp;
                }
            }
            JOptionPane.showMessageDialog(null, Mensajes.METODO_PAGO_INVALIDO, Mensajes.TITULO_ERROR, JOptionPane.ERROR_MESSAGE);
        }    }
    }
    // Muestra la factura
    public static void MostarFactura(Factura f){
    JOptionPane.showMessageDialog(null, FormatoUtil.enPanelMonoespaciado(f.toString(), 20, 60),Mensajes. TITULO_FACTURA, JOptionPane.INFORMATION_MESSAGE);


}

