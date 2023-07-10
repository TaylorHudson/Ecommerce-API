package br.com.compassuol.pb.challenge.msproducts.framework.exception;

public class CategoryAlreadyExistsException extends RuntimeException {
    public CategoryAlreadyExistsException(String name) {
        super("Category with name - " + name + " is already registered");
    }

}
