package com.creditease.geb.pavo.scheduler.core.listener;

import com.creditease.geb.pavo.scheduler.core.AppContext;
import com.creditease.geb.pavo.scheduler.core.NodeInfo;
import com.creditease.geb.pavo.scheduler.core.NodeType;
import com.creditease.geb.pavo.scheduler.core.event.Event;
import com.creditease.geb.pavo.scheduler.core.event.EventTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 关注的节点变化管理中心
 * zookeeper 的一个本地存储
 */
public class SubscribedNodeManager implements NodeChangeListener {

    private Logger logger = LoggerFactory.getLogger(SubscribedNodeManager.class);

    private Map<NodeType /* 节点类型*/, Set<NodeInfo>> NODES = new ConcurrentHashMap<>();

    private AppContext appContext;

    public SubscribedNodeManager(AppContext appContext) {
        this.appContext = appContext;
    }

    @Override
    public void addNodes(List<NodeInfo> nodes) {
        for(NodeInfo node : nodes){
            addNode(node);
        }
    }

    @Override
    public void removeNodes(List<NodeInfo> nodes) {
        for(NodeInfo node : nodes){
            removeNode(node);
        }
    }


    private void addNode(NodeInfo node){
        Set<NodeInfo> nodeSet = NODES.get(node.getNodeType());
        if(nodeSet == null){
            nodeSet = new HashSet<>();
            NODES.put(node.getNodeType(), nodeSet);
        }
        logger.debug("add node " + node);
        nodeSet.add(node);
        //发送到事件中心
        Event event = new Event(EventTopic.NODE_ADD);
        event.setParam("node",node);
        this.appContext.getEventCenter().publishSync(event);

    }


    private void removeNode(NodeInfo node){
        Set<NodeInfo> nodeSet = NODES.get(node.getNodeType());
        if(nodeSet != null){
            nodeSet.remove(nodeSet);
            Event event = new Event(EventTopic.NODE_REMOVE);
            event.setParam("node",node);
            this.appContext.getEventCenter().publishSync(event);
        }
    }


    public List<NodeInfo> getNodeList(NodeType nodeType){
        Set<NodeInfo> nodeInfoSet = NODES.get(nodeType);
        if(nodeInfoSet == null){
            return new ArrayList<>();
        }
        return  new ArrayList<>(NODES.get(nodeType));
    }
}
