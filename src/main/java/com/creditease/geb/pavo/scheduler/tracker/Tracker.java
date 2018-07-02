package com.creditease.geb.pavo.scheduler.tracker;

import com.creditease.geb.pavo.scheduler.core.AbstractServerNode;
import com.creditease.geb.pavo.scheduler.remoting.RemotingProcessor;
import com.creditease.geb.pavo.scheduler.tracker.processor.PingProcessor;

/**
 * task scheduler
 */
public class Tracker  extends AbstractServerNode {

    public Tracker() {
        this.setNodeInfo(new TrackerNodeInfo());
        this.appContext = new TrackerAppContext();
        this.appContext.setNode(this.getNodeInfo());
    }

    @Override
    protected RemotingProcessor getDefaultProcessor() {
        return new PingProcessor();
    }
}
