package co.zip.candidate.userapi.exception;

public class UserEmailAlreadyExistsException  extends RuntimeException{
    public UserEmailAlreadyExistsException(String exception) {
        super(exception);
    }
}
