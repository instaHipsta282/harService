package com.instahipsta.harCRUD.model.exception;

public enum ExceptionMessage {
    INTERNAL_SERVER_ERROR_C("Something went wrong"),
    JSON_VALIDATE_FAILED_EXCEPTION("There are no following elements in json: "),
    RESOURCE_NOT_FOUND_EXCEPTION("There is no such har with id ");

    private String errorMessage;

    ExceptionMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
