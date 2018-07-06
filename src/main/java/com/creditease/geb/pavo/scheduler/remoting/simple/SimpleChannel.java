package com.creditease.geb.pavo.scheduler.remoting.simple;

import com.creditease.geb.pavo.scheduler.remoting.*;
import com.creditease.geb.pavo.scheduler.remoting.mock.SimpleChannelRouter;

import java.net.SocketAddress;
import java.util.concurrent.Future;

public class SimpleChannel implements Channel {

    private boolean closed ;

    private String localAddr;

    private String remoteAddr;



    public SimpleChannel(String addr,String remoteAddr) {
        this.localAddr = addr;
        this.remoteAddr = remoteAddr;
        this.closed = false;
    }

    @Override
    public SocketAddress localAddress() {
        return null;
    }

    @Override
    public String localAddr() {
        return this.localAddr;
    }

    @Override
    public SocketAddress remoteAddress() {
        return null;
    }

    @Override
    public String remoteAddr() {
        return this.remoteAddr;
    }

    @Override
    public Object writeAndFlush(Object msg) {
        Channel pairChannel = SimpleChannelRouter.getPairChannel(this);
        AbstractRemoting remoting =  SimpleChannelRouter.getRemoting(this.remoteAddr);
        return remoting.processMessageReceived(pairChannel, (RemotingCommand) msg);
    }

    @Override
    public ChannelHandler asyncWriteAndFlush(Object msg) {
        Future<RemotingCommand> future  = null;//  SimpleChannelRouter.writeRemoteMsg(this, msg);
        return new SimpleChannelHandler(future);
    }

    @Override
    public void close() {
        closed = true;
    }

    @Override
    public boolean isConnected() {
        return !closed;
    }

    @Override
    public boolean isOpen() {
        return !closed;
    }

    @Override
    public boolean isClosed() {
        return closed;
    }
}
