package com.creditease.geb.pavo.scheduler.remoting;

public interface ChannelFuture {

    boolean isConnected();

    /**
     * 返回是否成功
     * @param timeoutMills
     * @return
     */
    boolean awaitUninterruptibly(long timeoutMills);

    Channel getChannel();

    boolean isDone();

    Throwable cause();
}
