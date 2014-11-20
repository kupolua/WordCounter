package com.qalight.javacourse.util;

public class WordCounterRuntimeException extends RuntimeException {
    private ErrorCode errorCode;
    private String clientRequest;

    public WordCounterRuntimeException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public WordCounterRuntimeException(ErrorCode errorCode, String clientRequest) {
        this.errorCode = errorCode;
        this.clientRequest = clientRequest;
    }

    public WordCounterRuntimeException(ErrorCode errorCode, String clientRequest, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
        this.clientRequest = clientRequest;
    }

    @Override
    public String getMessage() {
        String message = ErrorMessenger.getErrorMsg(errorCode);
        if (clientRequest != null){
            message += clientRequest;
        }
        return message;
    }
}
