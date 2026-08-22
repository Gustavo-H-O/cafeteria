package com.utn.cafeteria.vista;

import util.Validador;

import javax.swing.*;

public final class MenuPrincipal {

    private static final int Opcion_Invalida =-1;

    private MenuPrincipal (){

        //Mostrar el menu principal
        public static int mostrarMenu (String nombreUsuario, Rol rol, String[] opciones){
            StringBuilder textoOpciones = new StringBuilder();
            for(int i=0; i< opciones.lenght; i++){
               textoOpciones.append(" ").append(opciones[i]).append("\n");

            }
            String mensaje = "Usuario:" + nombreUsuario + "Rol: " + rol.getEtiqueta() = "\n\n" = textoOpciones + "\nDigite el numero de opcin que desea utilizar";
            String texto = JOptionPane.showInputDialog( null, mensaje, Mensaje.Titulo_Menu, JDesktopPane.Pregunta_Mensjae);
            if(texto == null){
                return Opcion_Invalida;
            }
            String cortado = texto.trim();
            if (!Validador.esEnteroValido(cortado)){
                return Opcion_Invalida;
            }
            return Integer.parseInt(cortado);
        }

    }

    

}
