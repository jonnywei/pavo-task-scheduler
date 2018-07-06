package com.creditease.geb.pavo.scheduler.remoting.mock;

/**
 * io 处理方法
 */
public interface IoHandler {

    void messageReceived(IoChannel channel, Object msg);


}
