package dnit.commons.model;

import dnit.commons.exception.CommonException;
import dnit.commons.utils.StringUtils;


public final class BR {

    private BR() { }


    /**
     * Valida se a string fornecida representa uma Sigla Rodoviária - BR válida.
     *
     * @param br A string a ser validada.
     * @return true se for um BR válido, false caso contrário.
     */
    public static boolean isValidBR(String br) {
        if (br == null || !br.matches("\\d{3}")) {
            return false;
        }

        try {
            int value = Integer.parseInt(br);
            return value >= 10 && value <= 499;

        } catch (NumberFormatException e) {
            return false;
        }
    }



    /**
     * Sanitiza uma string, extraindo apenas números e retornando no formata BR válido (3 dígitos).
     */
    public static String sanitizeBr(String input) {
        if (StringUtils.isNullOrEmpty(input)) {
            throw new CommonException("Não foi possível sanitizar a BR: " + input);
        }

        // Remove tudo que não for número
        String digitsOnly = input.replaceAll("\\D", "");

        try {
            int value = Integer.parseInt(digitsOnly);
            String formatted = String.format("%03d", value);

            if (isValidBR(formatted)) {
                return formatted;
            }
        } catch (NumberFormatException ignored) {
            // Ignorado, exceção tratada abaixo
        }

        throw new CommonException("Não foi possível sanitizar a BR: " + input);
    }



    /**
     * Sanitiza um inteiro, convertendo diretamente para o formato BR válido (com 3 dígitos).
     */
    public static String sanitizeBr(Integer input) {
        if (input == null) {
            throw new CommonException("Não foi possível sanitizar a BR: null");
        }

        // Formata como 3 dígitos
        String formatted = String.format("%03d", input);

        if (isValidBR(formatted)) {
            return formatted;
        }

        throw new CommonException("Não foi possível sanitizar a BR: " + input);
    }

}
