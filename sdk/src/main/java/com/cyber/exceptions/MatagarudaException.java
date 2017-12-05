package com.cyber.exceptions;

public class MatagarudaException extends Exception {

    public MatagarudaException() {
        super();
    }

    public MatagarudaException(String s) {
        super(s);
    }

    public MatagarudaException(Throwable throwable) {
        super(throwable);
    }

    public MatagarudaException(String s, Throwable throwable) {
        super(s, throwable);
    }

}
