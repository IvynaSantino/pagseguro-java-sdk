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

package br.com.uol.pagseguro.api.environment;

import br.com.uol.pagseguro.api.PagSeguroEnv;

/**
 * Class responsible for providing the environment configurations of jvm
 *
 * @author PagSeguro Internet Ltda.
 */
public class JVMEnvVariableEnvironmentProvider implements EnvironmentProvider {

    @Override
    public PagSeguroEnv getEnvironment() throws Exception {
        getLogger().info("Lendo configuracoes de ambiente");
        final PagSeguroEnv environment;
        if (System.getProperty("pagseguro.environment") != null) {
            environment = PagSeguroEnv.fromName(System.getProperty("pagseguro.environment"));
        } else {
            throw new IllegalArgumentException("Environment property not found");
        }
        getLogger().info("Configuracoes de ambiente lidas");
        return environment;
    }
}
