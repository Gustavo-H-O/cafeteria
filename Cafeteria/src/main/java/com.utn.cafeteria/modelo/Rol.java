package com.utn.cafeteria.modelo;

public enum Rol {
    Admin("Admin"),
    Cajero("Cajero");

    private final String etiqueta;

    Rol(String etiqueta) { this.etiqueta = etiqueta;}
    //Da la etiqueta legible
    public String getEtiqueta() {return etiqueta;}
    //Indica si tiene rol de admin
    public boolean esAdmin(){return this== Admin;}

}
