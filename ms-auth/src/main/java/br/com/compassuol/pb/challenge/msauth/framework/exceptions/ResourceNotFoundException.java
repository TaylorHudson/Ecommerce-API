package br.com.compassuol.pb.challenge.msauth.framework.exceptions;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String resource, String name, String value) {
        super("Resource " + resource + " field " + name + " - " + value + " not found!");
    }

}
