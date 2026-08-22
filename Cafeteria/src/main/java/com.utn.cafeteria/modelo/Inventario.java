package com.utn.cafeteria.modelo;

public class Inventario {
    // Capacidad maxima del arreglo de productos.
    public  static final int CAPACIDAD_MAX = 20;
    //Cantidad minima de productos que debe conservar el catalogo.
    public static final int MINIMO_PRODUCTOS = 3;

    private final Producto[] productos;
    private int cantidadProductos;
    private int siguienteCodigo;

    //Crea el inventario cargando los tres productos iniciales de la cafeteria.
    public Inventario (){
        productos = new Producto[CAPACIDAD_MAX];
        productos[0] = new Producto(1, "Cafe", 1500.00, 20);
        productos[1] = new Producto(2, "Reposteria", 2200.00, 15);
        productos[2] = new Producto(3, "Refresco", 1200.00, 25);
        cantidadProductos = 3;
        siguienteCodigo = 4;
    }
    //Busca un producto por su codigo recorriendo el arreglo de productos.
    public Producto buscarPorCodigo(int codigo) {
        for (int i = 0; i < cantidadProductos; i++) {
            if (productos[i].getCodigo() == codigo) {
                return productos[i];
            }
        }
        return null;
    }

    //Indica si existe un producto con el codigo indicado.
    public boolean existeCodigo(int codigo) {
        return buscarPorCodigo(codigo) != null;
    }

    //Indica si el arreglo de productos tiene espacio para una nueva alta.
    public boolean hayEspacio() {
        return cantidadProductos < CAPACIDAD_MAX;
    }

    //Obtiene la cantidad de productos actualmente registrados.
    public int getCantidadProductos() {
        return cantidadProductos;
    }

    //Obtiene el arreglo interno de productos.
    public Producto[] getProductos() { return productos; }

    //Crea un producto nuevo, asignandole el siguiente codigo disponible.
    public Producto agregarProducto(String nombre, double precio, int stockInicial) {
        if (!hayEspacio()) {
            return null;
        }
        Producto nuevo = new Producto(siguienteCodigo, nombre, precio, stockInicial);
        productos[cantidadProductos] = nuevo;
        cantidadProductos++;
        siguienteCodigo++;
        return nuevo;
    }

    /**
     * Elimina un producto del catalogo, desplazando una posicion a la
     * izquierda todos los elementos posteriores para no dejar huecos en el arreglo.
     */
    public boolean eliminarProducto(int codigo) {
        int posicion = -1;
        for (int i = 0; i < cantidadProductos; i++) {
            if (productos[i].getCodigo() == codigo) {
                posicion = i;
                break;
            }
        }
        if (posicion == -1) {
            return false;
        }
        for (int i = posicion; i < cantidadProductos - 1; i++) {
            productos[i] = productos[i + 1];
        }
        productos[cantidadProductos - 1] = null;
        cantidadProductos--;
        return true;
    }

    //Construye una tabla con el listado completo de productos.
    public String listarComoTexto() {
        String linea = "=".repeat(57);
        String separador = "-".repeat(57);
        StringBuilder sb = new StringBuilder();
        sb.append(linea).append("\n");
        sb.append("              PRODUCTOS DISPONIBLES").append("\n");
        sb.append(linea).append("\n");
        sb.append(" COD  PRODUCTO             P. UNITARIO        STOCK").append("\n");
        sb.append(separador).append("\n");
        for (int i = 0; i < cantidadProductos; i++) {
            sb.append(productos[i].toString()).append("\n");
        }
        sb.append(linea);
        return sb.toString();
    }



}
