package com.creditease.geb.pavo.scheduler.remoting.mock;

/**
 * io 处理方法 ,对上层的接口
 */
public interface IoHandler {

    void messageReceived(IoChannel channel, Object msg);


}
