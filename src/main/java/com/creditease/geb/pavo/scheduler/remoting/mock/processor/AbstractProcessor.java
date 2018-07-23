package com.creditease.geb.pavo.scheduler.remoting.mock.processor;

import com.creditease.geb.pavo.scheduler.remoting.RemotingCommand;
import com.creditease.geb.pavo.scheduler.remoting.mock.IoChannel;
import com.creditease.geb.pavo.scheduler.remoting.mock.IoFuture;
import com.creditease.geb.pavo.scheduler.remoting.mock.IoHandler;
import com.creditease.geb.pavo.scheduler.remoting.mock.WriteRequest;
import com.creditease.geb.pavo.scheduler.remoting.mock.nio.SelectionKey;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * 抽象的处理类
 */
public  abstract  class AbstractProcessor implements IoProcessor {


    private  ExecutorService MOCK_REMOTE_EXECUTOR = Executors.newFixedThreadPool(10);

    //任务处理器
    protected IoHandler eventHandler;


    protected EventHandlerLoop  eventHandleLoop;

    //写缓存Queue，每个processor一个
    private  ConcurrentMap<IoChannel, Queue<WriteRequest>> QUEUE_MAP = new ConcurrentHashMap<>();


    /**
     *
     * @param eventHandler 通过构造函数传入，业务处理实现相应的handler
     */
    public AbstractProcessor(IoHandler eventHandler) {
        this.eventHandler = eventHandler;
        this.eventHandleLoop = new EventHandlerLoop("IO-EVENT-HANDLER",this);


    }

    public  void start(){
        this.eventHandleLoop.start();
    }

    //客户端连接
    @Override
    public IoFuture connect(String remoteAddress) {
        IoFuture ioFuture = new IoFuture();
        IoChannel channel = doConnect(remoteAddress,ioFuture);
        QUEUE_MAP.putIfAbsent(channel, new ConcurrentLinkedQueue<>());
        return ioFuture;
    }

    //服务器接收
    @Override
    public void accept(SelectionKey selectionKey) {
        IoChannel channel = doAccept( selectionKey);
        QUEUE_MAP.putIfAbsent(channel, new ConcurrentLinkedQueue<>());
    }


    @Override
    public IoFuture writeAndFlush(IoChannel channel, Object msg) {
        IoFuture ioFuture = new IoFuture();
        WriteRequest writeRequest = new WriteRequest(ioFuture, msg);
        Queue queue = QUEUE_MAP.get(channel);
        queue.add(writeRequest);
        flush(channel);
        return ioFuture;
    }


    @Override
    public void flush(IoChannel channel) {
        MOCK_REMOTE_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Queue<WriteRequest> q = QUEUE_MAP.get(channel);
                while(!q.isEmpty()){
                    WriteRequest wr = q.poll();
                    try {
                        channel.socketChannel().write((RemotingCommand)wr.getCommand());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    IoFuture ioFuture = wr.getFuture();
                    ioFuture.notifyListeners();
                }
            }

        });
    }

    @Override
    public void read(IoChannel channel) {

        List<RemotingCommand> result = new ArrayList<>();
        try {

            result =  channel.socketChannel().read(channel);
            for(RemotingCommand obj : result){
                eventHandler.messageReceived(channel,obj);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doMessageReceived( final  IoChannel channel,final ByteBuffer readBuffer){


    }

    protected abstract IoChannel doAccept(SelectionKey selectionKey);


    protected abstract IoChannel doConnect(String remoteAddress, IoFuture ioFuture);




}
