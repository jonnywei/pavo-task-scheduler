package com.creditease.geb.pavo.scheduler.remoting;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractRemoting {

    protected Map<Integer ,RemotingProcessor> processorTables = new HashMap<>();

    public RemotingCommand processMessageReceived(Channel channel, final RemotingCommand cmd)  {

        RemotingProcessor processor = this.processorTables.get(cmd.getCode());
//        if (cmd != null) {
//            switch (RemotingCommandHelper.getRemotingCommandType(cmd)) {
//                case REQUEST_COMMAND:
//                    processRequestCommand(channel, cmd);
//                    break;
//                case RESPONSE_COMMAND:
//                    processResponseCommand(channel, cmd);
//                    break;
//                default:
//                    break;
//            }
//        }
        System.out.println("send pong");
        return processor.processRequest(cmd);
    }

}
