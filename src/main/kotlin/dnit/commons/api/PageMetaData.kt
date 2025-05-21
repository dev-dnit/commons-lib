package dnit.commons.api


/**
 * Classe para armazenar metadados de paginação
 *
 * @property count Número total de itens na página
 * @property total Número total de itens disponíveis
 * @property pages Número total de páginas disponíveis
 * @property currentPage Página atual
 */
data class PageMetadata(

    var count : Int = 0,
    var total : Int = 0,
    var pages : Int = 0,
    var currentPage : Int = 0,

) {

    val hasNextPage : Boolean
        get() = currentPage < total

    val hasPreviousPage : Boolean
        get() = currentPage > 0

}