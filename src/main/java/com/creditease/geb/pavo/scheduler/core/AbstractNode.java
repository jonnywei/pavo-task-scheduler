package com.creditease.geb.pavo.scheduler.core;

import com.creditease.geb.pavo.scheduler.core.listener.NodeChangeListener;
import com.creditease.geb.pavo.scheduler.core.listener.SubscribedNodeManager;
import com.creditease.geb.pavo.scheduler.registry.NotifyEvent;
import com.creditease.geb.pavo.scheduler.registry.NotifyListener;
import com.creditease.geb.pavo.scheduler.registry.Registry;
import com.creditease.geb.pavo.scheduler.registry.RegistryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * abstract node
 * 1, node register
 *
 */
public abstract class AbstractNode implements Node {

    private  Logger logger = LoggerFactory.getLogger(Node.class);

    // 注册中心
    private Registry registry;

    //节点信息
    protected NodeInfo nodeInfo;

    //appContext
    protected AppContext appContext;

    //节点变化监听器
    private List<NodeChangeListener> nodeChangeListeners;

    public AbstractNode(){

        this.nodeChangeListeners = new ArrayList<>();

    }


    @Override
    public void start() {

        initConfig();

        // 启动远程通信能力
        beforeRemotingStart();

        remotingStart();

        afterRemotingStart();

        initRegistry();

        registry.register(nodeInfo);

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    //初始化注册中心
    private void initRegistry(){

      registry = RegistryFactory.getRegistry(this.appContext);

      registry.subscribe(nodeInfo, new NotifyListener() {
          @Override
          public void notify(NotifyEvent event, List<NodeInfo> nodes) {
              switch (event){
                  case ADD:
                      for(NodeChangeListener listener : nodeChangeListeners){
                          listener.addNodes(nodes);
                      }
                      break;
                  case REMOVE:
                      for(NodeChangeListener listener : nodeChangeListeners){
                          listener.removeNodes(nodes);
                      }
                      break;
              }
          }
      });

    }

    private void initConfig(){

        SubscribedNodeManager subscribedNodeManager = new SubscribedNodeManager(this.appContext);
        this.appContext.setSubscribedNodeManager(subscribedNodeManager);
        //
        this.nodeChangeListeners.add(subscribedNodeManager);

    }

    public NodeInfo getNodeInfo() {
        return nodeInfo;
    }

    public void setNodeInfo(NodeInfo nodeInfo) {
        this.nodeInfo = nodeInfo;
    }

    //**子类实现
    protected abstract void remotingStart();

    protected abstract void remotingStop();

    protected abstract void beforeRemotingStart();

    protected abstract void afterRemotingStart();

    protected abstract void beforeRemotingStop();

    protected abstract void afterRemotingStop();

}
