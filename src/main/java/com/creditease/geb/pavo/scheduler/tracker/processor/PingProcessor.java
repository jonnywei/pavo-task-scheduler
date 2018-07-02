package com.creditease.geb.pavo.scheduler.tracker.processor;

import com.creditease.geb.pavo.scheduler.remoting.RemotingCommand;
import com.creditease.geb.pavo.scheduler.remoting.RemotingProcessor;

public class PingProcessor implements RemotingProcessor {

    @Override
    public RemotingCommand processRequest(RemotingCommand request) {
        RemotingCommand  pong = RemotingCommand.createResponseCommand(RemotingCommand.ResponeCode.P0NG.code());
        return pong;
    }
}
