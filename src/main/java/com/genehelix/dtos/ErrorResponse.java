package com.genehelix.dtos;

public class ErrorResponse {
    private String errorStatus;
    private String errorMessage;
    private String timeLapse;

    public ErrorResponse() {
        super();
    }

    public String getErrorStatus() {
        return errorStatus;
    }

    public void setErrorStatus(String errorStatus) {
        this.errorStatus = errorStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getTimeLapse() {
        return timeLapse;
    }

    public void setTimeLapse(String timeLapse) {
        this.timeLapse = timeLapse;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
