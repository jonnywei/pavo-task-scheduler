package com.creditease.geb.pavo.scheduler.executor;

import com.creditease.geb.pavo.scheduler.tracker.Tracker;

/**
 * 任务跟踪器引导类
 */
public class ExecutorBootstrap {

    public static void main(String[] args) {

        final Executor executor = new Executor();

        executor.start();

        //shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                executor.stop();
            }
        }));
        System.out.println("exist");

    }
}
