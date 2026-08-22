package com.utn.cafeteria.modelo;

public class Caja {

    private static final double TOLERANCIA = 0.01;

    private double totalEfectivo;
    private double totalTarjeta;
    private int facturasEmitidas;
    private boolean cerrada;

    // Registra el total pagado de una factura ya cerrada en el rubro que corresponda segun su metodo de pago.

    public void registarVenta (Factura f){
        if (f.getMetodoPago() == MetodoPago.EFECTIVO){
            totalEfectivo += f.calcularTotalPagar();
        }else {
            totalTarjeta += f.calcularTotalPagar();
        }
        facturasEmitidas++;
    }

    //Compara el efectivo declarado por la persona cajera contra el efectivo registrado.
    public int compararEfectivo(double declarado){return comparar(declarado, totalEfectivo);}
    //Compara el monto de tarjeta declarado por la persona cajera contra el registrado.
    public int compararTarjeta(double declarado){return comparar(declarado, totalTarjeta);}

    private int comparar(double declarado, double registrado){
        if(Math.abs(declarado-registrado <= TOLERANCIA)){
            return 0;
        }
        return declarado < registrado ? -1 : 1;

        // Marca la caja como cerrada impidiendo nuevas ventas
        public void marcarCerrada(){ cerrada = true; }

        //Indica si la caja ya fue cerrada
        public boolean estaCerrada(){return cerrada;}
        //Obtiene la cantidad de facturas emitidas durante la sesion.
        public int getFacturasEmitidas(){return facturasEmitidas; }


    }









}