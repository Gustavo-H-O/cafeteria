package com.utn.cafeteria.vista;

import com.utn.cafeteria.util.FormatoUtil;
import com.utn.cafeteria.util.Mensajes;
import javax.swing.JOptionPane;

public class VistaProductos {
    private VistaProductos(){
    }
    public static void mostrarInventario(String reporte){
        JOptionPane.showMessageDialog(null, FormatoUtil.enPanelMonoespaciado(reporte,12,60),Mensajes.TITULO_PRODUCTOS,JOptionPane.INFORMATION_MESSAGE, JOptionPane.INFORMATION_MESSAGE);

    }
}
