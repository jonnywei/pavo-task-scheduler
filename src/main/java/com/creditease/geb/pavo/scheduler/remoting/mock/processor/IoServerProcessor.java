package com.creditease.geb.pavo.scheduler.remoting.mock.processor;

import com.creditease.geb.pavo.scheduler.remoting.mock.IoChannel;
import com.creditease.geb.pavo.scheduler.remoting.mock.IoChannelImpl;
import com.creditease.geb.pavo.scheduler.remoting.mock.IoFuture;
import com.creditease.geb.pavo.scheduler.remoting.mock.IoHandler;
import com.creditease.geb.pavo.scheduler.remoting.mock.nio.MockServerSocketChannel;
import com.creditease.geb.pavo.scheduler.remoting.mock.nio.SelectableChannel;
import com.creditease.geb.pavo.scheduler.remoting.mock.nio.SelectionKey;

public class IoServerProcessor extends AbstractProcessor {

    //一个server一个
    private MockServerSocketChannel serverSocketChannel;


    String localAddress;


    public IoServerProcessor(IoHandler eventHandler,String addr) {
        super(eventHandler);
        this.localAddress = addr;
        this.serverSocketChannel = MockServerSocketChannel.open(addr);
    }

    public void bind(String localAddress){
        serverSocketChannel.bind(localAddress);
    }

    @Override
    protected IoChannel doAccept(SelectionKey selectionKey) {
        SelectableChannel socketChannel = serverSocketChannel.accept( );
        IoChannel ioChannel = new IoChannelImpl(socketChannel, this, eventHandler);
        socketChannel.register(selectionKey.selector(), ioChannel,SelectionKey.OP_READ);
        return ioChannel;
    }

    @Override
    protected IoChannel doConnect(String remoteAddress, IoFuture ioFuture) {
        throw  new UnsupportedOperationException();
    }

    @Override
    public void connect(SelectionKey key) {
            throw  new UnsupportedOperationException();
    }


    public void register(){
        this.serverSocketChannel.register(eventHandleLoop.selector(),null,SelectionKey.OP_ACCEPT);
    }
}
