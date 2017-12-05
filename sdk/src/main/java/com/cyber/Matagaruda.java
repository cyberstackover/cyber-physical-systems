package com.cyber;

import com.cyber.authentication.AccessToken;
import com.cyber.authentication.AuthorizationCode;
import com.cyber.authentication.ClientCredentials;
import com.cyber.authentication.GrantType;
import com.cyber.authentication.OAuth2Client;
import com.cyber.authentication.PasswordGrant;
import com.cyber.authentication.RefreshTokenGrant;
import com.cyber.exceptions.MatagarudaException;
import com.cyber.exceptions.MatagarudaOAuthException;
import com.cyber.http.MatagarudaHttpClient;
import com.cyber.http.MatagarudaHttpResponse;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;

public class Matagaruda {

    /**
     * const for base url
     */
    public final static String BASE_URL = "http://iout-er2c.com:8001/";//"https://sapawarga.surabaya.go.id/";

    /**
     *
     */
    public final static String API_ENDPOINT = "http://iout-er2c.com:8002/" + "api/v1";
    /**
     * access token key
     */
    public final static String ACCESS_TOKEN = "access_token";

    /**
     * sdk version
     */
    public final static String SKD_VERSION = "v1";

    /**
     * client id
     *
     * @var string
     */
    private String clientId;

    /**
     * client secret
     *
     * @var string
     */
    private String clientSecret;

    /**
     * redirect url
     *
     * @var string
     */
    private String redirectUrl;

    /**
     * scopes
     *
     * @var string
     */
    private String[] scopes;

    /**
     *
     * @var SakaNusantaraHttpClient;
     */
    private MatagarudaHttpClient httpClient;

    /**
     *
     * @var AccessToken
     */
    private AccessToken accessToken;

    /**
     *
     * @var OAuth2Client
     */
    private OAuth2Client oauth2Client;

    /**
     *
     */
    private GrantType grantType;

    /**
     *
     * @throws MatagarudaException
     */
    public Matagaruda() throws MatagarudaException {
        httpClient = new MatagarudaHttpClient();
    }

    /**
     *
     * @param url
     * @return
     */
    public MatagarudaHttpResponse get(String url) throws URISyntaxException, MatagarudaException {
        return this.get(url, new HashMap<String, String>());
    }

    /**
     * get request
     *
     * @param url
     * @param param
     * @return
     * @throws URISyntaxException
     * @throws MatagarudaException
     */
    public MatagarudaHttpResponse get(String url, Map<String, String> param) throws URISyntaxException, MatagarudaException {
        URIBuilder builder = new URIBuilder(API_ENDPOINT + url);
        for (Map.Entry<String, String> entrySet : param.entrySet()) {
            builder.addParameter(entrySet.getKey(), entrySet.getValue());
        }
        HttpGet request = new HttpGet(builder.build());
        return this.request(request);
    }

    /**
     * get request
     *
     * @param url
     * @param json
     * @param param
     * @return
     * @throws URISyntaxException
     * @throws MatagarudaException
     * @throws java.io.UnsupportedEncodingException
     */
    public MatagarudaHttpResponse postJson(String url, String json) throws URISyntaxException, MatagarudaException, UnsupportedEncodingException {
        url = API_ENDPOINT + url;
        HttpPost httpPost = new HttpPost(url);

        StringEntity entity = new StringEntity(json);
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        System.out.println(httpPost.toString());
        return this.request(httpPost);
    }

    /**
     *
     * @param request
     * @return
     * @throws MatagarudaException
     * @throws java.net.URISyntaxException
     */
    public MatagarudaHttpResponse request(HttpRequestBase request) throws MatagarudaException, URISyntaxException {
        try {
            String token = getAccessToken().getAccessToken();
            request.addHeader("Authorization", "Bearer " + token);
            return httpClient.execute(request);
        } catch (MatagarudaOAuthException ex) {
            throw new MatagarudaException("access token error");
        }
    }

