package com.creditease.geb.pavo.scheduler.remoting.simple;


import com.creditease.geb.pavo.scheduler.remoting.AbstractRemoting;
import com.creditease.geb.pavo.scheduler.remoting.Channel;

import java.util.concurrent.ConcurrentHashMap;

public class SimpleChannelRouter {


    private static  ConcurrentHashMap<Channel,AbstractRemoting> REMOTING = new ConcurrentHashMap<>();

    private static  ConcurrentHashMap<String,AbstractRemoting> SERVERS = new ConcurrentHashMap<>();

    private static  ConcurrentHashMap<Channel,Channel> CHANNEL_TUNNEL = new ConcurrentHashMap<>();



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


    public static void serverStart(String addr, AbstractRemoting server){
        SERVERS.put(addr, server);
    }

    public static AbstractRemoting getServer(String addr){
        return SERVERS.get(addr);
    }

    public static void serverShutdown(String addr){
        SERVERS.remove(addr);
    }


}
