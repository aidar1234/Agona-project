package com.technokratos.exception;

public class PublicationNotFoundException extends RuntimeException {

    public PublicationNotFoundException() {
        super();
    }

    public PublicationNotFoundException(String message) {
        super(message);
    }

    public PublicationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PublicationNotFoundException(Throwable cause) {
        super(cause);
    }
}
