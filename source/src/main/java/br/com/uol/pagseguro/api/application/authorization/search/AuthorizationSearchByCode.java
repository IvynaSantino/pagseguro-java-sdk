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
package br.com.uol.pagseguro.api.application.authorization.search;

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
 * Search authorization by code.
 *
 * @author PagSeguro Internet Ltda.
 */
class AuthorizationSearchByCode implements PagSeguroCommand<AuthorizationDetail> {

    private final String code;

    /**
     * @param code The authorization code of authorization that you want to search
     */
    AuthorizationSearchByCode(String code) {
        this.code = code;
    }

    /**
     * Execute Search by Code
     *
     * @param pagseguro  The instance of Pagseguro. Used on unmarshal and used to get current host.
     * @param httpClient Http client instance. Used to execute the search.
     * @return Authorization detail
     * @see AuthorizationDetail
     * @see PagSeguro
     * @see HttpClient#execute(HttpMethod, String, Map, HttpRequestBody)
     */
    @Override
    public AuthorizationDetail execute(PagSeguro pagseguro, HttpClient httpClient) {
        getLogger().info("Iniciando busca de autorizacao por codigo");
        final HttpResponse response;
        try {
            getLogger().debug(String.format("Parametros: authorizationCode:%s", code));
            response = httpClient.execute(HttpMethod.GET, String.format(Endpoints.AUTHORIZATION_SEARCH_BY_CODE, pagseguro.getHost(), code), null, null);
            getLogger().debug(String.format("Resposta: %s", response.toString()));
        } catch (IOException e) {
            getLogger().error("Erro ao executar busca de autorizacao por codigo");
            throw new PagSeguroLibException(e);
        }
        getLogger().info("Parseando XML de resposta");
        AuthorizationDetail authorizationDetail = response.parseXMLContent(pagseguro, AuthorizationDetailXML.class);
        getLogger().info("Parseamento finalizado");
        getLogger().info("Busca de autorizacao por codigo finalizada");
        return authorizationDetail;
    }
}
