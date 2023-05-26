package com.blue.sqldb.exception;

public class DuplicationKeyException extends RuntimeException {
    public DuplicationKeyException(Long key) {
        super("Duplicate entry,key:" + key);
    }
}
