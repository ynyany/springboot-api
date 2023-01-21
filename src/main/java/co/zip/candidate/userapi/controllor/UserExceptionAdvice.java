package co.zip.candidate.userapi.controllor;

import co.zip.candidate.userapi.exception.UserEmailAlreadyExistsException;
import co.zip.candidate.userapi.exception.UserIncomeNotQualitiedException;
import co.zip.candidate.userapi.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UserExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String UserNotFoundHandler(UserNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(UserIncomeNotQualitiedException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    String UserNotQualified(UserIncomeNotQualitiedException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(UserEmailAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String DuplicatedEmail(UserEmailAlreadyExistsException ex) {
        return ex.getMessage();
    }
}
