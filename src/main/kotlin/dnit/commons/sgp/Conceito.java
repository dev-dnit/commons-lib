package dnit.commons.sgp;

import dnit.commons.exception.CommonException;


/**
 * Enum com os conceitos de avaliação.
 * Utilizados para avaliar IRI, IGG e ICS
 */
public enum Conceito {

    OTIMO(5, "Ótimo"),
    BOM(4, "Bom"),
    REGULAR(3, "Regular"),
    RUIM(2, "Ruim"),
    PESSIMO(1, "Péssimo");

    public final int nota;
    public final String descricao;


    Conceito(int nota, String descricao) {
        this.nota = nota;
        this.descricao = descricao;
    }



    /**
     * Retorna o conceito correspondente à nota fornecida.
     */
    public static Conceito fromNota(Integer nota) {
        if (nota == null) {
            throw new CommonException("Nota não pode ser nula");
        }

        for (Conceito conceito : values()) {
            if (conceito.nota == nota) {
                return conceito;
            }
        }
        throw new CommonException("Nota inválida: " + nota);
    }



    /**
     * Retorna o conceito simplificado correspondente.
     * @see ConceitoSimplificado
     */
    public ConceitoSimplificado toConceitoSimplificado() {
        return switch (this) {
            case OTIMO, BOM -> ConceitoSimplificado.BOM;
            case REGULAR -> ConceitoSimplificado.REGULAR;
            case RUIM, PESSIMO -> ConceitoSimplificado.RUIM;
        };
    }

}
