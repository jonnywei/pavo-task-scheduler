package com.creditease.geb.pavo.scheduler.remoting.simple;

import com.creditease.geb.pavo.scheduler.remoting.*;
import com.creditease.geb.pavo.scheduler.remoting.mock.IoServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;

public class SimpleRemotingServer extends AbstractRemoting implements RemotingServer {

    private Logger logger = LoggerFactory.getLogger(SimpleRemotingServer.class);
    private String addr;
    private IoServer server;

    public SimpleRemotingServer(String addr) {

        this.addr = addr;
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

}
