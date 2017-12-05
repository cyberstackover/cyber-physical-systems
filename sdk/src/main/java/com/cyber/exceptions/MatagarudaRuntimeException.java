package com.cyber.exceptions;

public class MatagarudaRuntimeException extends RuntimeException {

    public MatagarudaRuntimeException() {
        super();
    }

    public MatagarudaRuntimeException(String message) {
        super(message);
    }

    public MatagarudaRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public MatagarudaRuntimeException(Throwable cause) {
        super(cause);
    }

}
