package com.utn.cafeteria.controlador;

import com.utn.cafeteria.modelo.Caja;
import com.utn.cafeteria.modelo.Factura;
import com.utn.cafeteria.modelo.Inventario;
import com.utn.cafeteria.modelo.MetodoPago;
import com.utn.cafeteria.modelo.Operacion;
import com.utn.cafeteria.modelo.Producto;
import com.utn.cafeteria.modelo.ResultadoArqueo;
import com.utn.cafeteria.modelo.Rol;
import com.utn.cafeteria.modelo.Usuario;
import com.utn.cafeteria.servicio.ServicioAutenticacion;
import com.utn.cafeteria.servicio.ServicioAutorizacion;
import com.utn.cafeteria.servicio.ServicioCierreCaja;
import com.utn.cafeteria.servicio.ServicioFacturacion;
import com.utn.cafeteria.servicio.ServicioInventario;
import com.utn.cafeteria.util.Mensajes;
import com.utn.cafeteria.vista.MenuPrincipal;
import com.utn.cafeteria.vista.VentanaLogin;
import com.utn.cafeteria.vista.VistaCierreCaja;
import com.utn.cafeteria.vista.VistaCompra;
import com.utn.cafeteria.vista.VistaFactura;
import com.utn.cafeteria.vista.VistaGestionInventario;
import com.utn.cafeteria.vista.VistaProductos;
import javax.swing.JOptionPane;

//coordinacion completa de la aplicacion
public class ControladorCafeteria {

    private final Inventario inventario;
    private final Caja caja;
    private final ServicioAutenticacion servicioAutenticacion;
    private final ServicioAutorizacion servicioAutorizacion;
    private final ServicioInventario servicioInventario;
    private final ServicioFacturacion servicioFacturacion;
    private final ServicioCierreCaja servicioCierreCaja;

    private Factura facturaEnCurso;
    private Usuario usuarioActivo;

    //Crea el controlador e inicializa el inventario, la caja y los servicios de la sesion.

    public  ControladorCafeteria(){
        this.inventario = new Inventario();
        this.caja = new Caja();
        this.servicioAutenticacion = new ServicioAutenticacion();
        this.servicioAutorizacion = new ServicioAutorizacion();
        this.servicioInventario = new ServicioInventario(inventario);
        this.servicioFacturacion = new ServicioFacturacion();
        this.servicioCierreCaja = new ServicioCierreCaja(caja);
    }

    //Autentica a la persona usuaria y, si el ingreso es exitoso, arranca el ciclo del menu principal.
    public void iniciar(){
        usuarioActivo = vista.VentanaLogin.mostrar(servicioAutenticacion);
        if(usuarioActivo != null){
            System.out.println("[INFO] Sesion iniciada: usuario " + usuarioActivo.getNombreUsuario());
            ejecutarCicloMenu();
        }
        System.exit(0);
    }

    private void ejecutarCicloMenu(){
        Rol rol = usuarioActivo.getRol();
        String[] opciones = servicioAutorizacion.opcionesPara(rol);
        int numero;
        do{
            numero = MenuPrincipal.mostrarMenu(usuarioActivo.getNombreUsuario(), rol, opciones);
            if (op == null) {
                Mensajes.opcionInvalida(opciones.length);
                continue;
            }
            switch (op) {
                case VER_PRODUCTOS -> opcionVerProductos();
                case REGISTRAR_COMPRA -> opcionRegistrarCompra();
                case FACTURAR -> opcionFacturar();
                case CERRAR_CAJA -> opcionCerrarCaja();
                case GESTIONAR_INVENTARIO -> opcionGestionarInventario();
                case SALIR -> {
                    if (opcionSalir()) {
                        despedir();
                        return;
                    }
                }
            }
        } while (true);
    }
    private void opcionVerProductos() {
        VistaProductos.mostrarInventario(servicioInventario.generarReporte());
    }

