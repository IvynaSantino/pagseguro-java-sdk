/*
 * 2007-2016 [PagSeguro Internet Ltda.]
 * 
 * NOTICE OF LICENSE
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Copyright: 2007-2016 PagSeguro Internet Ltda.
 * Licence: http://www.apache.org/licenses/LICENSE-2.0
 */

package br.com.uol.pagseguro.api.preapproval.search;

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
 * Search pre approval by date range
 *
 * @author PagSeguro Internet Ltda.
 * @see PagSeguroCommand
 * @see PreApprovalSummary
 */
class PreApprovalSearchByDateRange implements PagSeguroCommand<DataList<? extends PreApprovalSummary>> {

    private static final PreApprovalSearchV2MapConverter PRE_APPROVAL_SEARCH_MC =
            new PreApprovalSearchV2MapConverter();

    private final PreApprovalSearch preApprovalSearch;

    /**
     * Constructor
     *
     * @param preApprovalSearch Interface for Pre Approval Search
     */
    PreApprovalSearchByDateRange(PreApprovalSearch preApprovalSearch) {
        this.preApprovalSearch = preApprovalSearch;
    }

    /**
     * Execute search by date range
     *
     * @param pagseguro  Pagseguro
     * @param httpClient Http client
     * @return Pre Approval list
     * @see PreApprovalSummary
     * @see DataList
     * @see PagSeguro
     * @see HttpClient#execute(HttpMethod, String, Map, HttpRequestBody)
     */
    @Override
    public DataList<? extends PreApprovalSummary> execute(PagSeguro pagseguro,
                                                          HttpClient httpClient) {
        getLogger().info("Iniciando busca assinatura por intervalo de data");
        getLogger().info("Convertendo valores");
        final RequestMap map = PRE_APPROVAL_SEARCH_MC.convert(preApprovalSearch);
        getLogger().info("Valores convertidos");
        final HttpResponse response;
        try {
            getLogger().debug(String.format("Parametros: %s", map));
            response = httpClient.execute(HttpMethod.GET, String.format(Endpoints.PRE_APPROVAL_SEARCH,
                    pagseguro.getHost(), map.toUrlEncode(CharSet.ENCODING_UTF)), null, null);
            getLogger().debug(String.format("Resposta: %s", response.toString()));
        } catch (IOException e) {
            getLogger().error("Erro ao executar busca assinatura por intervalo de data");
            throw new PagSeguroLibException(e);
        }
        getLogger().info("Parseando XML de resposta");
        DataList<? extends PreApprovalSummary> preApprovalsSummary = response.parseXMLContent(pagseguro,
                PreApprovalSearchResponseXML.class);
        getLogger().info("Parseamento finalizado");
        getLogger().info("Busca assinatura por intervalo de data finalizada");
        return preApprovalsSummary;
    }
}
