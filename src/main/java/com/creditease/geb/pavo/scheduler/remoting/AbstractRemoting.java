package com.creditease.geb.pavo.scheduler.remoting;

import com.creditease.geb.pavo.scheduler.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractRemoting {

    private Logger logger = LoggerFactory.getLogger(AbstractRemoting.class);
    protected Map<Integer ,RemotingProcessor> processorTables = new HashMap<>();

    protected final ConcurrentHashMap<Integer /*opaque*/, ResponseFuture> responseTables = new ConcurrentHashMap<>();

    public void processMessageReceived(Channel channel, final RemotingCommand cmd)  {
        if (cmd != null) {
            RemotingCommand.RemotingCommandType type = RemotingCommand.RemotingCommandType.valueOf(cmd.getType());
            if (type == RemotingCommand.RemotingCommandType.REQUEST ) {
                processRequestCommand(channel, cmd);
            }else if (type == RemotingCommand.RemotingCommandType.RESPONSE ) {
                processResponseCommand(channel, cmd);
            } else {

            }
        }
    }

    public void processRequestCommand(final Channel channel, final RemotingCommand cmd) {
        logger.debug("receive request command" +cmd);
        RemotingProcessor processor = this.processorTables.get(cmd.getCode());
        RemotingCommand response =  processor.processRequest(channel,cmd);
        channel.writeAndFlush(response);
    }

    /**
     * 找到future对象，操作
     * @param channel
     * @param cmd
     */
    public void processResponseCommand(Channel channel, RemotingCommand cmd) {
        logger.debug("receive response command" +cmd);
        ResponseFuture responseFuture = this.responseTables.get(cmd.getOpaque());
        if(responseFuture != null){
            responseFuture.putResponse(cmd);
            //异步调用
            if(responseFuture.getAsyncCallback() != null){

                responseFuture.getAsyncCallback().operationComplete();
            }

        }else {
            logger.warn("receive response, but not matched any request");
        }
        this.responseTables.remove(cmd.getOpaque());

    }

    public void invokeAsyncImpl(final Channel channel, RemotingCommand request, final long timeoutMills,
                                final AsyncCallback asyncCallback) throws InterruptedException ,RemotingException{

        ResponseFuture responseFuture = new ResponseFuture(request.getId(), timeoutMills,asyncCallback);
        this.responseTables.put(request.getId(), responseFuture);

        channel.writeAndFlush(request).addListener(future -> {
            if(future.isSuccess()){
                responseFuture.setSendSuccess(true);
            }else {
                responseFuture.setSendSuccess(false);
            }
        });


    }




    public RemotingCommand invokeSyncImpl(final Channel channel, RemotingCommand request, final long timeoutMills)
            throws InterruptedException ,RemotingException  {

        try{
            ResponseFuture responseFuture = new ResponseFuture(request.getId(), timeoutMills,null);
            this.responseTables.put(request.getId(), responseFuture);

            channel.writeAndFlush(request).addListener(future -> {
                if(future.isSuccess()){
                    responseFuture.setSendSuccess(true);
                }else {
                    responseFuture.setSendSuccess(false);
                }
            });

            RemotingCommand responseCommand = responseFuture.waitResponse(timeoutMills);

            if(responseCommand == null){
                throw new RemotingException("error", responseFuture.getCause());
            }
            return responseCommand;
        }finally {
            this.responseTables.remove(request.getId());
        }

    }
}
