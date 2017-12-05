package com.cyber.authentication;

import java.util.Date;
import org.json.JSONObject;

public class AccessToken {

    /**
     * access token
     */
    private String accessToken;

    /**
     * expires access token
     */
    private long expiresIn;

    /**
     * scopes or permission
     */
    private String[] scopes;

    /**
     * refresh token
     */
    private String refreshToken;
    
    /**
     * 
     */
    private static final int EXPIRED = 43199;

    /**
     *
     * @param token
     * @param expiresIn
     * @param scopes
     * @param refreshToken
     */
    public AccessToken(String token, long expiresIn, String[] scopes, String refreshToken) {
        this.accessToken = token;
        this.scopes = scopes;
        this.refreshToken = refreshToken;
        setExpiresIn(expiresIn);
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expires) {
        long exp = expires;
        if (expires <= EXPIRED) {
            Date now = new Date();
            long time = now.getTime();
            exp += time;
        }
        
        this.expiresIn = exp;
    }

    public String[] getScopes() {
        return scopes;
    }

    public void setScopes(String[] scopes) {
        this.scopes = scopes;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    
    public String toJson() {

        JSONObject obj = new JSONObject();

        obj.put("access_token", accessToken);
        obj.put("expires_in", getExpiresIn());
        obj.put("scopes", getFormatedScopes());
        obj.put("refresh_token", refreshToken);
        return obj.toString();
    }

    public static AccessToken fromJson(String json) {
        JSONObject obj = new JSONObject(json);

        String accessToken = obj.getString("access_token");
        long expiresIn = obj.getLong("expires_in");
        String refreshToken = obj.getString("refresh_token");
        String[] scopes = obj.getString("scopes").split(" ");

        return new AccessToken(accessToken, expiresIn, scopes, refreshToken);
    }

    private String getFormatedScopes() {
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

    @Override
    public String toString() {
        return "AccessToken{" + "accessToken=" + accessToken + ", expiresIn=" + expiresIn + ", scopes=" + scopes + ", refreshToken=" + refreshToken + '}';
    }
}