    /**
     * set grant type
     *
     * @param grantType
     * @return
     */
    public Matagaruda setGrantType(GrantType grantType) {
        this.grantType = grantType;
        return this;
    }

    /**
     *
     * @param grantType
     * @return
     * @throws MatagarudaOAuthException
     */
    public Matagaruda build() throws MatagarudaOAuthException {
        switch (grantType) {
            case AUTHORIZATION_CODE:
                this.oauth2Client = new AuthorizationCode(clientId, clientSecret, redirectUrl, scopes, httpClient);
                return this;
            case CLIENT_CREDENTIALS:
                this.oauth2Client = new ClientCredentials(clientId, clientSecret, scopes, httpClient);
                return this;
            case PASSWORD_GRANT:
                this.oauth2Client = new PasswordGrant(clientId, clientSecret, scopes, httpClient);
                return this;
            case REFRESH_TOKEN:
                this.oauth2Client = new RefreshTokenGrant(clientId, clientSecret, scopes, httpClient);
                return this;
            default:
                throw new MatagarudaOAuthException("error grant type", "");
        }
    }

    /**
     *
     * @return @throws URISyntaxException
     * @throws com.matagaruda.exceptions.MatagarudaException
     * @throws com.matagaruda.exceptions.MatagarudaOAuthException
     */
    public String getAuthorizationUrl() throws URISyntaxException, MatagarudaException, MatagarudaOAuthException {
        if (!(oauth2Client instanceof AuthorizationCode)) {
            oauth2Client = setGrantType(GrantType.AUTHORIZATION_CODE)
                    .build().oauth2Client;
        }
        return oauth2Client.getAuthorizationURI();
    }

    /**
     *
     * @param code
     */
    public Matagaruda setAuthorizationCode(String code) throws MatagarudaException {
        if (oauth2Client instanceof AuthorizationCode) {
            this.oauth2Client.setCode(code);
            return this;
        } else {
            throw new MatagarudaException("must be authorization grant");
        }
    }

    /**
     *
     * @return @throws MatagarudaOAuthException
     * @throws java.net.URISyntaxException
     * @throws com.matagaruda.exceptions.MatagarudaException
     */
    public AccessToken getAccessToken() throws MatagarudaOAuthException, URISyntaxException, MatagarudaException {

        Date time = new Date();

        if (accessToken == null) {
            accessToken = oauth2Client.getAccessToken();
        }

        System.out.println("refresh token " + accessToken.getExpiresIn() + "  " + time.getTime() + "    " + (accessToken.getExpiresIn() - time.getTime()));
        if (accessToken.getExpiresIn() < time.getTime()) {
            this.accessToken = refreshAccessToken();
        }

        return accessToken;
    }

    /**
     *
     * @return @throws MatagarudaOAuthException
     * @throws URISyntaxException
     * @throws MatagarudaException
     */
    public AccessToken refreshAccessToken() throws MatagarudaOAuthException, URISyntaxException, MatagarudaException {
        //check if refresh token is valid
        if (!accessToken.getRefreshToken().isEmpty()) {
            System.out.println("refresh token");
            RefreshTokenGrant client = (RefreshTokenGrant) setGrantType(GrantType.REFRESH_TOKEN)
                    .build().oauth2Client;

            return client.setAccessToken(accessToken).getAccessToken();
        }
        //otherwise just request new access token
        return oauth2Client.getAccessToken();
    }

    /**
     *
     * @param accessToken
     */
    public void setAccessToken(AccessToken accessToken) throws MatagarudaOAuthException, URISyntaxException, MatagarudaException {

        Date time = new Date();
        this.accessToken = accessToken;

        if (accessToken.getExpiresIn() < time.getTime()) {
            this.accessToken = refreshAccessToken();
        }
    }

    public Matagaruda setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public Matagaruda setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    public Matagaruda setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
        return this;
    }

    public Matagaruda setScopes(String[] scopes) {
        this.scopes = scopes;
        return this;
    }

}
