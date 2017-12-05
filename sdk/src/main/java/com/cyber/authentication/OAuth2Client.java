package com.cyber.authentication;

import com.cyber.exceptions.MatagarudaOAuthException;
import com.cyber.http.MatagarudaHttpClient;
import com.cyber.http.MatagarudaHttpResponse;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Map;
import org.apache.commons.codec.binary.Base64;

public abstract class OAuth2Client implements OAuth2Grant {

    protected String clientId;

    protected String clientSecret;

    protected String redirectUrl;

    protected String[] scopes;

    protected MatagarudaHttpClient httpClient;

    protected String code;

    public OAuth2Client(String clientId,
            String clientSecret,
            String redirectUrl,
            String[] scopes,
            MatagarudaHttpClient httpClient) {

        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUrl = redirectUrl;
        this.scopes = scopes;

        this.httpClient = httpClient;
    }

    /**
     * build basic auth
     *
     * @param username
     * @param password
     * @return String
     */
    public String buildBasicAuth(String username, String password) {
        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("ISO-8859-1")));
        String authHeader = "Basic " + new String(encodedAuth);
        return authHeader;
    }

    /**
     * parse response body to access token
     *
     * @param response
     * @return
     * @throws MatagarudaOAuthException
     */
    protected AccessToken getAccessTokenFromResponseBody(MatagarudaHttpResponse response) throws MatagarudaOAuthException {
        Map<String, Object> value = (Map<String, Object>) response.getJsonObject();
        String refreshToken = "";
        if (value.containsKey("error")) {
            throw new MatagarudaOAuthException(value.get("error").toString(), response.getBody());
        }
        String accesstoken = value.get("access_token").toString();
        String[] scope = value.get("scope").toString().split(" ");
        int expiresId = Integer.parseInt(value.get("expires_in").toString());
       
        if (value.containsKey("refresh_token")) {
            refreshToken = value.get("refresh_token").toString();
        }
        return new AccessToken(accesstoken, expiresId, scope, refreshToken);
    }

    /**
     * get formated scope for request
     *
     * @return
     */
    public String getFormatedScopes() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < scopes.length - 1; i++) {
            if (!scopes[i].matches(" *")) {
                sb.append(scopes[i]);
                sb.append(" ");
            }
        }
        sb.append(scopes[scopes.length - 1].trim());
        return sb.toString();
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String[] getScopes() {
        return scopes;
    }

    public void setScopes(String[] scopes) {
        this.scopes = scopes;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public MatagarudaHttpClient getHttpClient() {
        return httpClient;
    }

    public void setHttpClient(MatagarudaHttpClient httpClient) {
        this.httpClient = httpClient;
    }

}
