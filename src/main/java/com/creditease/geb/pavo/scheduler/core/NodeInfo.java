package com.creditease.geb.pavo.scheduler.core;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * a node info
 * 节点信息
 */
public class NodeInfo {

    protected static AtomicInteger SEQ = new AtomicInteger(0);

    private String id;

    private String clusterName;

    private String ip;

    private Integer port = 0;
    //节点类型
    private NodeType nodeType;

    // 关注节点类型
    private List<NodeType> listenNodeTypes;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public NodeType getNodeType() {
        return nodeType;
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public List<NodeType> getListenNodeTypes() {
        return listenNodeTypes;
    }

    public void setListenNodeTypes(List<NodeType> listenNodeTypes) {
        this.listenNodeTypes = listenNodeTypes;
    }

    public void addListenNodeType(NodeType nodeType) {
        if(this.listenNodeTypes == null){
            this.listenNodeTypes = new ArrayList<>();
        }
        this.listenNodeTypes.add(nodeType);
    }
    /**
     *TODO MODIFY IP:PORT
     * 节点的地址
     */
    public String getAddress(){
        return id;
    }

    @Override
    public String toString() {
        return "NodeInfo{" +
                "id='" + id + '\'' +
                ", clusterName='" + clusterName + '\'' +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                ", nodeType=" + nodeType +
                ", listenNodeTypes=" + listenNodeTypes +
                '}';
    }
}
