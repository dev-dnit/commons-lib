package dnit.commons.infrastructure.session;

import dnit.commons.infrastructure.constant.LoggingConstants;
import dnit.commons.utils.NanoIdUtils;
import dnit.commons.utils.TimerUtils;
import org.jboss.logging.MDC;

import java.time.Instant;

/**
 * Componente de escopo de sessão responsável por armazenar informações de contexto
 * associadas a uma requisição.
 * Pode ser utilizado para controle de tempo de execução, rastreamento de sessão,
 * correlação de logs e outras funcionalidades relacionadas ao ciclo de vida da requisição.
 */
public class ContextSession {


    /**
     * Identificador único da sessão.
     * Gerado automaticamente no início da sessão para correlação e rastreamento.
     */
    private String sessionId;



    /**
     * Instante de início da sessão/requisição.
     * Inicializado automaticamente ao criar o contexto.
     */
    private Instant startTime;



    /**
     * Instante de término da sessão/requisição.
     * Pode ser definido automaticamente ao consultar, caso ainda não esteja definido.
     */
    private Instant endTime;



    public ContextSession() {
        this (NanoIdUtils.randomNanoId(10));
    }



    public ContextSession(String sessionId) {
        this.sessionId = sessionId;
        this.startTime = null;
        this.endTime = null;
    }


    /**
     * Register the sessionId in the MDC logger
     */
    public ContextSession registerInMDC() {
        MDC.put(LoggingConstants.SESSION_ID, sessionId);
        return this;
    }



    /**
     * Inicia o timer da sessão definindo o tempo de início como o momento atual.
     * @return esta instância para permitir method chaining
     */
    public ContextSession startSessionTime() {
        this.startTime = Instant.now();
        return this;
    }



    /**
     * Finaliza o timer da sessão definindo o tempo de término como o momento atual.
     * @return esta instância para permitir method chaining
     */
    public ContextSession finishSessionTime() {
        this.endTime = Instant.now();
        return this;
    }



    /**
     * Retorna a duração da sessão em formato legível.
     * @return a duração da sessão formatada como string
     */
    public String sessionDurationTime() {
        return TimerUtils.duracaoEntreDoisInstants(startTime, endTime);
    }


    public String getSessionId() {
        return sessionId;
    }


    public String getStartTime() {
        return startTime.toString();
    }



    public String getEndTime() {
        return endTime.toString();
    }


}