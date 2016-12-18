package de.bluemx.stocktool.annotations;

public class ValidationException extends RuntimeException {

    private Validate validator;
    private String actual;

    public ValidationException(String message, Validate validator, String actual) {
        super(message);
        this.actual = actual;
        this.validator = validator;
    }


    public Validate getValidator() {
        return validator;
    }

    public String getActual() {
        return actual;
    }
}
