package com.wallet.exception;

public class DuplicateTransactionException extends RuntimeException {

    public DuplicateTransactionException() {
        super("Duplicate transaction detected");
    }
}