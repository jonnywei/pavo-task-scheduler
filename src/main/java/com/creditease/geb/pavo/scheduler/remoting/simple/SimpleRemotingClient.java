package com.creditease.geb.pavo.scheduler.remoting.simple;

import com.creditease.geb.pavo.scheduler.remoting.*;
import com.creditease.geb.pavo.scheduler.remoting.exception.RemotingException;
import com.creditease.geb.pavo.scheduler.remoting.mock.IoClient;
import com.creditease.geb.pavo.scheduler.remoting.mock.IoFuture;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

public class SimpleRemotingClient extends AbstractRemoting implements RemotingClient {

    private ConcurrentHashMap<String /* addr */, Channel> channelTables = new ConcurrentHashMap<>();


    private IoClient client;

    private String addr;

    public SimpleRemotingClient(String addr) {
        this.addr = addr;
    }

    @Override
    public void start() {
        this.client = new IoClient(new SimpleEventHandler(this), this.addr);
    }

    @Override
    public RemotingCommand invokeSync(String addr, RemotingCommand request, long timeMills)
            throws RemotingException, InterruptedException {

        final Channel channel = getAndCreateChannel(addr);
        if(channel != null && channel.isConnected()){
            return this.invokeSyncImpl(channel, request,timeMills);
        }
        return null;

    }

    @Override
    public void invokeAsync(String addr, RemotingCommand request, long timeoutMills, AsyncCallback callback)
            throws RemotingException, InterruptedException {
        final Channel channel = getAndCreateChannel(addr);
        if(channel != null &&  channel.isConnected()){
            //abstract remoting impl
              this.invokeAsyncImpl(channel, request, timeoutMills, callback);
        }else{
            // close connection

        }

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

    private Channel connect(String   remoteAddress){
        IoFuture connectFuture = this.client.connect(remoteAddress);
        return new SimpleChannel( connectFuture.getChannel());
    }
}
