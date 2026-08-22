package com.utn.cafeteria.servicio;

import com.utn.cafeteria.modelo.Caja;
import com.utn.cafeteria.modelo.ResultadoArqueo;
import com.utn.cafeteria.modelo.ResultadoArqueo.Estado;

public class ServicioCierreCaja {

    private final Caja caja;

    public ServicioCierreCaja (Caja caja){ this.caja = caja;}

    //Realiza el arqueo de caja comparando los montos declarados contra loa registrados
    public  ResultadoArqueo arquear(double efectivoDeclarado, double tarjetaDeclarado, String sutorizadiPor){
        Estado estadoEfectivo = traducir(caja.compararEfectivo(efectivoDeclarado));
        Estado estadoTarjeta = traducir(caja.compararTarjeta(tarjetaDeclarado));

        ResultadoArqueo resultado =
                new ResultadoArqueo(estadoEfectivo,estadoTarjeta, caja,getFacturasEmitidas(), autorizadoPor);
        caja.marcarCerrada();
        return resultado;

        private Estado traducir(int comparacion){
            if (comparacion == 0){
                return Estado.CUADRE_EXACTO;
            }
            return comparacion < 0 ? Estado.FALTANTE : Estado.SOBRANTE;
        }





    }

















}