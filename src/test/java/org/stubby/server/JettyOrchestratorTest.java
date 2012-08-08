package org.stubby.server;

import org.eclipse.jetty.server.Server;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.stubby.database.DataStore;
import org.stubby.exception.Stubby4JException;
import org.stubby.yaml.stubs.StubHttpLifecycle;

import java.util.Map;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Alexander Zagniotov
 * @since 7/18/12, 9:57 PM
 */
public class JettyOrchestratorTest {

   private static JettyOrchestrator jettyOrchestrator;

   final static Server mockServer = Mockito.mock(Server.class);
   final static DataStore mockDataStore = Mockito.mock(DataStore.class);
   @SuppressWarnings("unchecked")
   final static Map<String, String> mockCommandLineArgs = Mockito.mock(Map.class);

   @BeforeClass
   public static void beforeClass() throws Exception {
      jettyOrchestrator = new JettyOrchestrator(mockServer, mockDataStore, mockCommandLineArgs);
   }

   @Test
   public void shouldVerifyBehaviourWhenRegistereringValidStubData() throws Exception {
      jettyOrchestrator.registerStubData("" +
            "-  request:\n" +
            "      method: GET\n" +
            "      url: /item/redirect\n" +
            "\n" +
            "   response:\n" +
            "      latency: 1000\n" +
            "      status: 301\n" +
            "      headers:\n" +
            "         location: /item/1\n" +
            "      body:");

      verify(mockDataStore, times(1)).setStubHttpLifecycles(Mockito.anyListOf(StubHttpLifecycle.class));
   }

   @Test(expected = Stubby4JException.class)
   public void shouldNotRegisterStubData() throws Exception {
      jettyOrchestrator.registerStubData("alex");
   }
}
