package br.com.compassuol.pb.challenge.msproducts.framework.exception;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String resource, String name, String value) {
        super("Resource " + resource + " field " + name + " - " + value + " not found!");
    }

}
