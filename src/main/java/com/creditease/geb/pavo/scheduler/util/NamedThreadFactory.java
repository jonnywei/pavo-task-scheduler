package com.creditease.geb.pavo.scheduler.util;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NamedThreadFactory implements ThreadFactory {

    private final String prefix;

    private final boolean daemon;

    private final AtomicInteger threadNum =new AtomicInteger(1);

    private static final AtomicInteger POOL_SEQ = new AtomicInteger(1);

    public NamedThreadFactory(String prefix) {
        this("Pool-"+POOL_SEQ.getAndIncrement(),false);
    }

    public NamedThreadFactory(String prefix, boolean daemon) {
        this.prefix = prefix +"-thread-";
        this.daemon = daemon;
    }

    @Override
    public Thread newThread(Runnable r) {
        String name = prefix +  threadNum.getAndIncrement();
        Thread thread = new Thread(r,name);
        thread.setDaemon(daemon);
        return thread;
    }
}
