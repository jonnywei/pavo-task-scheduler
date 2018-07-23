package com.creditease.geb.pavo.scheduler.remoting;

/**
 * request processor
 */
public interface RemotingProcessor {

    RemotingCommand processRequest(Channel channel, RemotingCommand request);
}
