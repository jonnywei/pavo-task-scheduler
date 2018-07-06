package com.creditease.geb.pavo.scheduler.remoting.mock.nio;

public class MockServerSocketChannel extends SelectableChannel {

    private MockServerSocketChannel(String localAddress) {
        super(localAddress);
    }

    public static MockServerSocketChannel open(String localAddress){
        return new MockServerSocketChannel(localAddress);
    }


    public MockSocketChannel accept(){
        MockSocketChannel socketChannel = new MockSocketChannel(localAddress);
        MockSocketChannel client =   MockSocketChannelRouter.getClientSocketChannel(this,socketChannel.getLocalAddress());
        MockSocketChannelRouter.makeTunnel(client,socketChannel);
        return socketChannel;
    }

    public void bind(String localAddress){
        MockSocketChannelRouter.bind(localAddress,this);
    }
}
