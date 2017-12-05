package com.cyber.frontend;

import org.springframework.http.HttpStatus;
public class JsonResponse {

    private HttpStatus status;

    private String error;

    private String message;

    private long total;

    private Object data;

    public JsonResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    /**
     * render json
     *
     * @param error
     * @param message
     * @param data
     * @return
     */
    public static JsonResponse render(HttpStatus status, Object data, long total) {
        return new JsonResponse(status, null, data, total);
    }

    /**
     * contructor
     *
     * @param errorCode
     * @param message
     * @param data
     */
    public JsonResponse(HttpStatus error, String message, Object data) {
        this.status = error;
        this.message = message;
        this.data = data;
    }

    /**
     *
     * @param error
     * @param message
     * @param data
     * @param total
     */
    public JsonResponse(HttpStatus error, String message, Object data, long total) {
        this.status = error;
        this.message = message;
        this.data = data;
        this.total = total;
    }

    /**
     * render json
     *
     * @param error
     * @param message
     * @param data
     * @return
     */
    public static JsonResponse render(HttpStatus status, Object data) {
        return new JsonResponse(status, null, data);
    }

    /**
     * render json
     *
     * @param error
     * @param message
     * @param data
     * @return
     */
    public static JsonResponse render(HttpStatus status, String message, Object data) {
        return new JsonResponse(status, message, data);
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
