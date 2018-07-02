package com.creditease.geb.pavo.scheduler.core;

import com.creditease.geb.pavo.scheduler.core.support.HeartBeatMonitor;
import com.creditease.geb.pavo.scheduler.remoting.RemotingClient;
import com.creditease.geb.pavo.scheduler.remoting.simple.SimpleRemotingClient;

/**
 * abstract client
 * 1 communication with server
 * 2 heart beat with server
 */
public abstract class AbstractClientNode extends AbstractNode {

    protected RemotingClient remotingClient;

    protected HeartBeatMonitor heartBeatMonitor;

    @Override
    protected void remotingStart() {
        remotingClient.start();
        this.heartBeatMonitor.start();
    }

    @Override
    protected void remotingStop() {
        remotingClient.shutdown();
        this.heartBeatMonitor.stop();
    }

    /**
     * build  remoting client
     */
    @Override
    protected void beforeRemotingStart() {
        this.remotingClient = new SimpleRemotingClient(getNodeInfo().getAddress());
        this.heartBeatMonitor = new HeartBeatMonitor(this.appContext,this.remotingClient);
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
}
