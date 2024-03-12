package pl.JestesPiekna.registration.exception;

public class PhoneNumberAlreadyExistsException extends RuntimeException{

    public PhoneNumberAlreadyExistsException(String message) {
        super(message);
    }
}
