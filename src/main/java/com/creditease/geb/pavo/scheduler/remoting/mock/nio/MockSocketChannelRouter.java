package com.creditease.geb.pavo.scheduler.remoting.mock.nio;


import com.creditease.geb.pavo.scheduler.remoting.RemotingCommand;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

public class MockSocketChannelRouter {

    private static  ConcurrentHashMap<MockServerSocketChannel,Set<MockSocketChannel>> CLIENT_SOCKET_CHANNEL = new ConcurrentHashMap<>();

    private static  ConcurrentHashMap<String,MockServerSocketChannel> SERVERS = new ConcurrentHashMap<>();

    private static  ConcurrentHashMap<MockSocketChannel,MockSocketChannel> CHANNEL_TUNNEL = new ConcurrentHashMap<>();

    private static ConcurrentHashMap<MockSocketChannel, BlockingQueue<RemotingCommand>> RECEIVE_QUEUE_MAP = new ConcurrentHashMap<>();

    private MockSocketChannelRouter(){}

    public static void makeTunnel(MockSocketChannel client, MockSocketChannel server){
        CHANNEL_TUNNEL.put(client,server);
        CHANNEL_TUNNEL.put(server,client);
        RECEIVE_QUEUE_MAP.put(client,new LinkedBlockingQueue<RemotingCommand>());
        RECEIVE_QUEUE_MAP.put(server,new LinkedBlockingQueue<RemotingCommand>());
        client.keyFor().setReadyOps(new int[]{SelectionKey.OP_CONNECT});
    }

    public static void addClientSocketChannel(MockServerSocketChannel serverSocketChannel, MockSocketChannel clientSocketChannel){
        Set<MockSocketChannel> set = CLIENT_SOCKET_CHANNEL.get(serverSocketChannel);
        if(set == null){
            set = new HashSet<>();
            CLIENT_SOCKET_CHANNEL.put(serverSocketChannel,set);
        }
        serverSocketChannel.keyFor().setReadyOps(new int[]{SelectionKey.OP_ACCEPT});
        clientSocketChannel.keyFor().setReadyOps(new int[]{SelectionKey.OP_CONNECT});
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


    public static void bind(String addr, MockServerSocketChannel serverSocketChannel){
        SERVERS.put(addr, serverSocketChannel);
    }

    public static MockServerSocketChannel getServerSocketChannel(String addr){
        return SERVERS.get(addr);
    }

    public static void serverShutdown(String addr){
        SERVERS.remove(addr);
    }



    public static int write(SelectableChannel socketChannel, RemotingCommand msg){

        MockSocketChannel pairChannel =   CHANNEL_TUNNEL.get(socketChannel);
        pairChannel.keyFor().setReadyOps(new int[]{SelectionKey.OP_READ});
        RECEIVE_QUEUE_MAP.get(pairChannel).add(msg);
        return 1;

    }


    public static List<RemotingCommand> read(SelectableChannel socketChannel ){
        List<RemotingCommand> result = new ArrayList<>();
        BlockingQueue<RemotingCommand> queue = RECEIVE_QUEUE_MAP.get(socketChannel);
        while (!queue.isEmpty()){
            result.add(queue.poll());
        }
        return result;
    }
}
