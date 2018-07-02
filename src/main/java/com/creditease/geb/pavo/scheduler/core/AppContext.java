package com.creditease.geb.pavo.scheduler.core;

import com.creditease.geb.pavo.scheduler.core.event.EventCenter;
import com.creditease.geb.pavo.scheduler.core.event.simple.SimpleEventCenter;
import com.creditease.geb.pavo.scheduler.core.listener.SubscribedNodeManager;

/**
 * application context
 * 应用程序上下文信息
 */
public abstract class AppContext {

    private EventCenter eventCenter;

    private SubscribedNodeManager subscribedNodeManager;

    private NodeInfo node;

    public AppContext( ) {
        this.eventCenter = new SimpleEventCenter();
    }

    public EventCenter getEventCenter() {
        return eventCenter;
    }

    public SubscribedNodeManager getSubscribedNodeManager() {
        return subscribedNodeManager;
    }

    public void setSubscribedNodeManager(SubscribedNodeManager subscribedNodeManager) {
        this.subscribedNodeManager = subscribedNodeManager;
    }

    public NodeInfo getNode() {
        return node;
    }

    public void setNode(NodeInfo node) {
        this.node = node;
    }
}
