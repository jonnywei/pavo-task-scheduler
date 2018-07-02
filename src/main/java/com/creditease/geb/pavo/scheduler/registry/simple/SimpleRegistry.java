package com.creditease.geb.pavo.scheduler.registry.simple;

import com.creditease.geb.pavo.scheduler.core.NodeInfo;
import com.creditease.geb.pavo.scheduler.core.NodeType;
import com.creditease.geb.pavo.scheduler.registry.AbstractRegistry;
import com.creditease.geb.pavo.scheduler.registry.NotifyEvent;
import com.creditease.geb.pavo.scheduler.registry.NotifyListener;
import com.creditease.geb.pavo.scheduler.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 简单的基于内存的注册
 */
public class SimpleRegistry extends AbstractRegistry implements Registry{
    private Logger logger = LoggerFactory.getLogger(SimpleRegistry.class);

    @Override
    public void register(NodeInfo node) {
        logger.debug("register "+ node);
        super.register(node);
        doRegister(node);
    }

    @Override
    public void unregister(NodeInfo node) {
        super.unregister(node);
        doUnRegister(node);
    }

    @Override
    public void subscribe(NodeInfo nodeInfo, NotifyListener notifyListener) {
        super.subscribe(nodeInfo,notifyListener);
        doSubscribe(nodeInfo,notifyListener);
    }

    @Override
    public void unsubscribe(NodeInfo nodeInfo, NotifyListener notifyListener) {
        super.unsubscribe(nodeInfo,notifyListener);
        doUnsubscribe(nodeInfo,notifyListener);
    }

    @Override
    public void destroy() {
        super.destroy();
    }



    protected  void doRegister(NodeInfo node){
        Set<NodeInfo> registered =  getRegistered();
        List<NodeInfo> listenedNodes = new ArrayList<>();
        listenedNodes.add(node);
        for(NodeInfo rn : registered){
            if(rn.getListenNodeTypes().contains(node.getNodeType()) ){
                for(NotifyListener notifyListener :  getSubscribed().get(rn)){
                    notifyListener.notify(NotifyEvent.ADD,listenedNodes);
                }
             }
        }
    }

    protected  void doUnRegister(NodeInfo node){

    }

    /**
     *
     * @param node
     * @param listener
     */
    protected  void doSubscribe(NodeInfo node, NotifyListener listener){
        //找到该节点关注的类型
        for(NodeType nodeType : node.getListenNodeTypes()){
            Set<NodeInfo> registered =  getRegistered();
            List<NodeInfo> listenedNodes = new ArrayList<>();
            for(NodeInfo rn : registered){
                if(rn.getNodeType() == nodeType){
                    listenedNodes.add(rn);
                }
            }
            for(NotifyListener notifyListener :  getSubscribed().get(node)){
                notifyListener.notify(NotifyEvent.ADD,listenedNodes);
            }
        }

    }

    protected  void doUnsubscribe(NodeInfo node, NotifyListener listener){

    }
}
