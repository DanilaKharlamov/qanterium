package com.dkharlamov.qanterium.common.constant;

public final class ExceptionConstants {
    private ExceptionConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String notFound = "Content was not found by %s:%s";
    public static final String defaultMemberMessage = "Exception during work with members api! Error message:%s";
    public static final String defaultTransactionMessage = "Exception during work with transactions api! Error message:%s";
    public static final String defaultExpenseCardMessage = "Exception during work with expense card api! Error message:%s";

}
