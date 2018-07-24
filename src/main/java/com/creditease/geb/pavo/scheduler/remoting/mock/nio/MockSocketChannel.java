package com.creditease.geb.pavo.scheduler.remoting.mock.nio;

import com.creditease.geb.pavo.scheduler.remoting.mock.IoFuture;

import java.io.IOException;

public class MockSocketChannel extends SelectableChannel {

    private String remoteAddress;

    protected MockSocketChannel(String localAddress) {
        super(localAddress);
    }

    public static MockSocketChannel open(String localAddress){
        return new MockSocketChannel(localAddress);
    }

    public String getRemoteAddress(){
        return  this.remoteAddress;
    }

    public void connect(String remoteAddress){
        this.remoteAddress = remoteAddress;
        MockServerSocketChannel serverSocketChannel = MockSocketChannelRouter.getServerSocketChannel(remoteAddress);
        MockSocketChannelRouter.addClientSocketChannel(serverSocketChannel, this);
    }

    public void connect(String remoteAddress, IoFuture ioFuture){
        this.remoteAddress = remoteAddress;
        MockServerSocketChannel serverSocketChannel = MockSocketChannelRouter.getServerSocketChannel(remoteAddress);
        MockSocketChannelRouter.addClientSocketChannel(serverSocketChannel, this,ioFuture);
    }
}
