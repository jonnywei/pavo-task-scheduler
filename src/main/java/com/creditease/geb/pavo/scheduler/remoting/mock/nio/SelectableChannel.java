package com.creditease.geb.pavo.scheduler.remoting.mock.nio;

import com.creditease.geb.pavo.scheduler.remoting.RemotingCommand;

import java.io.IOException;
import java.util.List;

public abstract class SelectableChannel {

    String localAddress;


    SelectionKey selectionKey;


    public  SelectionKey register(Selector selector, Object att, int... ops){

        if(this.selectionKey != null){
            this.selectionKey.setInterestOps(ops);
            this.selectionKey.attach(att);
        }
        if(this.selectionKey == null){
            SelectionKey selectionKey = selector.register(this,att,ops);
            this.selectionKey = selectionKey;
        }
        return this.selectionKey;
    }


    public SelectableChannel(String localAddress) {
        this.localAddress = localAddress;
    }

    public  String getLocalAddress(){
     return this.localAddress;
    }

    /**
     * read into buffer
     * @param dst
     * @return
     * @throws IOException
     */
    public List<RemotingCommand> read(Object dst) throws IOException {
        return MockSocketChannelRouter.read(this);
    }


    public int write(RemotingCommand src) throws IOException{
        return MockSocketChannelRouter.write(this, src);
    }

    /**
     * 返回注册的key
     * @return
     */
    public SelectionKey keyFor(){
        return this.selectionKey;
    }

    public   boolean finishConnect() throws IOException{
        return true;
    }
}
