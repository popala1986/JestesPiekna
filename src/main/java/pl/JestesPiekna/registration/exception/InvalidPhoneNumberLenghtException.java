package pl.JestesPiekna.registration.exception;

public class InvalidPhoneNumberLenghtException extends IllegalArgumentException{

    public InvalidPhoneNumberLenghtException(String message) {
        super(message);
    }
}
