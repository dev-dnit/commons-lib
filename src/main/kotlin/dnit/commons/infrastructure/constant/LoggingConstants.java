package dnit.commons.infrastructure.constant;


/**
 * Constantes utilizadas para padronização de campos nos logs da aplicação.
 * Esta classe define chaves padronizadas para campos comuns de logging,
 * facilitando a correlação e busca de logs em ferramentas de monitoramento.
 */
public final class LoggingConstants {

    /**
     * Chave para identificador único de sessão nos logs.
     * Utilizado para correlacionar logs de uma mesma requisição ou operação.
     */
    public static final String SESSION_ID = "session-id";


    /**
     * Construtor privado para prevenir instanciação.
     */
    private LoggingConstants() {
    }

}