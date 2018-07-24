package com.creditease.geb.pavo.scheduler.remoting.simple;

import com.creditease.geb.pavo.scheduler.executor.Executor;
import com.creditease.geb.pavo.scheduler.remoting.*;
import com.creditease.geb.pavo.scheduler.remoting.mock.IoServer;
import com.creditease.geb.pavo.scheduler.util.NamedThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleRemotingServer extends AbstractRemoting implements RemotingServer {

    private Logger logger = LoggerFactory.getLogger(SimpleRemotingServer.class);
    private String addr;
    private IoServer server;
    private final ExecutorService callbackExecutor;

    public SimpleRemotingServer(String addr) {

        this.addr = addr;
        this.callbackExecutor = Executors.newFixedThreadPool(2,new NamedThreadFactory("ServerCallback",true));
    }

    @Override
    public void start() {
        this.server = new IoServer(new SimpleEventHandler(this),this.addr);
        logger.debug("remoting server start. addr ={}" ,addr );
        server.bind(this.addr);
    }

    @Override
    public RemotingCommand invokeSync(final Channel channel, RemotingCommand request, long timeoutMillis) {
        return null;
    }

    @Override
    public void registerProcessor(RemotingProcessor processor, ExecutorService executor) {
        this.processorTables.put(100,processor);
    }

    @Override
    public void shutdown() {
        this.server.shutdown();

    }

    @Override
    protected ExecutorService getCallbackExecutor() {
        return callbackExecutor;
    }
}
