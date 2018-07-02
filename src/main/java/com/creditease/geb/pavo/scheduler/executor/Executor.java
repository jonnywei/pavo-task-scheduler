package com.creditease.geb.pavo.scheduler.executor;

import com.creditease.geb.pavo.scheduler.core.AbstractClientNode;

/**
 * job executor
 */
public class Executor extends AbstractClientNode {

    public Executor() {
        this.setNodeInfo(new ExecutorNodeInfo());
        this.appContext = new ExecutorAppContext();
        this.appContext.setNode(this.getNodeInfo());

    }
}
