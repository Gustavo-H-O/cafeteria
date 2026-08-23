package com.utn.cafeteria.vista;

import com.utn.cafeteria.modelo.Producto;
import com.utn.cafeteria.util.FormatoUtil;
import com.utn.cafeteria.util.Mensajes;
import com.utn.cafeteria.util.Validador;
import javax.swing.JOptionPane;


public class VistaGestionInventario {
    private static final int OPCION_INVALIDA = -1;

    private VistaGestionInventario(){
    }
    //Ensena el sub menu de la gestion del inventario
    public static int mostrarSubmenu(int registrados, int capacidad){
        String linea = "=".repeat(57);
        String separador = "-".repeat(57);
        String mensaje = linea + "\n"
                + "              GESTION DE INVENTARIO\n"
                + linea + "\n"
                + String.format(" Productos registrados: %d de %d", registrados, capacidad) + "\n"
                + separador + "\n"
                + " 1. Agregar un producto nuevo \n"
                + " 2. Eliminar un producto existente\n"
                + " 3. Reabastecer las existencias de un producto\n"
                + " 4. Volver al menu principal\n"
                + linea + "\n\nDigite el numero de la opcion deseada:";
        String texto =JOptionPane.showInputDialog(null, mensaje,Mensajes.TITULO_GESTION_INVENTARIO,JOptionPane.QUESTION_MESSAGE);
        if(texto== null || !Validador.esEnteroValido(texto.trim())){
            return OPCION_INVALIDA;
        }
        return Integer.parseInt(texto.trim());
    }
    // Solicita los datos de un producto nuevo
    public static Object[] pedirDatosNuevoProducto() {
        String nombre = Validador.leerTextoNoVacio("Digite el nombre del producto nuevo:",
                Mensajes.TITULO_AGREGAR_PRODUCTO, 30);
        if (nombre == null) {
            return null;
        }
        // se admite 0 en este dialogo a proposito: es ServicioInventario quien
        // rechaza un precio no positivo con el mensaje literal de negocio
        Double precio = Validador.leerDecimal("Digite el precio unitario del producto:",
                Mensajes.TITULO_AGREGAR_PRODUCTO, 0);
        if (precio == null) {
            return null;
        }
        Integer stockInicial = Validador.leerEntero("Digite las existencias iniciales del producto:",
                Mensajes.TITULO_AGREGAR_PRODUCTO, 0, Integer.MAX_VALUE);
        if (stockInicial == null) {
            return null;
        }
        return new Object[] { nombre, precio, stockInicial };
    }
    /**
     * Pide el codigo de un producto sobre el que se va a actuar, mostrando el inventario como referencia.
     *
     * @param reporte texto del inventario, ya formateado, para mostrarlo como referencia.
     * @param accion  verbo de la accion a realizar (por ejemplo, {@code "eliminar"} o {@code "reabastecer"}),
     *                para personalizar el mensaje.
     * @return el codigo digitado, o {@code null} si la persona usuaria cancela.
     */
    public static Integer pedirCodigo(String reporte, String accion) {
        String mensaje = "Productos disponibles:\n\n" + reporte
                + "\n\nDigite el codigo del producto que desea " + accion + ":";
        return Validador.leerEntero(mensaje, Mensajes.TITULO_GESTION_INVENTARIO, 1, Integer.MAX_VALUE);
    }

    /**
     * Pide confirmacion antes de eliminar un producto, mostrando sus datos completos.
     *
     * @param p producto que se va a eliminar.
     * @return {@code true} si la persona usuaria confirma la eliminacion.
     */
    public static boolean confirmarEliminacion(Producto p) {
        String mensaje = "  Va a eliminar el siguiente producto del catalogo:\n\n"
                + "     Codigo:      " + p.getCodigo() + "\n"
                + "     Nombre:      " + p.getNombre() + "\n"
                + "     Precio:      " + FormatoUtil.moneda(p.getPrecioUnitario()) + "\n"
                + "     Existencias: " + p.getStock() + " unidades\n\n"
                + "  Esta accion no se puede deshacer. Confirma?";
        return Validador.confirmar(mensaje, Mensajes.TITULO_ELIMINAR_PRODUCTO);
    }

    /**
     * Pide la cantidad de unidades a reabastecer de un producto.
     *
     * @param nombreProducto nombre del producto elegido, para mostrarlo en el mensaje.
     * @return la cantidad digitada, o {@code null} si la persona usuaria cancela.
     */
    public static Integer pedirCantidadReabastecer(String nombreProducto) {
        String mensaje = "Digite la cantidad de unidades a reabastecer de " + nombreProducto + ":";
        return Validador.leerEntero(mensaje, Mensajes.TITULO_REABASTECER, 1, Integer.MAX_VALUE);
    }
}
