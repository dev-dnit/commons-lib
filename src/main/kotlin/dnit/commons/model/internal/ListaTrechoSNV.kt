package dnit.commons.model.internal


internal data class ListaTrechoSNV(
    val rota: String,
    val trechos: List<MiniTrechoSNV>
)

internal data class MiniTrechoSNV(
    val trecho : String,
    val extensao : Double,
    val coincidencia: String?
)