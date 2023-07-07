package br.com.compassuol.pb.challenge.msproducts.framework.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        var errors = new ArrayList<String>();
        var bindingResult = e.getBindingResult();
        bindingResult.getFieldErrors().forEach(error -> errors.add(error.getDefaultMessage()));

        return ErrorMessage.builder()
                .timestamp(LocalDateTime.now())
                .errors(errors)
                .build();
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorMessage handle(BadCredentialsException e) {
        return createError(e);
    }

    @ExceptionHandler(InvalidPriceException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handle(InvalidPriceException e) {
        return createError(e);
    }

    @ExceptionHandler(JsonProcessingException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorMessage handle(JsonProcessingException e) {
        return createError(e);
    }

    @ExceptionHandler(MalformedJwtException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorMessage handle(MalformedJwtException e) {
        return createError(e);
    }

    @ExceptionHandler(InvalidTokenException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorMessage handle(InvalidTokenException e) {
        return createError(e);
    }

    @ExceptionHandler(MessageCouldNotBeSentToQueueException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handle(MessageCouldNotBeSentToQueueException e) {
        return createError(e);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handle(ResourceNotFoundException e) {
        return createError(e);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorMessage handle(EmailAlreadyExistsException e) {
        return createError(e);
    }

    private ErrorMessage createError(Exception e) {
        var errors = new ArrayList<String>();
        errors.add(e.getMessage());

        return ErrorMessage.builder()
                .timestamp(LocalDateTime.now())
                .errors(errors)
                .build();
    }
}
