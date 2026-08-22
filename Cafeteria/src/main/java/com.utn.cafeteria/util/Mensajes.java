package com.utn.cafeteria.util;

import javax.swing.JOptionPane;

    //Centraliza todos los textos visibles para la persona usuaria
public final class Mensajes {

    private Mensajes() {
    }

        // ----- Titulos de dialogo -----

        /** Titulo generico para mensajes de error. */
        public static final String TITULO_ERROR = "Error";
        /** Titulo generico para mensajes de advertencia. */
        public static final String TITULO_ADVERTENCIA = "Advertencia";
        /** Titulo generico para mensajes informativos. */
        public static final String TITULO_INFORMACION = "Informacion";
        /** Titulo de la ventana de inicio de sesion. */
        public static final String TITULO_LOGIN = "Inicio de sesion";
        /** Titulo del dialogo de elevacion de privilegios. */
        public static final String TITULO_ELEVACION = "Autorizacion de administrador";
        /** Titulo del menu principal. */
        public static final String TITULO_MENU = "Menu principal - Cafeteria Starbucks";
        /** Titulo del listado de productos. */
        public static final String TITULO_PRODUCTOS = "Productos disponibles";
        /** Titulo del dialogo que pide el codigo de un producto. */
        public static final String TITULO_CODIGO = "Codigo de producto";
        /** Titulo del dialogo que pide una cantidad. */
        public static final String TITULO_CANTIDAD = "Cantidad";
        /** Titulo del dialogo que pide el metodo de pago. */
        public static final String TITULO_METODO_PAGO = "Metodo de pago";
        /** Titulo del dialogo que muestra la factura. */
        public static final String TITULO_FACTURA = "Factura";
        /** Titulo del dialogo que muestra el pedido en curso. */
        public static final String TITULO_PEDIDO = "Pedido en curso";
        /** Titulo del cierre de caja. */
        public static final String TITULO_CIERRE_CAJA = "Cierre de caja";
        /** Titulo del dialogo que pide el efectivo declarado. */
        public static final String TITULO_EFECTIVO = "Efectivo declarado";
        /** Titulo del dialogo que pide el monto de tarjeta declarado. */
        public static final String TITULO_TARJETA = "Tarjeta declarada";
        /** Titulo del dialogo de confirmacion de salida. */
        public static final String TITULO_CONFIRMAR_SALIDA = "Confirmar salida";
        /** Titulo del submenu de gestion de inventario. */
        public static final String TITULO_GESTION_INVENTARIO = "Gestion de inventario";
        /** Titulo del dialogo para agregar un producto nuevo. */
        public static final String TITULO_AGREGAR_PRODUCTO = "Agregar producto";
        /** Titulo del dialogo para eliminar un producto. */
        public static final String TITULO_ELIMINAR_PRODUCTO = "Eliminar producto";
        /** Titulo del dialogo para reabastecer un producto. */
        public static final String TITULO_REABASTECER = "Reabastecer producto";

        // ----- Mensajes de autenticacion -----

        /** Mensaje mostrado cuando el nombre de usuario se deja vacio. */
        public static final String USUARIO_VACIO = "Debe digitar un nombre de usuario.";
        /** Mensaje mostrado cuando la contrasena se deja vacia. */
        public static final String CONTRASENA_VACIA = "Debe digitar la contraseña.";
        /** Plantilla para credenciales incorrectas; se completa con la cantidad de intentos restantes. */
        public static final String CREDENCIALES_INCORRECTAS =
                "Usuario o contraseña incorrectos. Le quedan %d intento(s).";
        /** Mensaje mostrado al agotarse los intentos de inicio de sesion. */
        public static final String INTENTOS_AGOTADOS =
                "Se agotaron los intentos permitidos. El sistema se cerrará.";

        // ----- Mensajes del menu -----

        /** Plantilla para opcion de menu invalida; se completa con la cantidad de opciones disponibles. */
        public static final String OPCION_INVALIDA = "Seleccione una opción válida entre 1 y %d.";

        // ----- Mensajes de elevacion de privilegios -----

        /** Mensaje mostrado al cajero antes de pedirle credenciales de administrador para cerrar caja. */
        public static final String CIERRE_REQUIERE_ELEVACION =
                "El cierre de caja requiere autorización de un administrador.";
        /** Mensaje mostrado cuando la elevacion se cancela por credenciales incorrectas. */
        public static final String ELEVACION_CREDENCIALES_INVALIDAS =
                "Credenciales incorrectas. El cierre de caja fue cancelado.";
        /** Mensaje mostrado cuando quien se autentica en la elevacion no tiene rol de administrador. */
        public static final String SIN_PRIVILEGIOS =
                "El usuario indicado no tiene rol de administrador. El cierre fue cancelado.";

