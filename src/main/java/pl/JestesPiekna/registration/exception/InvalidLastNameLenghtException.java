package pl.JestesPiekna.registration.exception;

public class InvalidLastNameLenghtException extends IllegalArgumentException{

    public InvalidLastNameLenghtException(String message) {
       super(message);
    }

}
