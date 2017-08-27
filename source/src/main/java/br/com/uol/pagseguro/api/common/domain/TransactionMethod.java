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

package br.com.uol.pagseguro.api.common.domain;

import br.com.uol.pagseguro.api.utils.StringUtils;

/**
 * Class for transaction method
 *
 * @author PagSeguro Internet Ltda.
 */
public enum TransactionMethod {

    BANK_SLIP("boleto"),
    CREDIT_CARD("creditCard"),
    ONLINE_DEBIT("eft"),
    OTHER(null);

    private final String name;

    TransactionMethod(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static TransactionMethod fromName(String name) {
        if (StringUtils.isNotEmpty(name)) {
            for (TransactionMethod paymentMethod : TransactionMethod.values()) {
                if (name.equalsIgnoreCase(paymentMethod.name)) {
                    return paymentMethod;
                }
            }
        }
        return OTHER;
    }
}