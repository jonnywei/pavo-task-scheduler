package com.creditease.geb.pavo.scheduler.remoting;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ResponseFuture {

    private int opaque;

    private long timeoutMills;

    private AsyncCallback asyncCallback;

    private volatile  boolean sendSuccess;

    private volatile RemotingCommand responseCommand;

    private volatile Throwable cause;


    private final CountDownLatch latch = new CountDownLatch(1);


    public ResponseFuture(int opaque, long timeoutMills, AsyncCallback asyncCallback) {
        this.opaque = opaque;
        this.timeoutMills = timeoutMills;
        this.asyncCallback = asyncCallback;

    }

    public int getOpaque() {
        return opaque;
    }

    public void setOpaque(int opaque) {
        this.opaque = opaque;
    }

    public long getTimeoutMills() {
        return timeoutMills;
    }

    public void setTimeoutMills(long timeoutMills) {
        this.timeoutMills = timeoutMills;
    }

    public AsyncCallback getAsyncCallback() {
        return asyncCallback;
    }

    public void setAsyncCallback(AsyncCallback asyncCallback) {
        this.asyncCallback = asyncCallback;
    }


    public boolean isSendSuccess() {
        return sendSuccess;
    }

    public void setSendSuccess(boolean sendSuccess) {
        this.sendSuccess = sendSuccess;
    }

    public Throwable getCause() {
        return cause;
    }

    public void setCause(Throwable cause) {
        this.cause = cause;
    }

    public void putResponse(RemotingCommand responseCommand){
        this.responseCommand = responseCommand;
        this.latch.countDown();
    }

    public RemotingCommand waitResponse(long timeoutMills) throws InterruptedException {
        this.latch.await(timeoutMills,TimeUnit.MILLISECONDS);
        return this.responseCommand;
    }

    //执行异步调用
    public void executeInvokeCallback(){
        if(this.asyncCallback != null){
            asyncCallback.operationComplete(this);
        }
    }
}
