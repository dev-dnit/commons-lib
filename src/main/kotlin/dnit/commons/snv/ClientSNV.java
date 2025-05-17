package dnit.commons.snv;

import dnit.commons.model.UF;

import java.util.List;


/**
 * Classe cliente para acessar os SNVs disponíveis.
 */
public final class ClientSNV {

    private ClientSNV() { }


    /**
     * Obtém os SNVs disponíveis para a UF, BR, latitude e longitude informados.
     */
    public static List<SNVResponse> obtemSNVs(
            final UF uf,
            final String br,
            final Double latitude,
            final Double longitude
    ) {
        return obtemSNVs(uf.sigla, br, latitude, longitude);
    }



    /**
     * Obtém os SNVs disponíveis para a UF, BR, latitude e longitude informados.
     */
    public static List<SNVResponse> obtemSNVs(
        final String uf,
        final String br,
        final Double latitude,
        final Double longitude
    ) {
        return ClientSnvImplementation.obtemSNVs(uf, br, latitude, longitude);
    }

}
