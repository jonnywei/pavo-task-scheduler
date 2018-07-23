package com.creditease.geb.pavo.scheduler.remoting;

import com.creditease.geb.pavo.scheduler.remoting.exception.RemotingException;

import java.util.concurrent.ExecutorService;

public interface RemotingClient {


    void start();


    RemotingCommand invokeSync(String addr, RemotingCommand request, final long timeoutMills)
            throws InterruptedException, RemotingException;

    /**
     * 异步调用
     * @param addr
     * @param request
     * @param timeoutMills
     * @param callback
     * @return
     */
    void invokeAsync(String addr, RemotingCommand request, final long timeoutMills, AsyncCallback callback)
            throws InterruptedException, RemotingException;


    void registerProcessor(final RemotingProcessor processor, ExecutorService executor);


    void shutdown();
}
