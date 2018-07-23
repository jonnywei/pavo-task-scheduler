package com.creditease.geb.pavo.scheduler.remoting.mock;

import com.creditease.geb.pavo.scheduler.remoting.mock.processor.IoClientProcessor;

public class IoClient {

    private IoClientProcessor processor;


    public IoClient(IoHandler eventHandler, String addr) {
        this.processor = new IoClientProcessor(eventHandler, addr);
    }

    public IoFuture connect(String remoteAddress){
        this.processor.start();
        return processor.connect(remoteAddress);
    }

    public void shutdown(){

    }
}
