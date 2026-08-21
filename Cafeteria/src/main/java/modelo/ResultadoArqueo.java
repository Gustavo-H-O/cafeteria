package com.utn.cafeteria.modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ResultadoArqueo {

    public enum Estado {

        CUADRE_EXACTO,
        FALTANTE,
        SOBRANTE
    }

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final Estado estadoEfectivo;
    private final Estado estadoTarjeta;
    private final int facturasEmitidas;
    private final String autorizadoPor;
    private final LocalDateTime fechaHora;

    //Crea el resultado del arqueo con la fecha y hora en que se realiza
    public ResultadoArqueo(Estado estadoEfectivo, Estado estadoTarjeta, int facturasEmitidas, String autorizadoPor) {
        this.estadoEfectivo = estadoEfectivo;
        this.estadoTarjeta = estadoTarjeta;
        this.facturasEmitidas = facturasEmitidas;
        this.autorizadoPor = autorizadoPor;
        this.fechaHora = LocalDateTime.now();
    }

    //Indica si tanto el efectivo como la tarjeta cuadraron exactamente.
    public boolean esCuadreExacto() {
        return estadoEfectivo == Estado.CUADRE_EXACTO && estadoTarjeta == Estado.CUADRE_EXACTO;
    }

    //Construye el texto del cierre de caja.

    public String describir() {
        String linea = "=".repeat(57);
        String separador = "-".repeat(57);
        StringBuilder sb = new StringBuilder();
        sb.append(linea).append("\n");
        sb.append("                  CIERRE DE CAJA").append("\n");
        sb.append(linea).append("\n");
        sb.append(String.format(" Fecha del cierre:  %s", fechaHora.format(FORMATO_FECHA))).append("\n");
        sb.append(String.format(" Autorizado por:    %s", autorizadoPor)).append("\n");
        sb.append(String.format(" Facturas emitidas: %d", facturasEmitidas)).append("\n");
        sb.append(separador).append("\n");
        sb.append(String.format(" EFECTIVO:  %s", textoEstado(estadoEfectivo))).append("\n");
        sb.append(String.format(" TARJETA :  %s", textoEstado(estadoTarjeta))).append("\n");
        sb.append(separador).append("\n");
        sb.append(" El sistema no revela los montos registrados en caja.").append("\n");
        sb.append(linea);
        return sb.toString();
    }

    private String textoEstado(Estado estado) {
        return switch (estado) {
            case CUADRE_EXACTO -> "Cuadre exacto";
            case FALTANTE -> "Faltante";
            case SOBRANTE -> "Sobrante";
        };
    }
}


