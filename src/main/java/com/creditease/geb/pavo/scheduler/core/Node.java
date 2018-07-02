package com.creditease.geb.pavo.scheduler.core;

/**
 * a node is a tracker or executor or client
 */
public interface Node {
    /**
     * start
     */
    void start();


    /**
     * stop
     */
    void stop();

    /**
     * destroy
     */
    void destroy();
}
