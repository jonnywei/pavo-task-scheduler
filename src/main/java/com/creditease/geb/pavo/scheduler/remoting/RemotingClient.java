package com.creditease.geb.pavo.scheduler.remoting;

import java.util.concurrent.ExecutorService;

public interface RemotingClient {


    void start();


    RemotingCommand invokeSync(String addr, RemotingCommand request, final long timeMills);


    RemotingCommand invokeASync(String addr, RemotingCommand request, final long timeMills);


    void registerProcessor(final RemotingProcessor processor, ExecutorService executor);


    void shutdown();
}
