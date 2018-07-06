package com.creditease.geb.pavo.scheduler.remoting.mock.nio;


import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MockSocketChannelRouter {


    private static  ConcurrentHashMap<MockServerSocketChannel,Set<MockSocketChannel>> CLIENT_SOCKET_CHANNEL = new ConcurrentHashMap<>();

    private static  ConcurrentHashMap<String,MockServerSocketChannel> SERVERS = new ConcurrentHashMap<>();

    private static  ConcurrentHashMap<MockSocketChannel,MockSocketChannel> CHANNEL_TUNNEL = new ConcurrentHashMap<>();



    private MockSocketChannelRouter(){}


    public static void makeTunnel(MockSocketChannel client, MockSocketChannel server){
        CHANNEL_TUNNEL.put(client,server);
        CHANNEL_TUNNEL.put(server,client);
    }

    public static void addClientSocketChannel(MockServerSocketChannel serverSocketChannel, MockSocketChannel clientSocketChannel){
        Set<MockSocketChannel> set = CLIENT_SOCKET_CHANNEL.get(serverSocketChannel);
        if(set == null){
            set = new HashSet<>();
            CLIENT_SOCKET_CHANNEL.put(serverSocketChannel,set);
        }
        set.add(clientSocketChannel);
    }

    public static MockSocketChannel getClientSocketChannel(MockServerSocketChannel serverSocketChannel, String clientAddress){
        Set<MockSocketChannel> set = CLIENT_SOCKET_CHANNEL.get(serverSocketChannel);
        for(MockSocketChannel socketChannel : set){
            if(socketChannel.getRemoteAddress().equals(clientAddress)){
                return socketChannel;
            }
        }
        return null;
    }


    public static MockSocketChannel getPairChannel(MockSocketChannel channel){
        return CHANNEL_TUNNEL.get(channel);
    }


    public static void bind(String addr, MockServerSocketChannel serverSocketChannel){
        SERVERS.put(addr, serverSocketChannel);
    }

    public static MockServerSocketChannel getServerSocketChannel(String addr){
        return SERVERS.get(addr);
    }

    public static void serverShutdown(String addr){
        SERVERS.remove(addr);
    }


}
