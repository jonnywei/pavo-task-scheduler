package com.creditease.geb.pavo.scheduler.remoting.simple;

import com.creditease.geb.pavo.scheduler.remoting.ChannelHandler;
import com.creditease.geb.pavo.scheduler.remoting.ChannelHandlerListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

public class SimpleChannelHandler implements ChannelHandler {

    private Future future;

    public SimpleChannelHandler( Future future ) {
        this.future = future;
    }

    @Override
    public ChannelHandler addListener(ChannelHandlerListener listener) {

        return this;
    }
}
