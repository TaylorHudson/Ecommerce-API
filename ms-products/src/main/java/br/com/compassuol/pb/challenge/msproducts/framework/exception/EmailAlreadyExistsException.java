package br.com.compassuol.pb.challenge.msproducts.framework.exception;

public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException(String email) {
        super("Email - " + email + " is already registered");
    }

}
