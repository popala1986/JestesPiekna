package pl.JestesPiekna.registration.exception;

public class InvalidFirstNameException extends IllegalArgumentException{

    public InvalidFirstNameException(String message) {
        super(message);
    }
}
