package com.creditease.geb.pavo.scheduler.registry;

import com.creditease.geb.pavo.scheduler.core.NodeInfo;

/**
 * registry interface
 */
public interface Registry {

    /**
     *注册节点
     */
    void register(NodeInfo node);

    /**
     * 取消注册节点
     * @param node
     */
    void unregister(NodeInfo node);

    /**
     * 监听节点事件
     * @param nodeInfo
     * @param notifyListener
     */
    void subscribe(NodeInfo nodeInfo, NotifyListener notifyListener);

    /**
     * 取消监听节点事件
     * @param nodeInfo
     * @param notifyListener
     */
    void unsubscribe(NodeInfo nodeInfo, NotifyListener notifyListener);


    /**
     * 销毁注册
     */
    void destroy();

}
