package br.com.uol.pagseguro.api.transaction.search;

import br.com.uol.pagseguro.api.Endpoints;
import br.com.uol.pagseguro.api.PagSeguro;
import br.com.uol.pagseguro.api.common.domain.DataList;
import br.com.uol.pagseguro.api.exception.PagSeguroLibException;
import br.com.uol.pagseguro.api.http.HttpClient;
import br.com.uol.pagseguro.api.http.HttpMethod;
import br.com.uol.pagseguro.api.http.HttpRequestBody;
import br.com.uol.pagseguro.api.http.HttpResponse;
import br.com.uol.pagseguro.api.utils.CharSet;
import br.com.uol.pagseguro.api.utils.PagSeguroCommand;
import br.com.uol.pagseguro.api.utils.RequestMap;

import java.io.IOException;
import java.util.Map;

/**
 * Search transactions by date range
 *
 * @author PagSeguro Internet Ltda.
 * @see PagSeguroCommand
 * @see DataList
 * @see TransactionSummary
 */
class TransactionSearchByDateRange implements PagSeguroCommand<DataList<? extends TransactionSummary>> {

    private static final TransactionSearchV2MapConverter TRANSACTION_SEARCH_MC =
            new TransactionSearchV2MapConverter();

    private final TransactionSearch transactionSearch;

    /**
     * Constructor
     *
     * @param transactionSearch Interface for Transaction Search
     */
    TransactionSearchByDateRange(TransactionSearch transactionSearch) {
        this.transactionSearch = transactionSearch;
    }

    /**
     * Execute Search Transactions by date range
     *
     * @param pagseguro  Pagseguro instance
     * @param httpClient Http Client
     * @return Transactions List
     * @see DataList
     * @see PagSeguroCommand#execute(PagSeguro, HttpClient)
     * @see HttpClient#execute(HttpMethod, String, Map, HttpRequestBody)
     */
    @Override
    public DataList<? extends TransactionSummary> execute(PagSeguro pagseguro,
                                                          HttpClient httpClient) {
        getLogger().info("Iniciando busca de transacao por intervalo de data");
        getLogger().info("Convertendo valores");
        final RequestMap map = TRANSACTION_SEARCH_MC.convert(transactionSearch);
        getLogger().info("Valores convertidos");
        final HttpResponse response;
        try {
            getLogger().debug(String.format("Parametros: %s", map));
            response = httpClient.execute(HttpMethod.GET, String.format(Endpoints.TRANSACTION_SEARCH,
                    pagseguro.getHost(), map.toUrlEncode(CharSet.ENCODING_UTF)), null, null);
            getLogger().debug(String.format("Resposta: %s", response.toString()));
        } catch (IOException e) {
            getLogger().error("Erro ao executar busca de transacao por intervalo de data");
            throw new PagSeguroLibException(e);
        }
        getLogger().info("Parseando XML de resposta");
        DataList<? extends TransactionSummary> transactionsSummary = response.parseXMLContent(pagseguro,
                TransactionSearchResponseXML.class);
        getLogger().info("Parseamento finalizado");
        getLogger().info("Busca de transacao por intervalo de data finalizada");
        return transactionsSummary;
    }
}
