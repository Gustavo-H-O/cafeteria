package servicio;

import com.utn.cafeteria.modelo.Usuario;
import java.util.Arrays;

public class ServicioAuntenticador {

    private final Usuario[] usuarios;

    //Creacion del servicio de usuarios autorizados
    public ServicioAuntenticador() {
        usuarios = new Usuario[]{
                new Usuario( "admin", "utn2026", Rol.Admin),
                new Usuario ( "cajero", "caja1", Rol.Cajero)

        }

    }
    // Verifica y auntentica el usuario en el arerglo registado
    public Usuario autenticar(String usuario, char[] clave){
        for (int i = 0; i < usuarios.length; i++){
            if (usuarios[i].getNombreUsuario().equals(usuario)){
                // esto solo compara el usuario y valida que corresponda
                //y tambien para no invalidar la clave original antes de verificar el usuario
                return usuarios[i].credencialValida(usuario, clave) ? usuarios[i] :null;
            }
        }
        Arrays.fill(clave, '0');
        return null;
    }
    //Esto exige que el rol de el usuario sea admin
    public Usuario auntenticarAdmin(String usuario, char[] clave){
       Usuario auntenticado = auntenticar (usuario, clave);
       if(auntenticado == null|| !auntenticado.esAdmin()){
           return null;
       }
       return auntenticado;
    }

}
