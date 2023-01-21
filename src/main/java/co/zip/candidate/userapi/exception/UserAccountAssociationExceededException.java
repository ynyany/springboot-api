package co.zip.candidate.userapi.exception;

public class UserAccountAssociationExceededException extends RuntimeException {

    public UserAccountAssociationExceededException(String exception) {
        super(exception);
    }

}