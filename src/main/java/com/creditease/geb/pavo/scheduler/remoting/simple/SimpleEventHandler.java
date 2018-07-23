package com.creditease.geb.pavo.scheduler.remoting.simple;

import com.creditease.geb.pavo.scheduler.remoting.AbstractRemoting;
import com.creditease.geb.pavo.scheduler.remoting.RemotingCommand;
import com.creditease.geb.pavo.scheduler.remoting.mock.IoChannel;
import com.creditease.geb.pavo.scheduler.remoting.mock.IoHandler;

public class SimpleEventHandler implements IoHandler {

    private AbstractRemoting remoting;


    public SimpleEventHandler(AbstractRemoting remoting) {
        this.remoting = remoting;
    }

    @Override
    public void messageReceived(IoChannel channel, Object msg) {
        if(msg != null && msg instanceof RemotingCommand){
            this.remoting.processMessageReceived(new SimpleChannel(channel),(RemotingCommand) msg);
        }
    }
}
