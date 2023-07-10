package br.com.compassuol.pb.challenge.msproducts.framework.exception;

public class ProductAlreadyExistsException extends RuntimeException {
    public ProductAlreadyExistsException(String name) {
        super("Product with name - " + name + " is already registered");
    }

}
