package com.triple.club.api.exception;

import org.springframework.transaction.TransactionException;

public class TransactionFailureException extends TransactionException {
    public TransactionFailureException(String msg) {
        super(msg);
    }
}
