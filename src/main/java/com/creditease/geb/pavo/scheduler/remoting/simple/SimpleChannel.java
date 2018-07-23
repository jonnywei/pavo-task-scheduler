package com.creditease.geb.pavo.scheduler.remoting.simple;

import com.creditease.geb.pavo.scheduler.remoting.Channel;
import com.creditease.geb.pavo.scheduler.remoting.ChannelHandler;
import com.creditease.geb.pavo.scheduler.remoting.mock.IoChannel;

import java.net.SocketAddress;

public class SimpleChannel implements Channel {

    private IoChannel ioChannel;


    public SimpleChannel(IoChannel ioChannel) {
        this.ioChannel = ioChannel;
    }

    @Override
    public SocketAddress localAddress() {
        return null;
    }

    @Override
    public String localAddr() {
        return this.ioChannel.localAddress();
    }

    @Override
    public SocketAddress remoteAddress() {
        return null;
    }

    @Override
    public String remoteAddr() {
        return this.ioChannel.remoteAddress();
    }


    @Override
    public ChannelHandler writeAndFlush(Object msg) {
        return new SimpleChannelHandler(this.ioChannel.writeAndFlush(msg));
    }

    @Override
    public void close() {
        this.ioChannel.close();
    }

    @Override
    public boolean isConnected() {
        return this.ioChannel.isConnected();
    }

    @Override
    public boolean isOpen() {
        return this.ioChannel.isOpen();
    }

    @Override
    public boolean isClosed() {
        return this.ioChannel.isClosed();
    }
}
