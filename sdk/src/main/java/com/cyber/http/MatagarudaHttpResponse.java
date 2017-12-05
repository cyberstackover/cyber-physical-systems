package com.cyber.http;

import com.cyber.exceptions.MatagarudaException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

public class MatagarudaHttpResponse {

    /**
     * body response
     */
    protected String body;

    /**
     * content type
     */
    protected String contentType;

    /**
     * response code
     */
    protected int responseCode;

    /**
     *
     */
    protected Map<String, Object> parameters = new HashMap<String, Object>();

    protected Header[] headers;

    /**
     *
     * @param body
     * @param contentType
     * @param responseCode
     * @throws MatagarudaException
     */
    public MatagarudaHttpResponse(String body, String contentType, Header[] headers, int responseCode) throws MatagarudaException {
        this.init(body, contentType, headers, responseCode);
    }

    /**
     *
     * @param param
     * @return
     */
    public String getParam(String param) {
        Object value = parameters.get(param);
        return value == null ? null : String.valueOf(value);
    }

    public String getBody() {
        return body;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getContentType() {
        return contentType;
    }

    protected void setBody(String body) throws MatagarudaException {
        this.body = body;
    }

    protected void setContentType(String contentType) {
        this.contentType = contentType;
    }

    protected void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public Header[] getHeaders() {
        return headers;
    }

    public void setHeaders(Header[] headers) {
        this.headers = headers;
    }

    /**
     *
     * @param body
     * @param contentType
     * @param responseCode
     * @throws SakaNusantaraOAuthException
     */
    protected void init(String body, String contentType, Header[] headers, int responseCode) throws MatagarudaException {
        this.setBody(body);
        this.setContentType(contentType);
        this.setResponseCode(responseCode);
        this.setHeaders(headers);
    }

    /**
     *
     * @return object
     */
    public Object getJsonObject() {
        JSONObject json = new JSONObject(body);
        return MatagarudaJsonUtil.toMap(json);
    }

    /**
     *
     * @return
     */
    public List getArrayJsonObject() {
        JSONArray json = new JSONArray(body);
        return MatagarudaJsonUtil.toList(json);
    }
}
