package pl.JestesPiekna.registration.exception;

public class InvalidUsernameException extends IllegalArgumentException {

    public InvalidUsernameException(String message) {
        super(message);
    }
}
