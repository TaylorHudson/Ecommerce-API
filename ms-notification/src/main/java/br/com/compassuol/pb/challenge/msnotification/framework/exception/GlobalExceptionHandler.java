package br.com.compassuol.pb.challenge.msnotification.framework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MessageCouldNotBeSentToQueueException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handle(MessageCouldNotBeSentToQueueException e) {
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
