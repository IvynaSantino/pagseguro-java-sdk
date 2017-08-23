package br.com.uol.pagseguro.api.transaction.search;

import br.com.uol.pagseguro.api.Endpoints;
import br.com.uol.pagseguro.api.PagSeguro;
import br.com.uol.pagseguro.api.exception.PagSeguroLibException;
import br.com.uol.pagseguro.api.http.HttpClient;
import br.com.uol.pagseguro.api.http.HttpMethod;
import br.com.uol.pagseguro.api.http.HttpRequestBody;
import br.com.uol.pagseguro.api.http.HttpResponse;
import br.com.uol.pagseguro.api.utils.PagSeguroCommand;

import java.io.IOException;
import java.util.Map;

/**
 * Search transaction by notification code
 *
 * @author PagSeguro Internet Ltda.
 * @see PagSeguroCommand
 * @see TransactionDetail
 */
class TransactionSearchByNotification implements PagSeguroCommand<TransactionDetail> {

    private final String notificationCode;

    /**
     * Constructor
     *
     * @param notificationCode Notification code
     */
    TransactionSearchByNotification(String notificationCode) {
        this.notificationCode = notificationCode;
    }

    /**
     * Execute Search Transaction by notification code
     *
     * @param pagseguro  Pagseguro instance
     * @param httpClient Http Client
     * @return Transaction Detail
     * @see PagSeguroCommand#execute(PagSeguro, HttpClient)
     * @see HttpClient#execute(HttpMethod, String, Map, HttpRequestBody)
     * @see TransactionDetail
     * @see PagSeguro
     */
    @Override
    public TransactionDetail execute(PagSeguro pagseguro, HttpClient httpClient) {
        getLogger().info("Iniciando busca de transacao por codigo de notificacao");
        final HttpResponse response;
        try {
            getLogger().debug(String.format("Parametros: notificationCode:%s", notificationCode));
            response = httpClient.execute(HttpMethod.GET,
                    String.format(Endpoints.TRANSACTION_SEARCH_BY_NOTIFICATION_CODE, pagseguro.getHost(),
                            notificationCode), null, null);
            getLogger().debug(String.format("Resposta: %s", response.toString()));
        } catch (IOException e) {
            getLogger().error("Erro ao executar busca de transacao por codigo de notificacao");
            throw new PagSeguroLibException(e);
        }
        getLogger().info("Parseando XML de resposta");
        TransactionDetail transaction = response.parseXMLContent(pagseguro, TransactionDetailXML.class);
        getLogger().info("Parseamento finalizado");
        getLogger().info("Busca de transacao por codigo de notificacao finalizada");
        return transaction;
    }
}