        // ----- Mensajes de compra -----

        /** Mensaje mostrado cuando el codigo digitado no existe en el inventario. */
        public static final String CODIGO_INEXISTENTE = "El código digitado no corresponde a ningún producto.";
        /** Mensaje mostrado cuando la cantidad digitada no es un entero positivo valido. */
        public static final String CANTIDAD_NO_NUMERICA = "La cantidad debe ser un número entero mayor que cero.";
        /** Plantilla para existencias insuficientes; se completa con el stock disponible y el nombre del producto. */
        public static final String STOCK_INSUFICIENTE =
                "Existencias insuficientes: solo hay %d unidades de %s. La compra fue cancelada.";
        /** Mensaje mostrado cuando la factura ya alcanzo el numero maximo de lineas distintas. */
        public static final String FACTURA_LLENA =
                "No es posible agregar más productos: la factura alcanzó el número máximo de líneas.";

        // ----- Mensajes de facturacion -----

        /** Mensaje mostrado cuando la opcion de metodo de pago no es 1 ni 2. */
        public static final String METODO_PAGO_INVALIDO = "Seleccione 1 para efectivo o 2 para tarjeta.";
        /** Mensaje mostrado cuando el monto digitado no es numerico. */
        public static final String MONTO_NO_NUMERICO = "Digite un monto numérico válido, sin letras ni símbolos.";
        /** Mensaje mostrado al intentar facturar sin productos registrados. */
        public static final String PEDIDO_VACIO = "No hay productos registrados. Primero registre una compra.";

        // ----- Mensajes de caja -----

        /** Mensaje mostrado al intentar operar sobre una caja ya cerrada. */
        public static final String CAJA_CERRADA = "La caja ya fue cerrada. No se pueden registrar más operaciones.";

    // ----- Mensajes de gestion de inventario -----

        /** Mensaje mostrado cuando el nombre de un producto nuevo se deja vacio. */
        public static final String NOMBRE_PRODUCTO_VACIO = "El nombre del producto no puede quedar vacío.";
        /** Mensaje mostrado cuando el precio de un producto nuevo no es mayor que cero. */
        public static final String PRECIO_INVALIDO = "El precio debe ser un número mayor que cero.";
        /** Mensaje mostrado cuando las existencias iniciales digitadas son negativas. */
        public static final String EXISTENCIAS_NEGATIVAS = "Las existencias iniciales no pueden ser negativas.";
        /** Mensaje mostrado cuando el inventario ya alcanzo su capacidad maxima. */
        public static final String INVENTARIO_LLENO = "El inventario alcanzó su capacidad máxima de 20 productos.";
        /** Plantilla para bloquear la baja de un producto que esta en el pedido en curso. */
        public static final String PRODUCTO_EN_PEDIDO =
                "No se puede eliminar %s porque forma parte del pedido en curso.";
        /** Mensaje mostrado cuando eliminar un producto dejaria el catalogo por debajo del minimo. */
        public static final String MINIMO_PRODUCTOS =
                "El catálogo debe conservar al menos 3 productos. La eliminación fue cancelada.";
        /** Mensaje mostrado cuando la cantidad a reabastecer no es mayor que cero. */
        public static final String CANTIDAD_REABASTECER_INVALIDA =
                "La cantidad a reabastecer debe ser mayor que cero.";
        /** Plantilla para alta exitosa de un producto; se completa con el codigo asignado. */
        public static final String ALTA_EXITOSA = "Producto agregado con el código %d.";
        /** Mensaje mostrado tras eliminar un producto con exito. */
        public static final String BAJA_EXITOSA = "Producto eliminado del catálogo.";

        // ----- Mensajes de salida -----

        /** Mensaje mostrado al confirmar la salida con un pedido sin facturar. */
        public static final String SALIR_CON_PEDIDO_PENDIENTE = "Hay un pedido sin facturar. ¿Confirma que desea salir?";
        /** Mensaje mostrado al confirmar la salida sin pedidos pendientes. */
        public static final String CONFIRMAR_SALIDA = "¿Confirma que desea salir del sistema?";
        /** Mensaje de despedida al cerrar la aplicacion. */
        public static final String DESPEDIDA = "Gracias por usar el sistema. ¡Hasta pronto!";

        /**
         * Muestra el dialogo de opcion de menu invalida, indicando el rango valido segun
         * la cantidad de opciones que tiene el menu de la persona usuaria activa.
         *
         * @param cantidadOpciones cantidad de opciones disponibles en el menu actual.
         */
        public static void opcionInvalida(int cantidadOpciones) {
            JOptionPane.showMessageDialog(null, String.format(OPCION_INVALIDA, cantidadOpciones),
                    TITULO_ERROR, JOptionPane.ERROR_MESSAGE);
        }
}
