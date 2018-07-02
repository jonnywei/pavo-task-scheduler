package com.creditease.geb.pavo.scheduler.remoting;

public class ResponseFuture {

    private int opaque;

    private long timeoutMills;

    private AsyncCallback asyncCallback;

    private volatile  boolean sendSuccess;

    private volatile RemotingCommand responseCommand;


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


    public void putResponse(RemotingCommand responseCommand){
        this.responseCommand = responseCommand;
    }

    public RemotingCommand waitResponse(long timeoutMills){

        return responseCommand;
    }

}
