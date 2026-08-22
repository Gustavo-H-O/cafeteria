package com.utn.cafeteria.vista;

import com.utn.cafeteria.modelo.ResultadoArqueo;
import com.utn.cafeteria.util.FormatoUtil;
import com.utn.cafeteria.util.Mensajes;
import com.utn.cafeteria.util.Validador;
import javax.swing.JOptionPane;


public final class VistaCierreCaja {

    private VistaCierreCaja() {
    }


      //Pide el monto de efectivo contado fisicamente en caja.
    public static Double pedirEfectivoDeclarado() {
        return Validador.leerDecimal("Digite el monto de efectivo contado en caja:", Mensajes.TITULO_EFECTIVO, 0);
    }


     // Pide el monto de ventas con tarjeta declarado.

    public static Double pedirTarjetaDeclarada() {
        return Validador.leerDecimal("Digite el monto de ventas con tarjeta declarado:", Mensajes.TITULO_TARJETA, 0);
    }

    //Muestra el resultado del arqueo de caja.
    public static void mostrarResultado(ResultadoArqueo r) {
        JOptionPane.showMessageDialog(null, FormatoUtil.enPanelMonoespaciado(r.describir(), 14, 60),
                Mensajes.TITULO_CIERRE_CAJA, JOptionPane.INFORMATION_MESSAGE);
    }
}