    private void opcionRegistrarCompra() {
        if (caja.estaCerrada()) {
            mostrarAdvertencia(Mensajes.CAJA_CERRADA);
            return;
        }

        Producto producto = null;
        while (producto == null) {
            Integer codigo = VistaCompra.pedirCodigo(servicioInventario.generarReporte());
            if (codigo == null) {
                return;
            }
            producto = inventario.buscarPorCodigo(codigo);
            if (producto == null) {
                mostrarError(Mensajes.CODIGO_INEXISTENTE);
            }
        }

        Integer cantidad = VistaCompra.pedirCantidad(producto.getNombre(), producto.getStock());
        if (cantidad == null) {
            return;
        }

        if (facturaEnCurso == null) {
            facturaEnCurso = new Factura(usuarioActivo.getNombreUsuario());
        }

        String error = servicioFacturacion.agregarLinea(facturaEnCurso, inventario, producto.getCodigo(), cantidad);
        if (error != null) {
            mostrarError(error);
            return;
        }

        System.out.println("[INFO] Producto agregado al pedido: " + producto.getNombre() + " x" + cantidad);
        VistaCompra.mostrarPedido(facturaEnCurso);
    }
    private void opcionFacturar() {
        if (caja.estaCerrada()) {
            mostrarAdvertencia(Mensajes.CAJA_CERRADA);
            return;
        }
        if (facturaEnCurso == null || facturaEnCurso.estaVacia()) {
            mostrarAdvertencia(Mensajes.PEDIDO_VACIO);
            return;
        }

        // se revalida el stock justo antes de facturar, por si cambio desde que se armo el pedido
        String error = servicioInventario.validarLineasContra(facturaEnCurso);
        if (error != null) {
            mostrarError(error);
            return;
        }

        MetodoPago metodoPago = VistaFactura.pedirMetodoPago();
        if (metodoPago == null) {
            return;
        }

        servicioFacturacion.cerrarFactura(facturaEnCurso, metodoPago);
        servicioInventario.descargar(facturaEnCurso);
        caja.registrarVenta(facturaEnCurso);

        System.out.printf("[INFO] Venta registrada: factura %06d, metodo %s%n",
                facturaEnCurso.getNumero(), metodoPago.getEtiqueta());

        VistaFactura.mostrarFactura(facturaEnCurso);
        facturaEnCurso = null;
    }

    /**
     Cierra la caja del dia. Si quien esta en sesion no es administrador, la
     operacion exige una elevacion temporal de privilegios antes de continuar.
     */
    private void opcionCerrarCaja() {
        if (caja.estaCerrada()) {
            mostrarAdvertencia(Mensajes.CAJA_CERRADA);
            return;
        }

        // 1. Determinar con que identidad se ejecutara ESTA operacion.
        // 'autorizante' es una variable LOCAL: nunca se reasigna el atributo
        // usuarioActivo, para que la elevacion sea temporal y no se conserve
        // al volver al menu.
        Usuario autorizante = usuarioActivo;
        if (!usuarioActivo.esAdministrador()) {
            autorizante = elevarAAdministrador();
            if (autorizante == null) {
                return;
            }
        }

        Double efectivoDeclarado = VistaCierreCaja.pedirEfectivoDeclarado();
        if (efectivoDeclarado == null) {
            return;
        }
        Double tarjetaDeclarada = VistaCierreCaja.pedirTarjetaDeclarada();
        if (tarjetaDeclarada == null) {
            return;
        }

        ResultadoArqueo resultado =
                servicioCierreCaja.arquear(efectivoDeclarado, tarjetaDeclarada, autorizante.getNombreUsuario());
        System.out.println("[INFO] Caja cerrada. Facturas emitidas: " + caja.getFacturasEmitidas());
        VistaCierreCaja.mostrarResultado(resultado);
        // al terminar este metodo, 'autorizante' desaparece: la sesion sigue siendo la del cajero.
    }

