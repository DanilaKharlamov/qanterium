package com.dkharlamov.qanterium.exchange.common.exception;

public class ExchangeException extends RuntimeException {
    public ExchangeException(Throwable cause, String message, Object... objects) {
        super(String.format(message, objects), cause);
    }

    public ExchangeException(String message, Object... objects) {
        super(String.format(message, objects));
    }
}
