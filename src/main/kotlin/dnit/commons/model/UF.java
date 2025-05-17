package dnit.commons.model;

import dnit.commons.exception.CommonException;
import dnit.commons.utils.StringUtils;

import static dnit.commons.model.Regiao.CENTRO_OESTE;
import static dnit.commons.model.Regiao.NORDESTE;
import static dnit.commons.model.Regiao.NORTE;
import static dnit.commons.model.Regiao.SUDESTE;
import static dnit.commons.model.Regiao.SUL;


/**
 * Enum com as UFs - estados brasileiros.
 */
public enum UF {

    RO(11, NORTE, "Rondônia"),
    AC(12, NORTE, "Acre"),
    AM(13, NORTE, "Amazonas"),
    RR(14, NORTE, "Roraima"),
    PA(15, NORTE, "Pará"),
    AP(16, NORTE, "Amapá"),
    TO(17, NORTE, "Tocantins"),
    MA(21, NORDESTE, "Maranhão"),
    PI(22, NORDESTE, "Piauí"),
    CE(23, NORDESTE, "Ceará"),
    RN(24, NORDESTE, "Rio Grande do Norte"),
    PB(25, NORDESTE, "Paraíba"),
    PE(26, NORDESTE, "Pernambuco"),
    AL(27, NORDESTE, "Alagoas"),
    SE(28, NORDESTE, "Sergipe"),
    BA(29, NORDESTE, "Bahia"),
    MG(31, SUDESTE, "Minas Gerais"),
    ES(32, SUDESTE, "Espírito Santo"),
    RJ(33, SUDESTE, "Rio de Janeiro"),
    SP(35, SUDESTE, "São Paulo"),
    PR(41, SUL, "Paraná"),
    SC(42, SUL, "Santa Catarina"),
    RS(43, SUL, "Rio Grande do Sul"),
    MS(50, CENTRO_OESTE, "Mato Grosso do Sul"),
    MT(51, CENTRO_OESTE, "Mato Grosso"),
    GO(52, CENTRO_OESTE, "Goiás"),
    DF(53, CENTRO_OESTE, "Distrito Federal");

    public final int codigoIbge;
    public final Regiao regiao;
    public final String sigla;
    public final String nomeCompleto;


    UF(int codigoIbge, Regiao regiao, String nome) {
        this.codigoIbge = codigoIbge;
        this.regiao = regiao;
        this.sigla = name();
        this.nomeCompleto = nome;
    }



    public static UF ufFromCodigoIbge(Integer codigoIbge) {
        if (codigoIbge == null) {
            throw new CommonException("Código IBGE não pode ser nulo");
        }

        for (UF uf : values()) {
            if (uf.codigoIbge == codigoIbge) {
                return uf;
            }
        }

        throw new CommonException("Código IBGE inválido: " + codigoIbge);
    }



    public static UF ufFromSigla(String sigla) {
        if (StringUtils.isNullOrEmpty(sigla)) {
            throw new CommonException("Sigla não pode ser nula ou vazia");
        }

        for (UF uf : values()) {
            if (uf.sigla.equals(sigla)) {
                return uf;
            }
        }

        throw new CommonException("Sigla inválida: " + sigla);
    }



    public static boolean isUfValida(String sigla) {
        try {
            ufFromSigla(sigla);
            return true;

        } catch (CommonException e) {
            return false;
        }
    }

}