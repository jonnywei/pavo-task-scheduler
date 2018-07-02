package com.creditease.geb.pavo.scheduler.remoting.simple;

import com.creditease.geb.pavo.scheduler.remoting.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;

public class SimpleRemotingServer extends AbstractRemoting implements RemotingServer {

    private Logger logger = LoggerFactory.getLogger(SimpleRemotingServer.class);
    private String addr;

    public SimpleRemotingServer(String addr) {

        this.addr = addr;
    }

    @Override
    public void start() {
        logger.debug("remoting server start. addr ={}" ,addr );
        SimpleChannelRouter.serverStart(addr,this);
    }

    @Override
    public RemotingCommand invokeSync(RemotingCommand request, long timeoutMillis) {
        return null;
    }

    @Override
    public void registerProcessor(RemotingProcessor processor, ExecutorService executor) {
        this.processorTables.put(100,processor);
    }

    @Override
    public void shutdown() {
        SimpleChannelRouter.serverShutdown(this.addr);

    }

}
