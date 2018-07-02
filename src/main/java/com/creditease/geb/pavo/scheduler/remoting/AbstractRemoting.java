package com.creditease.geb.pavo.scheduler.remoting;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractRemoting {

    protected Map<Integer ,RemotingProcessor> processorTables = new HashMap<>();

    public RemotingCommand processMessageReceived(Channel channel, final RemotingCommand cmd)  {
        if (cmd != null) {
            RemotingCommand.RemotingCommandType type = RemotingCommand.RemotingCommandType.valueOf(cmd.getType());
            if (type == RemotingCommand.RemotingCommandType.REQUEST ) {
               return processRequestCommand(channel, cmd);
            }else if (type == RemotingCommand.RemotingCommandType.RESPONSE ) {
                processResponseCommand(channel, cmd);
            } else {

            }
        }
        return null;
    }

    public RemotingCommand processRequestCommand(final Channel channel, final RemotingCommand cmd) {
        RemotingProcessor processor = this.processorTables.get(cmd.getCode());
        return processor.processRequest(cmd);
    }

    public void processResponseCommand(Channel channel, RemotingCommand cmd) {

    }

    public RemotingCommand invokeAsyncImpl(final Channel channel, RemotingCommand request, final long timeoutMills, final AsyncCallback asyncCallback){

        ResponseFuture responseFuture = new ResponseFuture(request.getId(), timeoutMills,asyncCallback);
        channel.asyncWriteAndFlush(request).addListener(future -> {
            if(future.isSuccess()){
                responseFuture.setSendSuccess(true);
            }else {
                responseFuture.setSendSuccess(false);
            }
        });

        RemotingCommand responseCommand = responseFuture.waitResponse(timeoutMills);

        return responseCommand;
    }

}
