package com.cyber.authentication;

import com.cyber.exceptions.MatagarudaException;
import com.cyber.exceptions.MatagarudaOAuthException;
import java.net.URISyntaxException;

public interface OAuth2Grant {

    /**
     * get access token
     *
     * @return AccessToken
     * @throws com.matagaruda.exceptions.MatagarudaOAuthException
     * @throws java.net.URISyntaxException
     * @throws com.matagaruda.exceptions.MatagarudaException
     */
    AccessToken getAccessToken()throws MatagarudaOAuthException, URISyntaxException, MatagarudaException;

    String getAuthorizationURI() throws URISyntaxException;

}
