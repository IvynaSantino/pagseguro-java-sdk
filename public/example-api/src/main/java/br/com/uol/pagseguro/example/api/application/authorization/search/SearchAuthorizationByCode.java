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
package br.com.uol.pagseguro.example.api.application.authorization.search;

import br.com.uol.pagseguro.api.PagSeguro;
import br.com.uol.pagseguro.api.PagSeguroEnv;
import br.com.uol.pagseguro.api.application.authorization.search.AuthorizationDetail;
import br.com.uol.pagseguro.api.credential.Credential;

/**
 * @author PagSeguro Internet Ltda.
 */
public class SearchAuthorizationByCode {

  public static void main(String[] args) {
    String appId = "your_app_id";
    String appKey = "your_app_key";

    try {

      final PagSeguro pagSeguro = PagSeguro.instance(Credential.applicationCredential(appId,
          appKey), PagSeguroEnv.SANDBOX);

      AuthorizationDetail authorizationDetail = pagSeguro.authorizations().search()
          .byCode("0F9DE39A0CF949538606EE7217620ADB");

      System.out.print(authorizationDetail);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
