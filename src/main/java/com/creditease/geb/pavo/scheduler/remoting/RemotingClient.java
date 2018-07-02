package com.creditease.geb.pavo.scheduler.remoting;

import java.util.concurrent.ExecutorService;

public interface RemotingClient {


    void start();


    RemotingCommand invokeSync(String addr, RemotingCommand request, final long timeoutMills);

    /**
     * 异步调用
     * @param addr
     * @param request
     * @param timeoutMills
     * @param callback
     * @return
     */
    RemotingCommand invokeASync(String addr, RemotingCommand request, final long timeoutMills, AysncCallback callback);


    void registerProcessor(final RemotingProcessor processor, ExecutorService executor);


    void shutdown();
}
