package com.creditease.geb.pavo.scheduler.remoting.mock;

import com.creditease.geb.pavo.scheduler.remoting.mock.processor.IoServerProcessor;

public class IoServer {

    private IoServerProcessor processor;

    private String addr;

    public IoServer(IoServerProcessor processor, String addr) {
        this.processor = processor;
        this.addr = addr;
    }

    public void bind(String localAddress){
        this.processor.start();
        this.processor.bind(localAddress);
    }
}
