package com.creditease.geb.pavo.scheduler.registry;

import com.creditease.geb.pavo.scheduler.core.NodeInfo;

import java.util.List;

/**
 * notify listener
 */
public interface NotifyListener {

    /**
     * notify event process
     * @param event
     * @param nodes
     */
    void notify(NotifyEvent event, List<NodeInfo> nodes);
}
