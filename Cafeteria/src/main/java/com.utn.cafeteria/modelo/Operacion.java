package com.utn.cafeteria.modelo;

public enum Operacion {

    VER_PRODUCTOS("Ver productos disponibles y stock", false),
    REGISTRAR_COMPRA("Registrar una compra", false),
    FACTURAR("Facturar", false),
    CERRAR_CAJA("Cerrar caja", false),
    GESTIONAR_INVENTARIO("Gestionar inventario", true),
    SALIR("Salir del sistema", false);

    private final String descripcion;
    private final boolean exclusivaAdministrador;

    Operacion(String descripcion, boolean exclusivaAdministrador) {
        this.descripcion = descripcion;
        this.exclusivaAdministrador = exclusivaAdministrador;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean esExclusivaAdministrador() {
        return exclusivaAdministrador;
    }
}
