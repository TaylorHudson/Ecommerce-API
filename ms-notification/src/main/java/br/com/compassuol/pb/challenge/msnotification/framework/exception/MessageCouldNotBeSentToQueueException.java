package br.com.compassuol.pb.challenge.msnotification.framework.exception;

public class MessageCouldNotBeSentToQueueException extends RuntimeException {

    public MessageCouldNotBeSentToQueueException() {
        super("Error during sending message to message queue");
    }

}
