package com.utn.cafeteria.modelo;

import com.utn.cafeteria.util.FormatoUtil;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

//* Representa una factura de venta: numero consecutivo, fecha, lineas de detalle, metodo de pago y la persona que atendio la venta.

public class Factura {

    /** Cantidad maxima de lineas de detalle que admite una factura. */
    private static final int MAXIMO_LINEAS = 10;

    // el IVA se define de forma local porque el modelo no puede depender de la capa de servicio
    private static final double TASA_IMPUESTO = 0.13;

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private static int contador = 0;

    private final int numero;
    private final LocalDateTime fechaHora;
    private final LineaDetalle[] lineas;
    private int cantidadLineas;
    private MetodoPago metodoPago;
    private final String atendidoPor;

    //Crea una nueva factura, asignandole un numero consecutivo y la fecha actual.
    public Factura(String atendidoPor) {
        contador++;
        this.numero = contador;
        this.fechaHora = LocalDateTime.now();
        this.lineas = new LineaDetalle[MAXIMO_LINEAS];
        this.cantidadLineas = 0;
        this.atendidoPor = atendidoPor;
    }

         //Agrega una linea a la factura. Si el producto ya esta en el pedido, acumula la cantidad en la linea existente en lugar de crear una nueva.

    public boolean agregarLinea(Producto p, int cantidad) {
        LineaDetalle existente = buscarLinea(p.getCodigo());
        if (existente != null) {
            existente.sumarCantidad(cantidad);
            return true;
        }
        if (cantidadLineas >= lineas.length) {
            return false;
        }
        lineas[cantidadLineas] = new LineaDetalle(p, cantidad);
        cantidadLineas++;
        return true;
    }

    //Busca la linea de detalle correspondiente a un codigo de producto.
    public LineaDetalle buscarLinea(int codigo) {
        for (int i = 0; i < cantidadLineas; i++) {
            if (lineas[i].getProducto().getCodigo() == codigo) {
                return lineas[i];
            }
        }
        return null;
    }

    //Indica si un producto forma parte del pend en curso.
    public boolean contieneProducto(int codigo) {
        return buscarLinea(codigo) != null;
    }

    //Calcula la suma de los subtotales de todas las lineas.
    public double calcularSubtotal() {
        double subtotal = 0;
        for (int i = 0; i < cantidadLineas; i++) {
            subtotal += lineas[i].getSubtotal();
        }
        return subtotal;
    }

    //Calcula el impuesto de la factura.
    public double calcularImpuesto() {
        return calcularSubtotal() * TASA_IMPUESTO;
    }

    //Calcula el total bruto de la factura, antes de aplicar descuentos.
    public double calcularTotalBruto() {
        return calcularSubtotal() + calcularImpuesto();
    }

    //Calcula el descuento aplicable segun el metodo de pago.
    public double calcularDescuento() {
        if (metodoPago == null) {
            return 0;
        }
        return calcularTotalBruto() * metodoPago.getPorcentajeDescuento();
    }

    //Calcula el total final que debe pagar la persona cliente.
    public double calcularTotalPagar() {
        return calcularTotalBruto() - calcularDescuento();
    }

    //Indica si la factura no tiene lineas registradas.
    public boolean estaVacia() {
        return cantidadLineas == 0;
    }

    //Obtiene la cantidad de lineas registradas en la factura.
    public int getCantidadLineas() {
        return cantidadLineas;
    }

    //Obtiene las lineas de detalle registradas en la factura.
    public LineaDetalle[] getLineas() {
        return Arrays.copyOf(lineas, cantidadLineas);
    }

    //Asigna el metodo de pago con el que se cierra la factura.
    public void setMetodoPago(MetodoPago mp) {
        this.metodoPago = mp;
    }

    //Obtiene el metodo de pago asignado a la factura.
    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    // Obtiene el numero consecutivo de la factura.
    public int getNumero() {
        return numero;
    }

    //Genera el texto completo de la factura, listo para mostrarse en una vista.
    @Override
    public String toString() {
        String linea = "=".repeat(57);
        String separador = "-".repeat(57);
        StringBuilder sb = new StringBuilder();
        sb.append(linea).append("\n");
        sb.append("            CAFETERIA STARBUCKS  -  FACTURA").append("\n");
        sb.append(linea).append("\n");
        sb.append(String.format(" Factura N.:   %06d", numero)).append("\n");
        sb.append(String.format(" Fecha:        %s", fechaHora.format(FORMATO_FECHA))).append("\n");
        sb.append(String.format(" Atendido por: %s", atendidoPor)).append("\n");
        sb.append(separador).append("\n");
        sb.append(" COD  PRODUCTO          CANT    P. UNIT       SUBTOTAL").append("\n");
        sb.append(separador).append("\n");
        for (int i = 0; i < cantidadLineas; i++) {
            sb.append(lineas[i].toString()).append("\n");
        }
        sb.append(separador).append("\n");
        sb.append(String.format("%39s%15s", "SUBTOTAL:", FormatoUtil.moneda(calcularSubtotal()))).append("\n");
        sb.append(String.format("%39s%15s", "IVA (13%):", FormatoUtil.moneda(calcularImpuesto()))).append("\n");
        sb.append(String.format("%39s%15s", "TOTAL BRUTO:", FormatoUtil.moneda(calcularTotalBruto()))).append("\n");
        sb.append(String.format("%39s%15s", "DESCUENTO (5%):", FormatoUtil.moneda(calcularDescuento()))).append("\n");
        sb.append(separador).append("\n");
        sb.append(String.format("%39s%15s", "TOTAL A PAGAR:", FormatoUtil.moneda(calcularTotalPagar()))).append("\n");
        sb.append(linea).append("\n");
        sb.append(String.format("            Metodo de pago:  %s",
                metodoPago != null ? metodoPago.getEtiqueta() : "")).append("\n");
        sb.append("        Gracias por su compra. Vuelva pronto.").append("\n");
        sb.append(linea);
        return sb.toString();
    }




}
