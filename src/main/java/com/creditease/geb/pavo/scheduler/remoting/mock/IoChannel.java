package com.creditease.geb.pavo.scheduler.remoting.mock;

import com.creditease.geb.pavo.scheduler.remoting.mock.nio.SelectableChannel;

/**
 *
 * 代表连接对象，连接建立之后服务器和客户端各有一个
 *
 */
public interface IoChannel {

    String remoteAddress();

    String localAddress();

    /**
     * 底层io操作的channel
     * @return
     */
    SelectableChannel socketChannel();

    IoFuture writeAndFlush(Object msg);

    boolean isConnected();

    boolean isOpen();

    boolean isClosed();

    IoFuture close();

}
