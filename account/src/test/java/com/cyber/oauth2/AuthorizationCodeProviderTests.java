package com.cyber.oauth2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.oauth2.client.resource.UserApprovalRequiredException;
import org.springframework.security.oauth2.client.resource.UserRedirectRequiredException;
import org.springframework.security.oauth2.client.test.BeforeOAuth2Context;
import org.springframework.security.oauth2.client.test.OAuth2ContextConfiguration;
import org.springframework.security.oauth2.client.test.OAuth2ContextSetup;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InsufficientScopeException;
import org.springframework.security.oauth2.common.exceptions.RedirectMismatchException;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import com.cyber.oauth2.ServerRunning.UriBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.ResponseExtractor;

public class AuthorizationCodeProviderTests {

    @Rule
    public ServerRunning serverRunning = ServerRunning.isRunning();

    @Rule
    public OAuth2ContextSetup context = OAuth2ContextSetup.standard(serverRunning);

    private AuthorizationCodeAccessTokenProvider accessTokenProvider;

    private String cookie;

    private ClientHttpResponse tokenEndpointResponse;

    @BeforeOAuth2Context
    public void setupAccessTokenProvider() {
        accessTokenProvider = new AuthorizationCodeAccessTokenProvider() {

            private ResponseExtractor<OAuth2AccessToken> extractor = super.getResponseExtractor();

            private ResponseExtractor<ResponseEntity<Void>> authExtractor = super.getAuthorizationResponseExtractor();

            private ResponseErrorHandler errorHandler = super.getResponseErrorHandler();

            @Override
            protected ResponseErrorHandler getResponseErrorHandler() {
                return new DefaultResponseErrorHandler() {
                    public void handleError(ClientHttpResponse response) throws IOException {
                        response.getHeaders();
                        response.getStatusCode();
                        tokenEndpointResponse = response;
                        errorHandler.handleError(response);
                    }
                };
            }

            @Override
            protected ResponseExtractor<OAuth2AccessToken> getResponseExtractor() {
                return new ResponseExtractor<OAuth2AccessToken>() {

                    public OAuth2AccessToken extractData(ClientHttpResponse response) throws IOException {
                        response.getHeaders();
                        response.getStatusCode();
                        tokenEndpointResponse = response;
                        return extractor.extractData(response);
                    }

                };
            }

            @Override
            protected ResponseExtractor<ResponseEntity<Void>> getAuthorizationResponseExtractor() {
                return new ResponseExtractor<ResponseEntity<Void>>() {

                    public ResponseEntity<Void> extractData(ClientHttpResponse response) throws IOException {
                        response.getHeaders();
                        response.getStatusCode();
                        tokenEndpointResponse = response;
                        return authExtractor.extractData(response);
                    }
                };
            }
        };
        context.setAccessTokenProvider(accessTokenProvider);
    }

    //@Test
    public void testResourceIsProtected() throws Exception {
        // first make sure the resource is actually protected.
        assertEquals(HttpStatus.UNAUTHORIZED, serverRunning.getStatusCode("/api/v1/user"));
    }

    //@Test
    @OAuth2ContextConfiguration(resource = DeveloperClient.class, initialize = false)
    public void testUnauthenticatedAuthorizationRequestRedirectsToLogin() throws Exception {

        AccessTokenRequest request = context.getAccessTokenRequest();
        request.setCurrentUri("http://iout-er2c.com:8000/developer");
        request.add(OAuth2Utils.USER_OAUTH_APPROVAL, "true");

        String location = null;

        try {
            String code = accessTokenProvider.obtainAuthorizationCode(context.getResource(), request);
            assertNotNull(code);
            fail("Expected UserRedirectRequiredException");
        } catch (UserRedirectRequiredException e) {
            location = e.getRedirectUri();
        }

        assertNotNull(location);
        assertEquals(serverRunning.getUrl("/account/login?error=true"), location);

    }

    //@Test
    //@OAuth2ContextConfiguration(resource = DeveloperClient.class, initialize = false)
    public void testSuccessfulAuthorizationCodeFlow() throws Exception {

        // Once the request is ready and approved, we can continue with the access token
        approveAccessTokenGrant("http://iout-er2c.com:8000/developer", true);

        // Finally everything is in place for the grant to happen...
        assertNotNull(context.getAccessToken());

        AccessTokenRequest request = context.getAccessTokenRequest();
        assertNotNull(request.getAuthorizationCode());
        System.out.println(serverRunning.getRestTemplate().toString());
        assertEquals(HttpStatus.OK, serverRunning.getStatusCode("/api/v1/user"));

    }

