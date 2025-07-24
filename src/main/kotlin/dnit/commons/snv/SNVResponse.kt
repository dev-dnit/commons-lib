package dnit.commons.snv

class SNVResponse {
    val snv : String?
    val versao : String?
    
    val coincidencia : String?
    val uf : String?
    val br : String?

    val tipo : String?
    val latitude : Double?
    val longitude : Double?

    val km : Double?


    constructor(snv : String, versao : String?) {
        this.snv = snv
        this.versao = versao

        this.coincidencia = null
        this.uf = null
        this.br = null

        this.tipo = null
        this.latitude = null
        this.longitude = null
        this.km = null
    }


    constructor(
        snv : String?,
        versao : String?,
        coincidencia : String?,
        uf : String?,
        br : String?,
        tipo : String?,
        latitude : Double?,
        longitude : Double?,
        km : Double?
    ) {
        this.snv = snv
        this.versao = versao
        this.coincidencia = coincidencia
        this.uf = uf
        this.br = br
        this.tipo = tipo
        this.latitude = latitude
        this.longitude = longitude
        this.km = km
    }


    override fun toString() : String {
        return "{snv='" + snv +
                "', versao='" + versao +
                "', coincidencia='" + coincidencia +
                "', uf='" + uf +
                "', br='" + br +
                "', tipo='" + tipo +
                "', latitude=" + latitude +
                ", longitude=" + longitude +
                "}"
    }
}
