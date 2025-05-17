package dnit.commons.sgp;

import dnit.commons.exception.CommonException;


/**
 * Classe utilitária para cálculos do SGP
 */
public final class CalculoSGP {

    private CalculoSGP() { }


    /**
     * Converte um valor de IRI para PSI.
     */
    public static Double convertIRItoPSI(final Double iri) {
        if (iri == null || iri < 0) {
            throw new CommonException("IRI não pode ser nulo");
        }

        return Math.max(0, 5 * Math.exp(-(iri * 13.0) / 71.5));
    }



    /**
     * Converte um valor de PSI de volta para IRI
     */
    public static Double convertPSItoIRI(final Double psi) {
        if (psi == null || psi < 0) {
            throw new CommonException("PSI não pode ser nulo");
        }

        return -Math.log(psi / 5.0) * 71.5 / 13.0;
    }



    /**
     * Converte um valor de IGG para SCI.
     */
    public static Double convertIGGtoSci(final Double igg) {
        if (igg == null || igg < 0) {
            throw new CommonException("IGG não pode ser nulo");
        }

        return Math.max(0, (309.22 - 0.616 * igg) / (61.844 + igg));
    }



    /**
     * Converte um valor de SCI de volta para IGG
     */
    public static Double convertSCItoIGG(final Double sci) {
        if (sci == null || sci < 0) {
            throw new CommonException("SCI não pode ser nulo");
        }
        return (309.22 - 61.844 * sci) / (sci + 0.616);
    }



    /**
     * Obtém conceito ICS a partir do valor ICS
     */
    public static Conceito getConceitoICS(final Double valorICS) {
        if (valorICS == null || valorICS <= 0) {
            throw new CommonException("ICS não pode ser nulo ou negativo");
        }

        if (valorICS <= 1) {
            return Conceito.PESSIMO;
        }

        if (valorICS <= 2) {
            return Conceito.RUIM;
        }

        if (valorICS <= 3) {
            return Conceito.REGULAR;
        }

        if (valorICS <= 4) {
            return Conceito.BOM;
        }

        if (valorICS <= 5 + 0.01) { // Delta para evitar erro de arredondamento
            return Conceito.OTIMO;
        }

        throw new CommonException("Nota ICS não pode ser maior que 5");
    }



    /**
     * Obtém nota para cálculo do Conceito ICS a partir do valor de IGG
     */
    public static Conceito getConceitoIGG(final Double valorIGG) {
        if (valorIGG == null || valorIGG < 0) {
            throw new CommonException("IGG não pode ser nulo ou negativo");
        }

        if (valorIGG > 160) {
            return Conceito.PESSIMO;
        }
        if (valorIGG > 80) {
            return Conceito.RUIM;
        }
        if (valorIGG > 40) {
            return Conceito.REGULAR;
        }
        if (valorIGG > 20) {
            return Conceito.BOM;
        }

        return Conceito.OTIMO;
    }



    /**
     * Obtém nota para cálculo do Conceito ICS a partir do valor de IRI
     * Conceito
     */
    public static Conceito getConceitoIRI(final Double valorIRI) {
        if (valorIRI == null || valorIRI < 0) {
            throw new CommonException("IRI não pode ser nulo ou negativo");
        }

        if (valorIRI > 6) {
            return Conceito.PESSIMO;
        }
        if (valorIRI > 4.5) {
            return Conceito.RUIM;
        }
        if (valorIRI > 3.5) {
            return Conceito.REGULAR;
        }
        if (valorIRI > 2.5) {
            return Conceito.BOM;
        }

        return Conceito.OTIMO;
    }

}
