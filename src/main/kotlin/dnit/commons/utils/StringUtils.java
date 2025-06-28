package dnit.commons.utils;

import java.text.Normalizer;
import java.util.regex.Pattern;


public final class StringUtils {

    private StringUtils() { }


    /**
     * Verifica se a string é nula ou vazia
     */
    public static boolean hasContent(String s) {
        return !isNullOrEmpty(s);
    }



    /**
     * Verifica se a string é nula ou vazia
     */
    public static boolean isNullOrEmpty(String s) {
        return s == null || s.isBlank();
    }



    /**
     * Função para remover todos os acentos de uma String
     */
    public static String unnacent(final String string) {
        if (isNullOrEmpty(string)) {
            return string;
        }

        // Normalize the string to NFD (Normalization Form D)
        String normalized = Normalizer.normalize(string, Normalizer.Form.NFD);

        // Remove all diacritical marks
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");

        return pattern.matcher(normalized).replaceAll("");
    }



    /**
     * Remove todas as letras (A-Z, a-z) da string de entrada.
     */
    public static String removeLetras(String input) {
        if (isNullOrEmpty(input)) {
            return input;
        }

        return input.replaceAll("[A-Za-z]", "");
    }



    /**
     * Remove todos os dígitos (0-9) da string de entrada.
     */
    public static String removeDigitos(String input) {
        if (isNullOrEmpty(input)) {
            return input;
        }

        return input.replaceAll("\\d", "");
    }



    /**
     * Remove todos os caracteres que não são letras ou dígitos.
     */
    public static String mantemApenasLetrasEDigitos(String input) {
        if (isNullOrEmpty(input)) {
            return input;
        }

        return input.replaceAll("[^A-Za-z0-9]", "");
    }



    /**
     * Capitaliza a primeira letra não espaco da string.
     * Exemplo: "  deparTaMento de esTraDas" -> "  Departamento de estradas"
     */
    public static String capitalizeFirst(String str) {
        if (isNullOrEmpty(str)) {
            return str;
        }

        int i = 0;
        while (i < str.length() && Character.isWhitespace(str.charAt(i))) {
            i++;
        }

        if (i == str.length()) {
            return str; // Only whitespace
        }

        return str.substring(0, i) +
                Character.toUpperCase(str.charAt(i)) +
                str.substring(i + 1).toLowerCase();
    }



    /**
     * Capitaliza a primeira letra de cada palavra, mantendo espaços originais.
     * Exemplo: "  departamento de estradas  " -> "  Departamento De Estradas  "
     */
    public static String capitalizeWords(String str) {
        if (isNullOrEmpty(str)) {
            return str;
        }

        StringBuilder result = new StringBuilder();
        boolean capitalizeNext = true;

        for (char c : str.toCharArray()) {
            if (Character.isWhitespace(c)) {
                result.append(c);
                capitalizeNext = true;
            } else {
                if (capitalizeNext) {
                    result.append(Character.toUpperCase(c));
                    capitalizeNext = false;
                } else {
                    result.append(Character.toLowerCase(c));
                }
            }
        }

        return result.toString();
    }

}
