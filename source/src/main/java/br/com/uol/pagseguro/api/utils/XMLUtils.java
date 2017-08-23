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
package br.com.uol.pagseguro.api.utils;

import br.com.uol.pagseguro.api.PagSeguro;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Unmarshaller.Listener;
import java.io.StringReader;

/**
 * Utils to parse xml
 *
 * @author PagSeguro Internet Ltda.
 */
public class XMLUtils {

    public static <T> T unmarshal(PagSeguro pagSeguro, Class<T> clazz, String rawXml) throws JAXBException {
        final JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
        final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        unmarshaller.setListener(new JaxBUnmarshalListener(pagSeguro, rawXml));
        try (StringReader reader = new StringReader(rawXml)) {
            return (T) unmarshaller.unmarshal(reader);
        }
    }

    private static class JaxBUnmarshalListener extends Listener {
        private final PagSeguro pagSeguro;
        private final String rawData;

        JaxBUnmarshalListener(PagSeguro pagSeguro, String rawData) {
            this.pagSeguro = pagSeguro;
            this.rawData = rawData;
        }

        @Override
        public void afterUnmarshal(Object target, Object parent) {
            super.afterUnmarshal(target, parent);
            if (target instanceof XMLUnmarshallListener) {
                ((XMLUnmarshallListener) target).onUnmarshal(pagSeguro, rawData);
            }
        }
    }
}
