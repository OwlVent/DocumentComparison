package com.compare.documentcomparison.exception_handler;

public enum ErrorType {
    FILE_IS_EMPTY("One of the files empty. Please check data of files"),
    NO_DATA("The file does not contain any data or has only one data row"),
    UNKNOWN_ERROR("An unexpected error has occurred. Please try again later");

    private final String message;

    ErrorType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
