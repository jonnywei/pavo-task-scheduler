package com.creditease.geb.pavo.scheduler.core.listener;

import com.creditease.geb.pavo.scheduler.core.NodeInfo;

import java.util.List;

/**
 * 节点变化通知
 */
public interface NodeChangeListener {

    /**
     * 添加节点
     * @param nodes
     */
    void addNodes(List<NodeInfo> nodes);

    /**
     * 删除节点
     * @param nodes
     */
    void removeNodes(List<NodeInfo> nodes);
}
