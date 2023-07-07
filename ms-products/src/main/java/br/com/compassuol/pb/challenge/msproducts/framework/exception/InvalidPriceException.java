package br.com.compassuol.pb.challenge.msproducts.framework.exception;

public class InvalidPriceException extends RuntimeException {
    public InvalidPriceException() {
        super("The price received is invalid");
    }
}
