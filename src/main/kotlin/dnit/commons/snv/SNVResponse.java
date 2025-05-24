package dnit.commons.snv;

public final class SNVResponse {

    public final String snv;
    public final String versao;


    public SNVResponse(String snv, String versao) {
        this.snv = snv;
        this.versao = versao;
    }


    @Override
    public String toString() {
        return "{snv='" + snv + "', versao='" + versao + "'}";
    }

}