    //@Test
    public void testInvalidScopeInAuthorizationRequest() throws Exception {
        // Need to use the client with a redirect because "my-less-trusted-client" has no registered scopes
        String cookie = loginAndGrabCookie();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.TEXT_HTML));
        headers.set("Cookie", cookie);

        String scope = "account";
        String redirectUri = "http://iout-er2c.com:8000/developer";
        String clientId = "developer";

        UriBuilder uri = serverRunning.buildUri("/account/oauth/authorize").queryParam("response_type", "code")
                .queryParam("state", "mystateid").queryParam("scope", scope);
        if (clientId != null) {
            uri.queryParam("client_id", clientId);
        }
        if (redirectUri != null) {
            uri.queryParam("redirect_uri", redirectUri);
        }
        ResponseEntity<String> response = serverRunning.getForString(uri.pattern(), headers, uri.params());
        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        String location = response.getHeaders().getLocation().toString();
        assertTrue(location.startsWith("http://iout-er2c.com:8000/developer"));
        assertTrue(location.contains("error=invalid_scope"));
        assertFalse(location.contains("redirect_uri="));
    }

   
    //@BeforeOAuth2Context
    public void loginAndExtractCookie() {
        this.cookie = loginAndGrabCookie();
    }

    private String loginAndGrabCookie() {

        ResponseEntity<String> page = serverRunning.getForString("/account/login");
        String cookie = page.getHeaders().getFirst("Set-Cookie");
        Matcher matcher = Pattern.compile("(?s).*name=\"_csrf\".*?value=\"([^\"]+).*").matcher(page.getBody());
        System.out.println(page.getBody());
        MultiValueMap<String, String> formData;
        formData = new LinkedMultiValueMap<String, String>();
        formData.add("username", "developer");
        formData.add("password", "admin");
        if (matcher.matches()) {
            formData.add("_csrf", matcher.group(1));
        }

        String location = "/account/login";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", cookie);
        headers.setAccept(Arrays.asList(MediaType.TEXT_HTML));
        ResponseEntity<Void> result = serverRunning.postForStatus(location, headers, formData);
        assertEquals(HttpStatus.FOUND, result.getStatusCode());
        cookie = result.getHeaders().getFirst("Set-Cookie");

        assertNotNull("Expected cookie in " + result.getHeaders(), cookie);
        return cookie;
    }

    private ResponseEntity<String> attemptToGetConfirmationPage(String clientId, String redirectUri) {

        if (cookie == null) {
            cookie = loginAndGrabCookie();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.TEXT_HTML));
        headers.set("Cookie", cookie);

        return serverRunning.getForString(getAuthorizeUrl(clientId, redirectUri, "account"), headers);

    }

    private String getAuthorizeUrl(String clientId, String redirectUri, String scope) {
        UriBuilder uri = serverRunning.buildUri("/account/oauth/authorize").queryParam("response_type", "code")
                .queryParam("state", "mystateid").queryParam("scope", scope);
        if (clientId != null) {
            uri.queryParam("client_id", clientId);
        }
        if (redirectUri != null) {
            uri.queryParam("redirect_uri", redirectUri);
        }
        return uri.build().toString();
    }

    private void approveAccessTokenGrant(String currentUri, boolean approved) {

        AccessTokenRequest request = context.getAccessTokenRequest();
        AuthorizationCodeResourceDetails resource = (AuthorizationCodeResourceDetails) context.getResource();

        request.setCookie(cookie);
        if (currentUri != null) {
            request.setCurrentUri(currentUri);
        }

        String location = null;

        try {
            // First try to obtain the access token...
            assertNotNull(context.getAccessToken());
            fail("Expected UserRedirectRequiredException");
        } catch (UserRedirectRequiredException e) {
            // Expected and necessary, so that the correct state is set up in the request...
            location = e.getRedirectUri();
        }

        assertTrue(location.startsWith(resource.getUserAuthorizationUri()));
        assertNull(request.getAuthorizationCode());

        try {
            // Now try again and the token provider will redirect for user approval...
            assertNotNull(context.getAccessToken());
            fail("Expected UserRedirectRequiredException");
        } catch (UserApprovalRequiredException e) {
            // Expected and necessary, so that the user can approve the grant...
            location = e.getApprovalUri();
        }

        assertTrue(location.startsWith(resource.getUserAuthorizationUri()));
        assertNull(request.getAuthorizationCode());

        // The approval (will be processed on the next attempt to obtain an access token)...
        request.set(OAuth2Utils.USER_OAUTH_APPROVAL, "" + approved);

    }

    static class DeveloperClient extends AuthorizationCodeResourceDetails {

        public DeveloperClient(Object target) {
            super();
            setClientId("developer");
            setScope(Arrays.asList("account"));
            setId(getClientId());
            AuthorizationCodeProviderTests test = (AuthorizationCodeProviderTests) target;
            setAccessTokenUri(test.serverRunning.getUrl("/account/oauth/token"));
            setUserAuthorizationUri(test.serverRunning.getUrl("/account/oauth/authorize"));

        }
    }

    static class MyClientWithRegisteredRedirect extends DeveloperClient {

        public MyClientWithRegisteredRedirect(Object target) {
            super(target);
            setClientId("developer");
            setPreEstablishedRedirectUri("http://iout-er2c.com:8000/developer");
        }
    }
}
