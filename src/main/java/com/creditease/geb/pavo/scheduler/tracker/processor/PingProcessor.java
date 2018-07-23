package com.creditease.geb.pavo.scheduler.tracker.processor;

import com.creditease.geb.pavo.scheduler.remoting.Channel;
import com.creditease.geb.pavo.scheduler.remoting.RemotingCommand;
import com.creditease.geb.pavo.scheduler.remoting.RemotingProcessor;

public class PingProcessor implements RemotingProcessor {

    @Override
    public RemotingCommand processRequest(Channel channel, RemotingCommand request) {
        RemotingCommand  pong = RemotingCommand.createResponseCommand(RemotingCommand.ResponeCode.P0NG.code());
        pong.setOpaque(request.getId());
        return pong;
    }
}
