package com.utn.cafeteria.vista;

import com.utn.cafeteria.modelo.Factura;
import com.utn.cafeteria.util.FormatoUtil;
import com.utn.cafeteria.util.Mensajes;
import com.utn.cafeteria.util.Validador;
import javax.swing.JOptionPane;

//muestra un dialogo al usuario en el registro
public final class VistaCompra {
    private VistaCompra() {
    }

        //pide el codigo del producto a comprar, mostando el inventario
        public static Integer pedirCodigo(String reporte){
            String mensaje = "Productos Disponibles: \n\n" + reporte + "\n\nDigite el codigo del producto que quiere comprar:";
            return Validador.leerEntero(mensaje, Mensajes.TITULO_CODIGO, 1, Integer.MAX_VALUE);

        }
        //Solicita la cantidad
        public static Integer pedirCantidad(String nombreProducto, int disponible){
            String mensaje = String.format("Ingrese la cantidad de %s que desea compar (disponibles: %d:",
                    nombreProducto, disponible);
            while(true){
                String texto = JOptionPane.showInputDialog(null,mensaje, Mensajes.TITULO_CANTIDAD, JOptionPane.QUESTION_MESSAGE);
                if(texto == null){
                    return null;
                }
                String cortado = texto.trim();
                if (!Validador.esEnteroValido(cortado)|| Integer.parseInt(cortado)<=0){
                    JOptionPane.showMessageDialog(null, Mensajes.CANTIDAD_NO_NUMERICA, Mensajes.TITULO_ERROR,JOptionPane.ERROR_MESSAGE);
                    continue;
                }
                return Integer.parseInt(cortado);
            }
        }
        //Muestra el pedido
        public static void mostrarPedido(Factura f){
            JOptionPane.showMessageDialog(null, FormatoUtil.enPanelMonoespaciado(f.toString(), 20, 60),Mensajes.TITULO_PEDIDO, JOptionPane.INFORMATION_MESSAGE);
        }
    }

