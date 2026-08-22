package com.utn.cafeteria.modelo;

import java.util.Arrays;

//Crear las clases donde se guardaran lso datos de lso usuarios
public class Usuario {

    private final String nombreUsuario;
    private final String clave;
    private final Rol rol;

    //Crear los usuarios de forma quemada y su rol
    public Usuario(String nombreUsuario, String clave, Rol rol){
        this.nombreUsuario = nombreUsuario;
        this.clave = clave;
        this.rol = rol;
    }

    //Verificador de las claves del usuario
    public boolean credencialValida(String usuario, char[] clave){
    boolean coincide = this.nombreUsuario.equals(usuario) && this.clave.equals(new String(clave));
    Arrays.fill(clave, '0');
    return coincide;
    }

    //Nombre de usuario
    public String getNombreUsuario() {return nombreUsuario;}

    //Rol asignado
    public Rol getRol() {return rol;}

    //verifica si el usuario tiene rol de admin
    public boolean esAdmin() {return rol.esAdmin();}
}
