package com.utn.cafeteria.servicio;

import com.utn.cafeteria.modelo.Rol;
import com.utn.cafeteria.modelo.Usuario;
import java.util.Arrays;

public class ServicioAutenticacion {

    private final Usuario[] usuarios;

    //Creacion del servicio de usuarios autorizados
    public ServicioAutenticacion() {
        usuarios = new Usuario[] {
                new Usuario("admin", "utn2026", Rol.ADMINISTRADOR),
                new Usuario("cajero", "caja123", Rol.CAJERO)
        };
    }

    // Verifica y auntentica el usuario en el arerglo registado
    public Usuario autenticar(String usuario, char[] clave) {
        for (int i = 0; i < usuarios.length; i++) {
            if (usuarios[i].getNombreUsuario().equals(usuario)) {
                // solo se compara (y se borra la clave) contra el usuario cuyo nombre coincide,
                // para no invalidar la clave original antes de revisar al usuario correcto
                return usuarios[i].credencialesValidas(usuario, clave) ? usuarios[i] : null;
            }
        }
        Arrays.fill(clave, '0');
        return null;
    }

    //Esto exige que el rol de el usuario sea admin
    public Usuario autenticarAdministrador(String usuario, char[] clave) {
        Usuario autenticado = autenticar(usuario, clave);
        if (autenticado == null || !autenticado.esAdministrador()) {
            return null;
        }
        return autenticado;
    }

}