    //Pide credenciales de administrador para autorizar una operacion restringida
    private Usuario elevarAAdministrador() {
        mostrarAdvertencia(Mensajes.CIERRE_REQUIERE_ELEVACION);

        String[] credenciales = VentanaLogin.solicitarElevacion();
        if (credenciales == null) {
            return null;
        }

        Usuario autenticado = servicioAutenticacion.autenticar(credenciales[0], credenciales[1].toCharArray());
        if (autenticado == null) {
            mostrarError(Mensajes.ELEVACION_CREDENCIALES_INVALIDAS);
            System.out.println("[SEGURIDAD] Elevacion denegada: credenciales invalidas");
            return null;
        }
        if (!autenticado.esAdministrador()) {
            mostrarError(Mensajes.SIN_PRIVILEGIOS);
            System.out.println("[SEGURIDAD] Elevacion denegada: usuario sin rol de administrador");
            return null;
        }

        System.out.println("[SEGURIDAD] Elevacion concedida a '" + autenticado.getNombreUsuario()
                + "' para CERRAR_CAJA");
        return autenticado;
    }

    //Ejecuta el submenu de gestion de inventario
    private void opcionGestionarInventario() {
        int opcion;
        do {
            opcion = VistaGestionInventario.mostrarSubmenu(inventario.getCantidadProductos(), Inventario.CAPACIDAD_MAX);
            switch (opcion) {
                case 1 -> opcionAgregarProducto();
                case 2 -> opcionEliminarProducto();
                case 3 -> opcionReabastecerProducto();
                case 4 -> {
                    return;
                }
                default -> Mensajes.opcionInvalida(4);
            }
        } while (true);
    }

    private void opcionEliminarProducto() {
        Integer codigo = VistaGestionInventario.pedirCodigo(servicioInventario.generarReporte(), "eliminar");
        if (codigo == null) {
            return;
        }
        Producto producto = inventario.buscarPorCodigo(codigo);
        if (producto == null) {
            mostrarError(Mensajes.CODIGO_INEXISTENTE);
            return;
        }
        if (!VistaGestionInventario.confirmarEliminacion(producto)) {
            return;
        }

        String error = servicioInventario.eliminarProducto(codigo, facturaEnCurso);
        if (error != null) {
            mostrarError(error);
            return;
        }

        System.out.println("[INFO] Producto eliminado: codigo " + codigo + ", " + producto.getNombre());
        mostrarInformacion(Mensajes.BAJA_EXITOSA);
    }

    private void opcionReabastecerProducto() {
        Integer codigo = VistaGestionInventario.pedirCodigo(servicioInventario.generarReporte(), "reabastecer");
        if (codigo == null) {
            return;
        }
        Producto producto = inventario.buscarPorCodigo(codigo);
        if (producto == null) {
            mostrarError(Mensajes.CODIGO_INEXISTENTE);
            return;
        }

        Integer cantidad = VistaGestionInventario.pedirCantidadReabastecer(producto.getNombre());
        if (cantidad == null) {
            return;
        }

        String error = servicioInventario.reabastecer(codigo, cantidad);
        if (error != null) {
            mostrarError(error);
            return;
        }

        System.out.println("[INFO] Producto reabastecido: codigo " + codigo + ", +" + cantidad + " unidades");
    }

    //Pide confirmacion de salida, advirtiendo si hay un pedido sin facturar.
    private boolean opcionSalir() {
        String mensaje = (facturaEnCurso != null && !facturaEnCurso.estaVacia())
                ? Mensajes.SALIR_CON_PEDIDO_PENDIENTE
                : Mensajes.CONFIRMAR_SALIDA;
        int opcion = JOptionPane.showConfirmDialog(null, mensaje, Mensajes.TITULO_CONFIRMAR_SALIDA,
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return opcion == JOptionPane.YES_OPTION;
    }

    private void despedir() {
        JOptionPane.showMessageDialog(null, Mensajes.DESPEDIDA, Mensajes.TITULO_INFORMACION,
                JOptionPane.INFORMATION_MESSAGE);
        System.out.println("[INFO] Sesion finalizada.");
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, Mensajes.TITULO_ERROR, JOptionPane.ERROR_MESSAGE);
    }

    private void mostrarAdvertencia(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, Mensajes.TITULO_ADVERTENCIA, JOptionPane.WARNING_MESSAGE);
    }

    private void mostrarInformacion(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, Mensajes.TITULO_INFORMACION, JOptionPane.INFORMATION_MESSAGE);
    }
}














