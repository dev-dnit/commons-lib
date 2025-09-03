package dnit.commons.model;

import dnit.commons.exception.CommonException;
import dnit.commons.utils.StringUtils;

import java.util.Set;

import static dnit.commons.utils.StringUtils.unnacent;


public final class SNV {

    private static final Set<Character> tiposEixo = Set.of('A', 'B', 'C', 'N', 'U', 'V');

    private SNV() { }


    /**
     * Valida se o snv fornecido é valido
     * BR + EIXO + UF + 4digitos
     * XXX + U + XX + XXXX
     */
    public static boolean isValidSNV(String snv) {
        // Check exact length (10 characters: XXX + U + XX + XXXX)
        if (snv == null || snv.length() != 10) {
            return false;
        }

        // Extract parts
        String rodovia = snv.substring(0, 3);
        String tipoEixo = snv.substring(3, 4);
        String uf = snv.substring(4, 6);
        String digitosTrecho = snv.substring(6, 10);

        // Validate rodovia (3 digits)
        if (!BR.isValidBR(rodovia)) {
            return false;
        }

        // Validate tipo de eixo (1 uppercase letter)
        if (!tiposEixo.contains(tipoEixo.charAt(0))) {
            return false;
        }

        // Validate UF (2 letters)
        if (!UF.isUfValida(uf)) {
            return false;
        }

        // Validate digitosTrecho (4 digits)
        if (!digitosTrecho.chars().allMatch(Character::isDigit)) {
            return false;
        }

        return true;
    }



    /**
     * Sanitiza uma string, extraindo um snv
     */
    public static String sanitizeSNV(String input) {
        if (StringUtils.isNullOrEmpty(input)) {
            throw new CommonException("Não foi possível sanitizar o SNV: " + input);
        }

        try {
            String snv = unnacent(input).toUpperCase().replaceAll("[^A-Z0-9]", "");

            if (isValidSNV(snv)) {
                return snv;
            }

        } catch (Exception ignored) {
            // Ignorado, exceção tratada abaixo
        }

        throw new CommonException("Não foi possível sanitizar o SNV: " + input);
    }


}
