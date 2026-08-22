package com.utn.cafeteria.vista;

import com.utn.cafeteria.modelo.Rol;
import com.utn.cafeteria.util.Mensajes;
import com.utn.cafeteria.util.Validador;
import javax.swing.JOptionPane;

    //Muestra el menu principal del sistema y devuelve la opcion elegida

    public final class MenuPrincipal {

    /** Valor devuelto cuando la entrada no es un numero; quien invoca este metodo debe rechazarla. */
    private static final int OPCION_INVALIDA = -1;

    private MenuPrincipal() {
    }

    // Muestra el menu principal con las opciones que le corresponden al rol activo.

    public static int mostrarMenu(String nombreUsuario, Rol rol, String[] opciones) {
        StringBuilder textoOpciones = new StringBuilder();
        for (int i = 0; i < opciones.length; i++) {
            textoOpciones.append("  ").append(opciones[i]).append("\n");
        }
        String mensaje = "Usuario: " + nombreUsuario + "   Rol: " + rol.getEtiqueta() + "\n\n"
                + textoOpciones + "\nDigite el numero de la opcion deseada:";
        String texto = JOptionPane.showInputDialog(null, mensaje, Mensajes.TITULO_MENU,
                JOptionPane.QUESTION_MESSAGE);
        if (texto == null) {
            return OPCION_INVALIDA;
        }
        String recortado = texto.trim();
        if (!Validador.esEnteroValido(recortado)) {
            return OPCION_INVALIDA;
        }
        return Integer.parseInt(recortado);
    }
}
