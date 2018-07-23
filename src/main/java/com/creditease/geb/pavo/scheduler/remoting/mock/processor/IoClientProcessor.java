package com.creditease.geb.pavo.scheduler.remoting.mock.processor;

import com.creditease.geb.pavo.scheduler.remoting.mock.IoChannel;
import com.creditease.geb.pavo.scheduler.remoting.mock.IoChannelImpl;
import com.creditease.geb.pavo.scheduler.remoting.mock.IoFuture;
import com.creditease.geb.pavo.scheduler.remoting.mock.IoHandler;
import com.creditease.geb.pavo.scheduler.remoting.mock.nio.MockSocketChannel;
import com.creditease.geb.pavo.scheduler.remoting.mock.nio.SelectionKey;

public class IoClientProcessor extends AbstractProcessor{


    String localAddress;

    public IoClientProcessor(IoHandler eventHandler,String localAddress) {
        super(eventHandler);
        this.localAddress = localAddress;
    }

    @Override
    protected IoChannel doAccept(SelectionKey selectionKey) {
        throw  new UnsupportedOperationException();
    }

    @Override
    protected IoChannel doConnect(String remoteAddress,IoFuture ioFuture) {
        //每次连接建立一个
        MockSocketChannel socketChannel = MockSocketChannel.open(localAddress);

        IoChannelImpl ioChannel = new IoChannelImpl(socketChannel, this,eventHandler);

        socketChannel.register(this.eventHandleLoop.selector(),ioChannel,SelectionKey.OP_CONNECT);

        socketChannel.connect(remoteAddress);


        ioFuture.setSuccess(true);
        ioFuture.setChannel(ioChannel);
        ioFuture.notifyListeners();
        return ioChannel;
    }


    @Override
    public void connect(SelectionKey key) {

        key.setInterestOps(new int[] {SelectionKey.OP_READ});
    }
}
