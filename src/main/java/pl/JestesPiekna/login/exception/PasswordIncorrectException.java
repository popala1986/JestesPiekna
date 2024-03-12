package pl.JestesPiekna.login.exception;

public class PasswordIncorrectException extends IllegalArgumentException{
    public PasswordIncorrectException(String message) {
        super(message);
    }
}
