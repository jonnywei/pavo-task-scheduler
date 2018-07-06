package com.creditease.geb.pavo.scheduler.remoting.mock.processor;

import com.creditease.geb.pavo.scheduler.remoting.mock.IoChannel;
import com.creditease.geb.pavo.scheduler.remoting.mock.IoFuture;
import com.creditease.geb.pavo.scheduler.remoting.mock.nio.SelectionKey;

public interface IoProcessor {


    void accept(SelectionKey selectionKey);


    IoFuture writeAndFlush(IoChannel channel, Object msg);


    /**
     * 写数据
     * @param channel
     */
    void flush(IoChannel channel);


    void read(IoChannel channel);


    IoFuture connect(String remoteAddress);


    /**
     * 处理连接事件
     * @param key
     */
    void connect(SelectionKey key);
}
