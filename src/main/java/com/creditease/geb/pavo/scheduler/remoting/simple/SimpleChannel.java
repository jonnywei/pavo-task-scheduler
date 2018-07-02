package com.creditease.geb.pavo.scheduler.remoting.simple;

import com.creditease.geb.pavo.scheduler.remoting.AbstractRemoting;
import com.creditease.geb.pavo.scheduler.remoting.Channel;
import com.creditease.geb.pavo.scheduler.remoting.RemotingCommand;

import java.net.SocketAddress;

public class SimpleChannel implements Channel {

    private boolean closed ;

    private String addr;


    public SimpleChannel(String addr) {
        this.addr = addr;
        this.closed = false;
    }

    @Override
    public SocketAddress localAddress() {
        return null;
    }

    @Override
    public SocketAddress remoteAddress() {
        return null;
    }

    @Override
    public Object writeAndFlush(Object msg) {
        Channel pairChannel = SimpleChannelRouter.getPairChannel(this);
        AbstractRemoting remoting =  SimpleChannelRouter.getServer(addr);
        return remoting.processMessageReceived(pairChannel, (RemotingCommand) msg);
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
