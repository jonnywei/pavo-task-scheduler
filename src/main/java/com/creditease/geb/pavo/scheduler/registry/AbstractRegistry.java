package com.creditease.geb.pavo.scheduler.registry;

import com.creditease.geb.pavo.scheduler.core.NodeInfo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 *
 * abstract registry
 *
 */
public abstract class AbstractRegistry implements Registry {

    private Map<String, NodeInfo> nodeInfoMap = new HashMap<>();

    private final ConcurrentMap<NodeInfo, Set<NotifyListener>> subscribed = new ConcurrentHashMap<>();

    @Override
    public void register(NodeInfo node) {
        nodeInfoMap.put(node.getId(),node);
    }

    @Override
    public void unregister(NodeInfo node) {
        nodeInfoMap.remove(node.getId());
    }

    @Override
    public void subscribe(NodeInfo nodeInfo, NotifyListener notifyListener) {
        Set<NotifyListener> listeners = subscribed.get(nodeInfo);
        if(listeners == null){
            subscribed.putIfAbsent(nodeInfo, new HashSet<NotifyListener>());
            listeners = subscribed.get(nodeInfo);
        }
        listeners.add(notifyListener);
    }

    @Override
    public void unsubscribe(NodeInfo nodeInfo, NotifyListener notifyListener) {
        Set<NotifyListener> listeners = subscribed.get(nodeInfo);
        if(listeners != null){
            listeners.remove(notifyListener);
        }
    }

    @Override
    public void destroy() {

    }

    protected void notify(String d){

    }

    protected Set<NodeInfo> getRegistered(){
        Set<NodeInfo> nodeInfoSet = new HashSet<>();
        for(String key : nodeInfoMap.keySet()){
            nodeInfoSet.add(nodeInfoMap.get(key));
        }
        return nodeInfoSet;
    }

    protected ConcurrentMap<NodeInfo, Set<NotifyListener>> getSubscribed(){
        return  subscribed;
    }
}
