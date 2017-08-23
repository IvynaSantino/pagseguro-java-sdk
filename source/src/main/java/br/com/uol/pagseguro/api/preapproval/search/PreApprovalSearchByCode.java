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
import br.com.uol.pagseguro.api.exception.PagSeguroLibException;
import br.com.uol.pagseguro.api.http.HttpClient;
import br.com.uol.pagseguro.api.http.HttpMethod;
import br.com.uol.pagseguro.api.http.HttpRequestBody;
import br.com.uol.pagseguro.api.http.HttpResponse;
import br.com.uol.pagseguro.api.utils.PagSeguroCommand;

import java.io.IOException;
import java.util.Map;

/**
 * Search pre approval (signature) by code
 *
 * @author PagSeguro Internet Ltda.
 * @see PagSeguroCommand
 * @see PreApprovalDetail
 */
class PreApprovalSearchByCode implements PagSeguroCommand<PreApprovalDetail> {

    private final String code;

    /**
     * Constructor
     *
     * @param code Pre Approval code
     */
    PreApprovalSearchByCode(String code) {
        this.code = code;
    }

    /**
     * Execute search by code
     *
     * @param pagseguro  Pagseguro
     * @param httpClient Http Client
     * @return Pre Approval Detail Response
     * @see PreApprovalDetail
     * @see PagSeguro
     * @see HttpClient#execute(HttpMethod, String, Map, HttpRequestBody)
     */
    @Override
    public PreApprovalDetail execute(PagSeguro pagseguro, HttpClient httpClient) {
        getLogger().info("Iniciando busca assinatura por codigo");
        final HttpResponse response;
        try {
            getLogger().debug(String.format("Parametros: preApprovalCode:%s", code));
            response = httpClient.execute(HttpMethod.GET,
                    String.format(Endpoints.PRE_APPROVAL_SEARCH_BY_CODE, pagseguro.getHost(), code), null,
                    null);
            getLogger().debug(String.format("Resposta: %s", response.toString()));
        } catch (IOException e) {
            getLogger().error("Erro ao executar busca assinatura por codigo");
            throw new PagSeguroLibException(e);
        }
        getLogger().info("Parseando XML de resposta");
        PreApprovalDetail preApproval = response.parseXMLContent(pagseguro, PreApprovalDetailXML.class);
        getLogger().info("Parseamento finalizado");
        getLogger().info("Busca assinatura por codigo finalizada");
        return preApproval;
    }
}
