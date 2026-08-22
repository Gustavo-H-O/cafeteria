package com.utn.cafeteria.vista;

import com.utn.cafeteria.modelo.Usuario;
import com.utn.cafeteria.servicio.ServicioAutenticacion;
import com.utn.cafeteria.util.Mensajes;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

// Ventana de inicio de sesion. Solicita usuario y contrasena, enmascarando la contrasena, y limita los intentos permitidos.

public final class VentanaLogin {

    private static final int MAXIMO_INTENTOS = 3;

    private VentanaLogin() {
    }

    //Muestra el dialogo de inicio de sesion y autentica a la persona usuaria.

    public static Usuario mostrar(ServicioAutenticacion servicio) {
        int intentosRestantes = MAXIMO_INTENTOS;
        while (intentosRestantes > 0) {
            String usuario = JOptionPane.showInputDialog(null, "Usuario:", Mensajes.TITULO_LOGIN,
                    JOptionPane.QUESTION_MESSAGE);
            if (usuario == null) {
                return null;
            }
            if (usuario.isBlank()) {
                mostrarError(Mensajes.USUARIO_VACIO);
                continue;
            }

            char[] clave = pedirClave();
            if (clave == null) {
                return null;
            }
            if (clave.length == 0) {
                mostrarError(Mensajes.CONTRASENA_VACIA);
                continue;
            }

            Usuario autenticado = servicio.autenticar(usuario.trim(), clave);
            if (autenticado != null) {
                return autenticado;
            }

            intentosRestantes--;
            if (intentosRestantes > 0) {
                mostrarError(String.format(Mensajes.CREDENCIALES_INCORRECTAS, intentosRestantes));
            }
        }
        mostrarError(Mensajes.INTENTOS_AGOTADOS);
        return null;
    }

    // Solicita credenciales de administrador para elevar temporalmente los privilegios de la sesion activa
    public static String[] solicitarElevacion() {
        String usuario = JOptionPane.showInputDialog(null,
                "Esta operación requiere autorización de un administrador.\nUsuario administrador:",
                Mensajes.TITULO_ELEVACION, JOptionPane.QUESTION_MESSAGE);
        if (usuario == null) {
            return null;
        }
        JPasswordField campoClave = new JPasswordField();
        int opcion = JOptionPane.showConfirmDialog(null, campoClave, "Contraseña de administrador:",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (opcion != JOptionPane.OK_OPTION) {
            return null;
        }
        char[] clave = campoClave.getPassword();
        String claveTexto = new String(clave);
        java.util.Arrays.fill(clave, '0');
        return new String[] { usuario.trim(), claveTexto };
    }

    private static char[] pedirClave() {
        JPasswordField campoClave = new JPasswordField();
        int opcion = JOptionPane.showConfirmDialog(null, campoClave, "Contraseña:",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (opcion != JOptionPane.OK_OPTION) {
            return null;
        }
        return campoClave.getPassword();
    }

    private static void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, Mensajes.TITULO_ERROR, JOptionPane.ERROR_MESSAGE);
    }
}
