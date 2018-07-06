package com.creditease.geb.pavo.scheduler.remoting.mock;

import com.creditease.geb.pavo.scheduler.remoting.RemotingCommand;

public class WriteRequest {

    private IoFuture future;

    private Object command;


    public WriteRequest(IoFuture future, Object command) {
        this.future = future;
        this.command = command;
    }

    public IoFuture getFuture() {
        return future;
    }

    public void setFuture(IoFuture future) {
        this.future = future;
    }

    public Object getCommand() {
        return command;
    }

    public void setCommand(RemotingCommand command) {
        this.command = command;
    }
}
