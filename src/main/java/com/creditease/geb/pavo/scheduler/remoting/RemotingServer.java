package com.creditease.geb.pavo.scheduler.remoting;

import java.util.concurrent.ExecutorService;

public interface RemotingServer {

    void start();

    RemotingCommand invokeSync( final Channel channel, final RemotingCommand request, final long timeoutMillis);

    void registerProcessor(final RemotingProcessor processor, final ExecutorService executor);

    void shutdown();

}
