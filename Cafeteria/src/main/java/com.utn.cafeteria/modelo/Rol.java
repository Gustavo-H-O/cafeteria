package com.utn.cafeteria.modelo;

public enum Rol {
    // Persona con acceso completo, incluida la gestion de inventario.
    ADMINISTRADOR("Administrador"),
    // Persona con acceso operativo: ventas, facturacion y cierre de caja con elevacion.
    CAJERO("Cajero");

    private final String etiqueta;

    Rol(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    //Obtiene la etiqueta legible del rol.

    public String getEtiqueta() {
        return etiqueta;
    }

    //Indica si este rol corresponde al de administrador.

    public boolean esAdministrador() {
        return this == ADMINISTRADOR;
    }
}

