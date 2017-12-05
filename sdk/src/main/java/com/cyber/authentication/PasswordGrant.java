package com.cyber.authentication;

import com.cyber.Matagaruda;
import com.cyber.exceptions.MatagarudaException;
import com.cyber.exceptions.MatagarudaOAuthException;
import com.cyber.http.MatagarudaHttpClient;
import com.cyber.http.MatagarudaHttpResponse;
import java.net.URISyntaxException;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;

public class PasswordGrant extends OAuth2Client {

    private String username;

    private String password;

    public PasswordGrant(String clientId, String clientSecret, String[] scopes, MatagarudaHttpClient httpClient) {
        super(clientId, clientSecret, null, scopes, httpClient);
    }

    /**
     *
     * @param username
     * @param password
     * @return
     */
    public PasswordGrant setCredentials(String username, String password) {
        this.username = username;
        this.password = password;
        return this;
    }

    /**
     *
     * @return @throws MatagarudaOAuthException
     * @throws java.net.URISyntaxException
     * @throws com.matagaruda.exceptions.MatagarudaException
     */
    public AccessToken getAccessToken() throws MatagarudaOAuthException, URISyntaxException, MatagarudaException {

        URIBuilder builder = new URIBuilder(Matagaruda.BASE_URL + "account/oauth/token")
                .addParameter("grant_type", "password")
                .addParameter("username", username)
                .addParameter("password", password)
                .addParameter("scope", getFormatedScopes());
        HttpPost httpPost = new HttpPost(builder.build());
        httpPost.setHeader(HttpHeaders.AUTHORIZATION, buildBasicAuth(clientId, clientSecret));

        MatagarudaHttpResponse response = httpClient.execute(httpPost);

        AccessToken accessToken = getAccessTokenFromResponseBody(response);

        return accessToken;
    }

    public String getAuthorizationURI() throws URISyntaxException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
