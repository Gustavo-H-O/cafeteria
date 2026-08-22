package vista;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
public final class VentanaLogin {
    // limitar los intentos de login a 3
    private static final int intentosMaximos = 3;

    private VentanaLogin() {
    }

    //Esto muestra el dialogo de inicio de sesion
    public static usuario mostrar(servicioAuntenticacion servicio) {
        int intentosRestantes = intentosMaximos;
        while (intentosRestantes > 0) {
            String usuario = JOptionPane.showInputDialog(null, "Usuario", Mensajes.TituloLogin,
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
        mostrarError(Mensajes.Muchos_Intentos_Fallidos);
        return null;
    }

    //Solicitud de credenciales de admin temporalmente
    public static String[] solicitarElevacion() {
        String usuario = JOptionPane.showInputDialog(null, "Esta operación requiere autorización de un administrador.\nUsuario administrador:",
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
        JOptionPane.showMessageDialog(null, mensaje, Mensajes.Titulo_Error, JOptionPane.ERROR_MESSAGE);
    }
}