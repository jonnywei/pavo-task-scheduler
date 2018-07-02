package com.creditease.geb.pavo.scheduler.remoting.simple;


import com.creditease.geb.pavo.scheduler.remoting.AbstractRemoting;
import com.creditease.geb.pavo.scheduler.remoting.Channel;
import com.creditease.geb.pavo.scheduler.remoting.RemotingCommand;

import java.util.concurrent.*;

public class SimpleChannelRouter {


    private static  ConcurrentHashMap<Channel,AbstractRemoting> REMOTING = new ConcurrentHashMap<>();

    private static  ConcurrentHashMap<String,AbstractRemoting> SERVERS = new ConcurrentHashMap<>();

    private static  ConcurrentHashMap<Channel,Channel> CHANNEL_TUNNEL = new ConcurrentHashMap<>();


    private static ExecutorService MOCK_REMOTE_EXECUTOR = Executors.newFixedThreadPool(2);

    private SimpleChannelRouter(){}


    public static void makeTunnel(Channel client, Channel server){
        CHANNEL_TUNNEL.put(client,server);
        CHANNEL_TUNNEL.put(server,client);
    }

    public static void addRemoting(Channel channel, AbstractRemoting remoting){
        REMOTING.put(channel, remoting);
    }



    public static Channel getPairChannel(Channel channel){
        return CHANNEL_TUNNEL.get(channel);
    }


    public static void remotingStart(String addr, AbstractRemoting remoting){
        SERVERS.put(addr, remoting);
    }

    public static AbstractRemoting getRemoting(String addr){
        return SERVERS.get(addr);
    }

    public static void remotingShutdown(String addr){
        SERVERS.remove(addr);
    }


    public static Future writeRemoteMsg(SimpleChannel channel, Object msg){
        Future<RemotingCommand> future = MOCK_REMOTE_EXECUTOR.submit(new Callable<RemotingCommand>() {

            @Override
            public RemotingCommand call() throws Exception {
                Thread.sleep(4000);
                Channel pairChannel = SimpleChannelRouter.getPairChannel(channel);
                AbstractRemoting remoting =  SimpleChannelRouter.getRemoting(channel.remoteAddr());
                return remoting.processMessageReceived(pairChannel, (RemotingCommand) msg);
            }
        });
        return future;

    }



}
