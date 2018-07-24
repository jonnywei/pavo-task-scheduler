package com.creditease.geb.pavo.scheduler.remoting.mock;

import com.creditease.geb.pavo.scheduler.remoting.Future;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class IoFuture implements Future {

    private static final long DEAD_LOCK_CHECK_INTERVAL = 5000L;

    private boolean done = false;

    private boolean success = false;

    private Throwable cause;

    private List<IoFutureListener> listeners = new ArrayList<>();

    private IoChannelImpl channel;

    private  final  Object lock = this;

    public void addListener(IoFutureListener listener){
        synchronized (lock){
            this.listeners.add(listener);
            if(isDone()){
                listener.operationComplete(this);
            }
        }
    }


    public void removeListener(IoFutureListener listener){
        this.listeners.remove(listener);
    }


    public   void notifyListeners(){

        synchronized (lock){
            this.done = true;
            for(IoFutureListener listener : listeners){
                listener.operationComplete(this);
            }
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


    public boolean awaitUninterruptibly(long timeoutMillis){
        try {
            return await0(timeoutMillis,false);
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
    }


    private boolean await0 (long timeoutMills, boolean interruptable) throws InterruptedException{
        if(isDone()){
            return  true;
        }
        if(timeoutMills <= 0 ){
            return isDone();
        }

        if(interruptable && Thread.interrupted()){
            throw new InterruptedException(toString());
        }

        long endTime = System.currentTimeMillis() +timeoutMills;

        boolean interrupted = false;

        synchronized (lock){

            if(isDone()){
                return true;
            }

            if(timeoutMills <= 0 ){
                return isDone();
            }
            try{
                for(;;){

                    long timeOut = Math.min(timeoutMills,DEAD_LOCK_CHECK_INTERVAL);
                    try{
                        lock.wait(timeOut);
                    }catch (InterruptedException e){
                        if(interruptable){
                            throw e;
                        } else {
                            interrupted  = true;
                        }
                    }
                    if(isDone()){
                        return true;
                    }
                    if(endTime < System.currentTimeMillis()){
                        return isDone();
                    }


                }
            }finally {
                if(interrupted){
                    Thread.currentThread().interrupt();
                }
            }

        }

    }

}
