package com.creditease.geb.pavo.scheduler.remoting.mock;

import com.creditease.geb.pavo.scheduler.remoting.mock.nio.SelectableChannel;
import com.creditease.geb.pavo.scheduler.remoting.mock.processor.IoProcessor;

public class IoChannelImpl implements IoChannel {

    protected SelectableChannel channel; //引用底层的channel

    private IoProcessor processor; //应用当前的processor

    private IoHandler eventHandler;


    public IoChannelImpl(SelectableChannel channel, IoProcessor processor, IoHandler eventHandler) {
        this.channel = channel;
        this.processor = processor;
        this.eventHandler = eventHandler;
    }

    @Override
    public String remoteAddress() {
        return null;
    }

    @Override
    public String localAddress() {
        return null;
    }

    @Override
    public SelectableChannel socketChannel() {
        return channel;
    }

    @Override
    public IoFuture writeAndFlush(Object msg) {
        return processor.writeAndFlush(this,msg);
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public boolean isOpen() {
        return false;
    }

    @Override
    public boolean isClosed() {
        return false;
    }
}
