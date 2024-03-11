package pl.JestesPiekna.registration.exception;

public class InvalidEmailException extends IllegalArgumentException{

    public InvalidEmailException(String message) {
        super(message);
    }
}
