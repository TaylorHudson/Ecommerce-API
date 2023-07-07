package br.com.compassuol.pb.challenge.msproducts.framework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handle(Exception e) {
        return e.getMessage();
    }

    @ExceptionHandler(MessageCouldNotBeSentToQueueException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handle(MessageCouldNotBeSentToQueueException e) {
        return e.getMessage();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handle(ResourceNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handle(EmailAlreadyExistsException e) {
        return e.getMessage();
    }

}
