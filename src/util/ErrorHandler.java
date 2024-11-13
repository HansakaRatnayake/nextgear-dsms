package util;

public class ErrorHandler {
        private StringBuilder errors;
        private Boolean status;

    public ErrorHandler() {
    }

    public ErrorHandler(StringBuilder errors, Boolean status) {
        this.errors = errors;
        this.status = status;
    }

    public StringBuilder getErrors() {
        return errors;
    }

    public void setErrors(StringBuilder errors) {
        this.errors = errors;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
