package com.dkharlamov.qanterium.exchange.common.exception;

public class RatesDoesntExistException extends RuntimeException {

    public RatesDoesntExistException(Throwable cause, String message, Object... objects) {
        super(String.format(message, objects), cause);
    }

    public RatesDoesntExistException(String message, Object... objects) {
        super(String.format(message, objects));
    }
}
