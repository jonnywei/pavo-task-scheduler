package com.creditease.geb.pavo.scheduler.executor;

import com.creditease.geb.pavo.scheduler.core.NodeInfo;
import com.creditease.geb.pavo.scheduler.core.NodeType;

/**
 * 代表tracker节点
 */
public class ExecutorNodeInfo extends NodeInfo {
    public ExecutorNodeInfo() {
        this.setId("executor"+SEQ.getAndIncrement());
        this.setNodeType(NodeType.EXECUTOR);
        //关注的节点
        this.addListenNodeType(NodeType.TRACKER);
        this.addListenNodeType(NodeType.CLIENT);
        this.addListenNodeType(NodeType.EXECUTOR);
    }
}
