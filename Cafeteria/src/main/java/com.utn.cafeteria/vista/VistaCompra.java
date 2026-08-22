package com.utn.cafeteria.vista;

//import com.utn.cafeteria.modelo.Factura;
//import com.utn.cafeteria.util.FormatoUtil;
//import com.utn.cafeteria.util.Mensajes;
import com.utn.cafeteria.util.Validador;
import javax.swing.JOptionPane;

//muestra un dialogo al usuario en el registro
public final class VistaCompra {
    private VistaCompra() {

        //pide el codigo del producto a comprar, mostando el inventario
        public static Integer pedirCodigo(String reporte){
            String mensaje = "Productos Disponibles: \n\n" + reporte + "\n\nDigite el codigo del producto que quiere comprar:";
            return util.Validador.leerEntero(mensaje, Mensajes.Titulo_Codigo, 1, Integer.MAX_VALUE);

        }
        //Solicita la cantidad
        public static Integer pedirCantidad(String nombreProducto, int disponible){
            String mensaje = String.format("Ingrese la cantidad de %s que desea compar (disponibles: %d:",
                    nombreProducto, disponible);
            while(true){
                String texto = JOptionPane.showInputDialog(null,mensaje, Mensajes.Titulo_Cantidad, JOptionPane.QUESTION_MESSAGE);
                if(texto == null){
                    return null;
                }
                String cortado = texto.trim();
                if (!Validador.esEnteroValido(cortado)|| Integer.parseInt(cortado)<=0){
                    JOptionPane.showMessageDialog(null, Mensajes.Cantidad_No_Numerica, Mensajes.Titulo_Error,JOptionPane.ERROR_MESSAGE);
                    continue;
                }
                return Integer.parseInt(cortado);
            }
        }
        //Muestra el pedido
        public static void mostrarPedido(Factura f){
            JOptionPane.showMessageDialog(null, FormatoUtil.enPanelMonoespaciado(f.toString(), 20, 60),Mensajes.Titulo_Pedido, JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
