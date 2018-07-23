package com.creditease.geb.pavo.scheduler.remoting.mock;

import com.creditease.geb.pavo.scheduler.remoting.mock.processor.IoServerProcessor;

public class IoServer {

    private IoServerProcessor processor;

    public IoServer(IoHandler eventHandler, String addr) {
        this.processor = new IoServerProcessor(eventHandler, addr);
    }

    public void bind(String localAddress){
        this.processor.start(); //启动
        this.processor.register(); //注册
        this.processor.bind(localAddress);
    }

    public void shutdown(){

    }
}
