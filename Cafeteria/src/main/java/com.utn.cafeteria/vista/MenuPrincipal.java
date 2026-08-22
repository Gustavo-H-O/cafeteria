package com.utn.cafeteria.vista;


import com.utn.cafeteria.modelo.Rol;
//import com.utn.cafeteria.util.Mensajes;
import com.utn.cafeteria.util.Validador;
import javax.swing.JOptionPane;

public final class MenuPrincipal {

    private static final int OPCION_INVALIDA =-1;

    private MenuPrincipal (){

        //Mostrar el menu principal
        public static int mostrarMenu (String nombreUsuario, Rol rol, String[] opciones){
            StringBuilder textoOpciones = new StringBuilder();
            for(int i=0; i< opciones.lenght; i++){
               textoOpciones.append(" ").append(opciones[i]).append("\n");

            }
            String mensaje = "Usuario:" + nombreUsuario + "Rol: " + rol.getEtiqueta() = "\n\n" = textoOpciones + "\nDigite el numero de opcin que desea utilizar";
            String texto = JOptionPane.showInputDialog( null, mensaje, Mensaje.TITULO_MENU, JDesktopPane.Pregunta_Mensjae);
            if(texto == null){
                return OPCION_INVALIDA;
            }
            String cortado = texto.trim();
            if (!Validador.esEnteroValido(cortado)){
                return OPCION_INVALIDA;
            }
            return Integer.parseInt(cortado);
        }

    }

    

}
