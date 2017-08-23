package br.com.uol.pagseguro.api.environment;

import br.com.uol.pagseguro.api.PagSeguroEnv;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;

/**
 * @author PagSeguro Internet Ltda.
 */
@RunWith(PowerMockRunner.class)
public class SystemPropEnvironmentProviderTest {

    private SystemPropEnvironmentProvider provider;

    @Test
    public void shouldGetSandboxEnv() throws Exception {
        provider = new SystemPropEnvironmentProvider("pagseguro.properties");
        PagSeguroEnv expectedEnv = PagSeguroEnv.SANDBOX;
        PagSeguroEnv env = provider.getEnvironment();
        assertEquals(expectedEnv, env);
    }

    @Test
    public void shouldGetProductionEnv() throws Exception {
        provider = new SystemPropEnvironmentProvider("pagseguro2.properties");
        PagSeguroEnv expectedEnv = PagSeguroEnv.PRODUCTION;
        PagSeguroEnv env = provider.getEnvironment();
        assertEquals(expectedEnv, env);
    }
}