package com.creditease.geb.pavo.scheduler.remoting.mock;

import com.creditease.geb.pavo.scheduler.remoting.Future;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class IoFuture implements Future {

    private boolean done = false;

    private boolean success = false;

    private Throwable cause;

    private List<IoFutureListener> listeners = new ArrayList<>();

    private IoChannelImpl channel;

    public void addListener(IoFutureListener listener){
        this.listeners.add(listener);
    }


    public void removeListener(IoFutureListener listener){
        this.listeners.remove(listener);
    }


    public   void notifyListeners(){
        for(IoFutureListener listener : listeners){
            listener.operationComplete(this);
        }
    }

    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isDone() {
        return done;
    }

    public Object get() throws InterruptedException, ExecutionException {
        return null;
    }

    public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }

    @Override
    public boolean isSuccess() {
        return success;
    }

    @Override
    public Throwable cause() {
        return cause;
    }


    public IoChannelImpl getChannel() {
        return channel;
    }

    public void setChannel(IoChannelImpl channel) {
        this.channel = channel;
    }
}
