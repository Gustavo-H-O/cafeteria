package com.utn.cafeteria.util;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public final class FormatoUtil {

    private static final DecimalFormat FORMATO = new DecimalFormat("#,##0.00", DecimalFormatSymbols.getInstance(Locale.US));

    private FormatoUtil(){

}
    //Formatea un monto con separador de miles y dos decimales, sin simbolo de moneda.
    public static String moneda(double monto) {
        return FORMATO.format(monto);
    }

    public static String alinearDerecha(String texto, int ancho) {
        return String.format("%" + ancho + "s", texto);
    }

    public static String alinearIzquierda(String texto, int ancho) {
        return String.format("%-" + ancho + "s", texto);
    }

    public static JScrollPane enPanelMonoespaciado(String contenido, int filas, int columnas) {
        JTextArea area = new JTextArea(contenido, filas, columnas);
        area.setFont(new Font("Monospaced", Font.PLAIN, 12));
        area.setEditable(false);
        return new JScrollPane(area);
    }


}
