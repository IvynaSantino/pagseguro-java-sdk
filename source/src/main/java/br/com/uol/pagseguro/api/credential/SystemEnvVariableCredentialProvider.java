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
package br.com.uol.pagseguro.api.credential;

/**
 * This class is responsible for retrieving the credentials through the system environments
 * variable.
 *
 * @author PagSeguro Internet Ltda.
 */
public class SystemEnvVariableCredentialProvider implements CredentialProvider {


    SystemEnvVariableCredentialProvider() {
    }

    /**
     * Retrieve the credentials through the system environments variable.
     *
     * @return Credential
     */
    @Override
    public Credential getCredential() {
        getLogger().info("Lendo credenciais");
        final Credential credential;
        if (System.getenv("PSL_EMAIL") != null && System.getenv("PSL_TOKEN") != null) {
            credential = Credential.sellerCredential(System.getenv("PSL_EMAIL").trim(), System.getenv("PSL_TOKEN").trim());
        } else if (System.getenv("PSL_APP_ID") != null && System.getenv("PSL_APP_KEY") != null) {
            credential = Credential.applicationCredential(System.getenv("PSL_APP_ID").trim(), System.getenv("PSL_APP_KEY").trim());
        } else {
            throw new IllegalArgumentException("Seller credential and Application credential not found");
        }
        getLogger().info("Credenciais lidas");
        return credential;
    }
}
