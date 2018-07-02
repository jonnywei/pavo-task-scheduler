package com.creditease.geb.pavo.scheduler.core.support;

import com.creditease.geb.pavo.scheduler.core.AppContext;
import com.creditease.geb.pavo.scheduler.core.NodeInfo;
import com.creditease.geb.pavo.scheduler.core.NodeType;
import com.creditease.geb.pavo.scheduler.remoting.RemotingClient;
import com.creditease.geb.pavo.scheduler.remoting.RemotingCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * send heartbeat to server
 * 心跳检测的目的是为了去除无法连接的jobtracker
 */
public class HeartBeatMonitor {

    private Logger logger = LoggerFactory.getLogger(HeartBeatMonitor.class);

    private AppContext appContext;

    private RemotingClient remotingClient;

    private ScheduledExecutorService PING_EXECUTOR = Executors.newSingleThreadScheduledExecutor();

    public HeartBeatMonitor(AppContext appContext, RemotingClient remotingClient) {
        this.appContext = appContext;
        this.remotingClient = remotingClient;
    }


    /**
     * 启动,定时check
     */
    public void start(){
        PING_EXECUTOR.scheduleWithFixedDelay(() -> check(), 1000, 5000, TimeUnit.MILLISECONDS);
    }

    public void stop(){

    }

    private void check(){
        try {
            List<NodeInfo> trackers = appContext.getSubscribedNodeManager().getNodeList(NodeType.TRACKER);
            for(NodeInfo node :trackers){
                check(node);
            }
        }catch (Throwable t){
            logger.error("Check Tracker error", t);
        }


    }

    private void check(NodeInfo tracker){
        if(beat(tracker.getAddress())){

        }


    }

    private boolean beat(String addr){
        logger.debug(this.appContext.getNode().getId() +  " send ping to "+ addr);
        RemotingCommand pingCommand =  RemotingCommand.createRequestCommand(RemotingCommand.RequestCode.PING.code());
        RemotingCommand response = this.remotingClient.invokeSync(addr, pingCommand,5000);
        logger.debug(this.appContext.getNode().getId() +  " receive "+ response.getCode() +" from to "+ addr);

        return true;
    }

}
