package com.duzon.lulu.exception;


public class HisException extends HisServiceException {
    public HisException() { }
    public HisException(String message, int code) {
        super(message, code);
    }
    public HisException(String message) { super(message); }
    public HisException(String message, int code, Exception ex) { super(message, code, ex); }

}
