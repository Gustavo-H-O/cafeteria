package com.utn.cafeteria.util;

import javax.swing.JOptionPane;

public final class Validador {

    private Validador() {
    }

    //Solicita repetidamente un numero entero hasta obtener un valor valido

    public static Integer leerEntero(String mensaje, String titulo, int min, int max) {
        while (true) {
            String texto = JOptionPane.showInputDialog(null, mensaje, titulo, JOptionPane.QUESTION_MESSAGE);
            if (texto == null) {
                return null;
            }
            if (texto.isBlank()) {
                mostrarError("Debe digitar un valor.");
                continue;
            }
            if (!esEnteroValido(texto.trim())) {
                mostrarError(mensajeRangoEntero(min, max));
                continue;
            }
            int valor = Integer.parseInt(texto.trim());
            if (valor < min || valor > max) {
                mostrarError(mensajeRangoEntero(min, max));
                continue;
            }
            return valor;
        }
    }

    //Solicita repetidamente un numero decimal hasta obtener un valor valido
    public static Double leerDecimal(String mensaje, String titulo, double min) {
        while (true) {
            String texto = JOptionPane.showInputDialog(null, mensaje, titulo, JOptionPane.QUESTION_MESSAGE);
            if (texto == null) {
                return null;
            }
            if (texto.isBlank()) {
                mostrarError(Mensajes.MONTO_NO_NUMERICO);
                continue;
            }
            String normalizado = texto.trim().replace(',', '.');
            if (!esDecimalValido(normalizado)) {
                mostrarError(Mensajes.MONTO_NO_NUMERICO);
                continue;
            }
            double valor = Double.parseDouble(normalizado);
            if (valor < min) {
                mostrarError("El monto debe ser mayor o igual a " + FormatoUtil.moneda(min) + ".");
                continue;
            }
            return valor;
        }
    }

    //Solicita repetidamente un texto no vacio y dentro del largo maximo permitido,
    public static boolean esEnteroValido(String texto) {
        try {
            Integer.parseInt(texto.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //Indica si un texto representa un numero decimal valido.
    public static boolean esDecimalValido(String texto) {
        try {
            Double.parseDouble(texto.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    private static String mensajeRangoEntero(int min, int max) {
        if (max == Integer.MAX_VALUE) {
            return "Debe digitar un número entero mayor o igual a " + min + ".";
        }
        return "Debe digitar un número entero entre " + min + " y " + max + ".";
    }

    private static void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, Mensajes.TITULO_ERROR, JOptionPane.ERROR_MESSAGE);
    }

}
