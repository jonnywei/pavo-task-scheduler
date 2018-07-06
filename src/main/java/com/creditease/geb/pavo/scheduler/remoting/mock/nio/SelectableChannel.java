package com.creditease.geb.pavo.scheduler.remoting.mock.nio;

import java.io.IOException;
import java.nio.ByteBuffer;

public abstract class SelectableChannel {

    String localAddress;


    SelectionKey selectionKey;


    public  SelectionKey register(Selector selector, Object att, int... ops){
        SelectionKey selectionKey = selector.register(this,att,ops);
        this.selectionKey = selectionKey;
        return selectionKey;
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
    public int read(ByteBuffer dst) throws IOException {
        return 0;
    }


    public int write(ByteBuffer src) throws IOException{
        return 0;
    }


}
