package co.zip.candidate.userapi.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String exception) {
        super(exception);
    }

}