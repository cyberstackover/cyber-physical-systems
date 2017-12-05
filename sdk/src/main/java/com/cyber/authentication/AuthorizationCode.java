package com.cyber.authentication;

import com.cyber.Matagaruda;
import com.cyber.exceptions.MatagarudaOAuthException;
import com.cyber.http.MatagarudaHttpClient;
import com.cyber.http.MatagarudaHttpResponse;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;

public class AuthorizationCode extends OAuth2Client{

    

    /**
     *
     * @param clientId
     * @param clientSecret
     * @param redirectUrl
     * @param scopes
     * @param httpClient
     */
    public AuthorizationCode(String clientId,
            String clientSecret,
            String redirectUrl,
            String[] scopes,
            MatagarudaHttpClient httpClient) {
        super(clientId, clientSecret, redirectUrl, scopes, httpClient);
    }

    /**
     *
     * @return @throws URISyntaxException
     */
    @Override
    public String getAuthorizationURI() throws URISyntaxException {
        List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
        queryParams.add(new BasicNameValuePair("client_id", this.clientId));
        queryParams.add(new BasicNameValuePair("response_type", "code"));
        queryParams.add(new BasicNameValuePair("redirect_uri", this.redirectUrl));
        queryParams.add(new BasicNameValuePair("scope", getFormatedScopes()));
        queryParams.add(new BasicNameValuePair("state", "state"));

        URIBuilder builder = new URIBuilder()
                .setPath("account/oauth/authorize")
                .setParameters(queryParams);

        URI uri = builder.build();

        return Matagaruda.BASE_URL + uri.toString();
    }

    /**
     *
     * @return @throws MatagarudaOAuthException
     */
    public AccessToken getAccessToken() throws MatagarudaOAuthException {

        try {
            HttpPost httpPost = new HttpPost(Matagaruda.BASE_URL + "account/oauth/token");

            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("client_id", clientId));
            nvps.add(new BasicNameValuePair("client_secret", clientSecret));
            nvps.add(new BasicNameValuePair("grant_type", "authorization_code"));
            nvps.add(new BasicNameValuePair("redirect_uri", redirectUrl));
            nvps.add(new BasicNameValuePair("code", code));

            httpPost.setEntity(new UrlEncodedFormEntity(nvps));

            MatagarudaHttpResponse response = httpClient.execute(httpPost);
            
            return getAccessTokenFromResponseBody(response);
            
        } catch (Exception ex) {
            throw new MatagarudaOAuthException(ex.getLocalizedMessage(), ex.getMessage());
        }
    }

    

}
