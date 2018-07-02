package com.creditease.geb.pavo.scheduler.tracker;

import com.creditease.geb.pavo.scheduler.core.NodeInfo;
import com.creditease.geb.pavo.scheduler.core.NodeType;

/**
 * 代表tracker节点
 */
public class TrackerNodeInfo extends NodeInfo {
    public TrackerNodeInfo() {
        this.setId("tracker"+SEQ.getAndIncrement());
        this.setNodeType(NodeType.TRACKER);
        //关注的节点
        this.addListenNodeType(NodeType.TRACKER);
        this.addListenNodeType(NodeType.CLIENT);
        this.addListenNodeType(NodeType.EXECUTOR);
    }
}
