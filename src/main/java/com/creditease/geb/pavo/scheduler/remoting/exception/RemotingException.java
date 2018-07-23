package com.creditease.geb.pavo.scheduler.remoting.exception;

public class RemotingException extends Exception {


    private static final long serialVersionUID = 6430099750505304659L;

    public RemotingException(String message) {
        super(message);
    }

    public RemotingException(String message, Throwable cause) {
        super(message, cause);
    }
}
