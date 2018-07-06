package com.creditease.geb.pavo.scheduler.remoting.simple;

import com.creditease.geb.pavo.scheduler.remoting.*;
import com.creditease.geb.pavo.scheduler.remoting.mock.SimpleChannelRouter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

public class SimpleRemotingClient extends AbstractRemoting implements RemotingClient {

    private ConcurrentHashMap<String, Channel> channelTables = new ConcurrentHashMap<>();


    private String addr;

    public SimpleRemotingClient(String addr) {
        this.addr = addr;
    }

    @Override
    public void start() {
        SimpleChannelRouter.remotingStart(addr,this);
    }

    @Override
    public RemotingCommand invokeSync(String addr, RemotingCommand request, long timeMills) {

        final Channel channel = getAndCreateChannel(addr);
        if(channel != null && channel.isConnected()){
            Object result = channel.writeAndFlush(request);
            RemotingCommand response = (RemotingCommand)result;
            return response;
        }
        return null;

    }

    @Override
    public RemotingCommand invokeASync(String addr, RemotingCommand request, long timeoutMills, AsyncCallback callback) {
        final Channel channel = getAndCreateChannel(addr);
        if(channel != null &&  channel.isConnected()){
            //abstract remoting impl
            return this.invokeAsyncImpl(channel, request, timeoutMills, callback);
        }else{

        }
        return null;
    }

    @Override
    public void registerProcessor(RemotingProcessor processor, ExecutorService executor) {

    }

    @Override
    public void shutdown() {

    }

    private Channel getAndCreateChannel(String addr){
        Channel channel = channelTables.get(addr);
        if(channel != null && channel.isConnected()){
            return channel;
        }
        return  this.createChannel(addr);
    }

    private Channel createChannel(final String addr){
        Channel channel = channelTables.get(addr);
        if(channel != null && channel.isConnected()){
            return channel;
        }
//        String[] s = addr.split(":");
//        SocketAddress socketAddress = new InetSocketAddress(s[0], Integer.valueOf(s[1]));

        channel =connect(addr);

        channelTables.put(addr, channel);
        return channel;

    }

    private Channel connect(String  addr){
        SimpleChannel clientChannel = new SimpleChannel(this.addr, addr); //local remote
        SimpleChannel severChannel  = new SimpleChannel(addr, this.addr); // remote local
        SimpleChannelRouter.makeTunnel(clientChannel,severChannel);
        SimpleChannelRouter.addRemoting(clientChannel, this);
        AbstractRemoting serverRemoting = SimpleChannelRouter.getRemoting(addr);
        SimpleChannelRouter.addRemoting(severChannel, serverRemoting);
        return clientChannel;
    }
}
