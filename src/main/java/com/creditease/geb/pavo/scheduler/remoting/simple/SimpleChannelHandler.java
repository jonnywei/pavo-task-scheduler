package com.creditease.geb.pavo.scheduler.remoting.simple;

import com.creditease.geb.pavo.scheduler.remoting.ChannelHandler;
import com.creditease.geb.pavo.scheduler.remoting.ChannelHandlerListener;
import com.creditease.geb.pavo.scheduler.remoting.Future;
import com.creditease.geb.pavo.scheduler.remoting.mock.IoFuture;
import com.creditease.geb.pavo.scheduler.remoting.mock.IoFutureListener;


public class SimpleChannelHandler implements ChannelHandler {

    private IoFuture future;

    public SimpleChannelHandler( IoFuture future ) {
        this.future = future;
    }

    @Override
    public ChannelHandler addListener(ChannelHandlerListener listener) {
        future.addListener(new IoFutureListener() {
            @Override
            public void operationComplete(Future future) {
                listener.operationComplete(future);
            }
        });
        return this;
    }
}
