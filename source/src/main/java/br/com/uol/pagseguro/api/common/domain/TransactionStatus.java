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

/**
 * Class for transaction status
 *
 * @author PagSeguro Internet Ltda.
 */
public enum TransactionStatus {

    WAITING_PAYMENT(1),
    IN_REVIEW(2),
    APPROVED(3),
    AVAILABLE(4),
    IN_DISPUTE(5),
    RETURNED(6),
    CANCELLED(7),
    UNRECOGNIZED(null);

    private Integer statusId;

    TransactionStatus(Integer statusId) {
        this.statusId = statusId;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public static TransactionStatus fromStatusId(Integer statusId) {
        for (TransactionStatus status : TransactionStatus.values()) {
            if (status.statusId != null && status.statusId == statusId) {
                return status;
            }
        }
        return UNRECOGNIZED;
    }
}
