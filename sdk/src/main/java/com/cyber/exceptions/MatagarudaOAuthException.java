package com.cyber.exceptions;

import java.util.HashMap;
import java.util.Map;

public class MatagarudaOAuthException extends Exception {

    private String error;
    private String description;
    private String uri;
    private String state;
    private String scope;
    private String redirectUri;

    private int responseStatus;

    private Map<String, String> parameters = new HashMap<String, String>();

    public MatagarudaOAuthException(Exception ex){
        super(ex);
    }
    
    protected MatagarudaOAuthException(String error) {
        this(error, "");
    }

    public MatagarudaOAuthException(String error, String description) {
        super(error + " " + description);
        this.description = description;
        this.error = error;
    }


    public static MatagarudaOAuthException error(String error) {
        return new MatagarudaOAuthException(error);
    }

    public static MatagarudaOAuthException error(String error, String description) {
        return new MatagarudaOAuthException(error, description);
    }

    public MatagarudaOAuthException description(String description) {
        this.description = description;
        return this;
    }

    public MatagarudaOAuthException uri(String uri) {
        this.uri = uri;
        return this;
    }

    public MatagarudaOAuthException state(String state) {
        this.state = state;
        return this;
    }

    public MatagarudaOAuthException scope(String scope) {
        this.scope = scope;
        return this;
    }

    public MatagarudaOAuthException responseStatus(int responseStatus) {
        this.responseStatus = responseStatus;
        return this;
    }

    public MatagarudaOAuthException setParameter(String name, String value) {
        parameters.put(name, value);
        return this;
    }

    public String getError() {
        return error;
    }

    public String getDescription() {
        return description;
    }

    public String getUri() {
        return uri;
    }

    public String getState() {
        return state;
    }

    public String getScope() {
        return scope;
    }

    public int getResponseStatus() {
        return responseStatus == 0 ? 400 : responseStatus;
    }

    public String get(String name) {
        return parameters.get(name);
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    @Override
    public String getMessage() {
        StringBuilder b = new StringBuilder();
        if (!error.isEmpty()) {
            b.append(error);
        }

        if (!description.isEmpty()) {
            b.append(", ").append(description);
        }

        if (!scope.isEmpty()) {
            b.append(", ").append(scope);
        }

        return b.toString();
    }

    @Override
    public String toString() {
        return "OAuthProblemException{" +
                "error='" + error + '\'' +
                ", description='" + description + '\'' +
                ", uri='" + uri + '\'' +
                ", state='" + state + '\'' +
                ", scope='" + scope + '\'' +
                ", redirectUri='" + redirectUri + '\'' +
                ", responseStatus=" + responseStatus +
                ", parameters=" + parameters +
                '}';
    }
    
}
