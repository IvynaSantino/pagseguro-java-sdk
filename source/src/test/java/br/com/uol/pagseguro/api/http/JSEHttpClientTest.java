package br.com.uol.pagseguro.api.http;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

/**
 * @author PagSeguro Internet Ltda.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({JSEHttpClient.class})
public class JSEHttpClientTest {

    @Mock
    private URL url;

    @Mock
    private HttpURLConnection connection;

    private JSEHttpClient httpClient;

    private HttpMethod httpMethod;

    private String targetUrl;

    private Map<String, String> headers;

    private HttpRequestBody httpRequestBody;

    private ByteArrayOutputStream outputStream;

    private ByteArrayInputStream inputStream;

    @Before
    public void setUp() throws Exception {
        whenNew(URL.class).withAnyArguments().thenReturn(url);
        when(url.openConnection()).thenReturn(connection);
        httpClient = new JSEHttpClient();
        httpMethod = HttpMethod.POST;
        targetUrl = "http://localhost";
        headers = new HashMap<String, String>() {{
            put("header", "value");
        }};
        httpRequestBody = new HttpRequestBody("Content-Type: text/html", "param=value", "UTF-8");
        inputStream = new ByteArrayInputStream("çãàâ".getBytes());
        outputStream = new ByteArrayOutputStream();
        when(connection.getOutputStream()).thenReturn(outputStream);
    }

    @After
    public void verifications() throws Exception {

    }

    @Test
    public void shouldExecuteAndReturnSuccessWithDefaultCharset() throws Exception {
        when(connection.getContentType()).thenReturn(null);
        when(connection.getErrorStream()).thenReturn(null);
        when(connection.getInputStream()).thenReturn(inputStream);
        when(connection.getResponseCode()).thenReturn(200);

        HttpResponse expectedResponse = new HttpResponse(200, new String("çãàâ".getBytes(),
                Charset.forName("ISO-8859-1")));
        HttpResponse response = httpClient.execute(httpMethod, targetUrl, headers, httpRequestBody);
        assertEquals(expectedResponse, response);

        InOrder inOrder = inOrder(connection);
        inOrder.verify(connection, times(headers.size())).setRequestProperty(anyString(), anyString());
        inOrder.verify(connection).getOutputStream();
        inOrder.verify(connection).getContentType();
        inOrder.verify(connection).getErrorStream();
        inOrder.verify(connection).getInputStream();
        inOrder.verify(connection).disconnect();
    }

    @Test
    public void shouldExecuteAndReturnSuccessWithDefaultCharsetInContentType() throws Exception {
        when(connection.getContentType()).thenReturn("text/html");
        when(connection.getErrorStream()).thenReturn(null);
        when(connection.getInputStream()).thenReturn(inputStream);
        when(connection.getResponseCode()).thenReturn(200);

        HttpResponse expectedResponse = new HttpResponse(200, new String("çãàâ".getBytes(),
                Charset.forName("ISO-8859-1")));
        HttpResponse response = httpClient.execute(httpMethod, targetUrl, headers, httpRequestBody);
        assertEquals(expectedResponse, response);

        InOrder inOrder = inOrder(connection);
        inOrder.verify(connection, times(headers.size())).setRequestProperty(anyString(), anyString());
        inOrder.verify(connection).getOutputStream();
        inOrder.verify(connection).getContentType();
        inOrder.verify(connection).getErrorStream();
        inOrder.verify(connection).getInputStream();
        inOrder.verify(connection).disconnect();
    }

    @Test
    public void shouldExecuteAndReturnSuccessWithCustomCharset() throws Exception {
        when(connection.getContentType()).thenReturn("text/html; charset=UTF-8");
        when(connection.getErrorStream()).thenReturn(null);
        when(connection.getInputStream()).thenReturn(inputStream);
        when(connection.getResponseCode()).thenReturn(200);

        HttpResponse expectedResponse = new HttpResponse(200, new String("çãàâ".getBytes(),
                Charset.forName("UTF-8")));
        HttpResponse response = httpClient.execute(httpMethod, targetUrl, headers, httpRequestBody);
        assertEquals(expectedResponse, response);

        InOrder inOrder = inOrder(connection);
        inOrder.verify(connection, times(headers.size())).setRequestProperty(anyString(), anyString());
        inOrder.verify(connection).getOutputStream();
        inOrder.verify(connection).getContentType();
        inOrder.verify(connection).getErrorStream();
        inOrder.verify(connection).getInputStream();
        inOrder.verify(connection).disconnect();
    }

    @Test
    public void shouldExecuteAndReturnError() throws Exception {
        when(connection.getContentType()).thenReturn("text/html; charset=UTF-8");
        when(connection.getErrorStream()).thenReturn(inputStream);
        when(connection.getInputStream()).thenReturn(null);
        when(connection.getResponseCode()).thenReturn(400);

        HttpResponse expectedResponse = new HttpResponse(400, new String("çãàâ".getBytes(),
                Charset.forName("UTF-8")));
        HttpResponse response = httpClient.execute(httpMethod, targetUrl, headers, httpRequestBody);
        assertEquals(expectedResponse, response);

        InOrder inOrder = inOrder(connection);
        inOrder.verify(connection, times(headers.size())).setRequestProperty(anyString(), anyString());
        inOrder.verify(connection).getOutputStream();
        inOrder.verify(connection).getContentType();
        inOrder.verify(connection).getErrorStream();
        inOrder.verify(connection).disconnect();
    }

}