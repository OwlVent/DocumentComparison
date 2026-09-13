package com.compare.documentcomparison.exceptionhandler;

public class BusinessException extends RuntimeException {
    private final ErrorType errorType;

    public BusinessException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }
}
