package dnit.commons.model.internal


internal data class RotaSNV(
    val id : String? = null,
    val idTrecho : String? = null,
    val br : String,
    val uf : String,
    val sgTpTrecho : String,
    val km : Double,
    val lat : String? = null,
    val lng : String? = null,
    val versao : String? = null,
)
