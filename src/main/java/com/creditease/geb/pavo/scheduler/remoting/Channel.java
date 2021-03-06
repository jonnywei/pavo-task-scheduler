package com.creditease.geb.pavo.scheduler.remoting;

import java.net.SocketAddress;

/**
 * client server connect object
 */
public interface Channel {

    SocketAddress localAddress();

    String localAddr();

    SocketAddress remoteAddress();

    String remoteAddr();

    ChannelHandler writeAndFlush(Object msg);

    void close();

    boolean isConnected();

    boolean isOpen();

    boolean isClosed();
}
