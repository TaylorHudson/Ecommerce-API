package br.com.compassuol.pb.challenge.msauth.framework.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException(String email) {
        super("Email - " + email + " is already registered");
    }

}
