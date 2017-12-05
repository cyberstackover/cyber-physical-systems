package com.cyber.http;

import com.cyber.exceptions.MatagarudaException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;


public class MatagarudaHttpClient {

    private HttpClientContext httpContext = HttpClientContext.create();
    /**
     * http client based on apache http
     */
    private HttpClient httpClient;

    /**
     * public constructor
     *
     * @throws com.matagaruda.exceptions.MatagarudaException
     */
    public MatagarudaHttpClient() throws MatagarudaException {
        httpClient = this.configHttpClient().build();
    }

    /**
     *
     * @throws MatagarudaException
     */
    public HttpClientBuilder configHttpClient() throws MatagarudaException {
        try {
            HttpClientBuilder b = HttpClientBuilder.create();

            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                    return true;
                }
            }).build();
            b.setSslcontext(sslContext);

            HostnameVerifier hostnameVerifier = SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;

            SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
            Registry<ConnectionSocketFactory> socketFactoryRegistry
                    = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.getSocketFactory())
                    .register("https", sslSocketFactory)
                    .build();

            PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            b.setConnectionManager(connMgr);

            return b.setRedirectStrategy(new LaxRedirectStrategy());
        } catch (Exception ex) {
            throw new MatagarudaException();
        }
    }

    /**
     * public constructor
     *
     * @param client
     */
    public MatagarudaHttpClient(HttpClient client) {
        this.httpClient = client;
    }

    /**
     * shutdown http client and release it's resource
     */
    public void shutdown() {
        if (httpClient != null) {
            ClientConnectionManager connectionManager = httpClient.getConnectionManager();
            if (connectionManager != null) {
                connectionManager.shutdown();
            }
        }
    }

    /**
     * execute http request
     *
     * @param req
     * @return
     * @throws MatagarudaException
     */
    public MatagarudaHttpResponse execute(
            HttpRequestBase req) throws MatagarudaException {

        try {
            String responseBody = "";

            HttpResponse response = httpClient.execute(req, httpContext);
            Header contentTypeHeader = null;
            HttpEntity entity = response.getEntity();
            
            if (entity != null) {
                responseBody = EntityUtils.toString(entity);
                contentTypeHeader = entity.getContentType();
            }
            
            String contentType = null;
            if (contentTypeHeader != null) {
                contentType = contentTypeHeader.toString();
            }

            return new MatagarudaHttpResponse(responseBody,
                    contentType,
                    response.getAllHeaders(),
                    response.getStatusLine().getStatusCode());

        } catch (Exception e) {
            throw new MatagarudaException(e);
        }

    }

    /**
     *
     * @param url
     * @return
     * @throws IOException
     */
    public HttpResponse get(String url) throws IOException {
        HttpClient client = getHttpClient();
        HttpUriRequest request = RequestBuilder.get().setUri(url).build();
        return client.execute(request);
    }

    /**
     *
     * @return
     */
    public HttpClient getHttpClient() {
        return httpClient;
    }

    /**
     *
     * @return
     */
    public HttpClientContext getHttpContext() {
        return httpContext;
    }

}
