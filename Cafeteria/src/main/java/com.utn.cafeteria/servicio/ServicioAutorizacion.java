package com.utn.cafeteria.servicio;

import com.utn.cafeteria.modelo.Operacion;
import com.utn.cafeteria.modelo.Rol;
import com.utn.cafeteria.modelo.Usuario;

    // Clase con todos los permisos del sistema segun el rol
public class ServicioAutorizacion {

        private static final Operacion[] OPERACIONES_CAJERO = {
                Operacion.VER_PRODUCTOS, Operacion.REGISTRAR_COMPRA, Operacion.FACTURAR, Operacion.CERRAR_CAJA,
                Operacion.GESTIONAR_INVENTARIO, Operacion.SALIR
        };

        //Indica si el usuario tiene permiso para poder realizar una operacion
        public boolean tienePermiso(Usuario usuario, Operacion operacion) {
            Operacion[] operaciones = operacionesPara(usuario.getRol());
            for (int i = 0; i < operaciones.length; i++) {
                if (operaciones[i] == operacion) {
                    return true;
                }
            }
            return false;
        }

        //Obtiene las operaciones disponibles para un rol
        public Operacion[] operacionesPara(Rol rol) {
            return rol == Rol.ADMINISTRADOR ? OPERACIONES_ADMINISTRADOR : OPERACIONES_CAJERO;
        }

        //Construye las descripciones numeradas
        public String[] opcionesPara(Rol rol) {
            Operacion[] operaciones = operacionesPara(rol);
            String[] opciones = new String[operaciones.length];
            for (int i = 0; i < operaciones.length; i++) {
                opciones[i] = (i + 1) + ". " + operaciones[i].getDescripcion();
            }
            return opciones;
        }

        //Traduce el numero de opcion que digito la persona usuaria a la operacion
        public Operacion operacionDeOpcion(Rol rol, int numeroOpcion) {
            Operacion[] operaciones = operacionesPara(rol);
            if (numeroOpcion < 1 || numeroOpcion > operaciones.length) {
                return null;
            }
            return operaciones[numeroOpcion - 1];
        }

    }
