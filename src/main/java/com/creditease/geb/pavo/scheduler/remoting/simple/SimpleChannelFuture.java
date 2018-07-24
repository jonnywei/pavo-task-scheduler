package com.creditease.geb.pavo.scheduler.remoting.simple;

import com.creditease.geb.pavo.scheduler.remoting.Channel;
import com.creditease.geb.pavo.scheduler.remoting.ChannelFuture;
import com.creditease.geb.pavo.scheduler.remoting.mock.IoFuture;

public class SimpleChannelFuture implements ChannelFuture {

    private IoFuture ioFuture;

    public SimpleChannelFuture(IoFuture ioFuture) {
        this.ioFuture = ioFuture;
    }

    @Override
    public boolean isConnected() {
        return ioFuture.getChannel().isConnected();
    }

    @Override
    public boolean awaitUninterruptibly(long timeoutMills) {
        return ioFuture.awaitUninterruptibly(timeoutMills);
    }

    @Override
    public Channel getChannel() {
        return  new SimpleChannel(ioFuture.getChannel());
    }

    @Override
    public boolean isDone() {
        return ioFuture.isDone();
    }

    @Override
    public Throwable cause() {
        return ioFuture.cause();
    }
}
