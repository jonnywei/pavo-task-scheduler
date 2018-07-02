package com.creditease.geb.pavo.scheduler.core;

import com.creditease.geb.pavo.scheduler.remoting.RemotingProcessor;
import com.creditease.geb.pavo.scheduler.remoting.RemotingServer;
import com.creditease.geb.pavo.scheduler.remoting.simple.SimpleRemotingServer;

/**
 * abstract server
 */
public abstract  class AbstractServerNode extends AbstractNode {

    protected RemotingServer remotingServer;


    @Override
    protected void remotingStart() {
        this.remotingServer.start();
    }

    @Override
    protected void remotingStop() {
        this.remotingServer.shutdown();
    }

    @Override
    protected void beforeRemotingStart() {
        String addr = this.getNodeInfo().getAddress();
        this.remotingServer = new SimpleRemotingServer(addr);
        this.remotingServer.registerProcessor(getDefaultProcessor(),null);
    }

    @Override
    protected void afterRemotingStart() {

    }

    @Override
    protected void beforeRemotingStop() {

    }

    @Override
    protected void afterRemotingStop() {

    }


    /**
     * 得到默认的处理器
     */
    protected abstract RemotingProcessor getDefaultProcessor();

}
