package dnit.commons.model;

import dnit.commons.utils.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Enum com as regiões do Brasil.
 */
public enum Regiao {

    NORTE("Norte", "N"),
    NORDESTE("Nordeste", "NE"),
    CENTRO_OESTE("Centro-Oeste", "CO"),
    SUDESTE("Sudeste", "SE"),
    SUL("Sul", "S");

    public final String nomeCompleto;
    public final String sigla;


    Regiao(String nomeCompleto, String sigla) {
        this.nomeCompleto = nomeCompleto;
        this.sigla = sigla;
    }


    public static boolean isRegiaoValida(String regiao) {
        if (StringUtils.isNullOrEmpty(regiao)) {
            return false;
        }
        
        return Arrays.stream(Regiao.values())
                     .anyMatch(r -> r.nomeCompleto.equals(regiao));
    }



    /**
     * Retorna a lista de UFs que pertencem a esta região.
     */
    public List<UF> ufsDaRegiao() {
        return Arrays.stream(UF.values())
                      .filter(uf -> uf.regiao == this)
                      .toList();
    }

}
