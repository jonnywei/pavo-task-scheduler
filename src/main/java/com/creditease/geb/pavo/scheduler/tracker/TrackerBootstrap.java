package com.creditease.geb.pavo.scheduler.tracker;

/**
 * 任务跟踪器引导类
 */
public class TrackerBootstrap {

    public static void main(String[] args) {

        final Tracker tracker = new Tracker();

        tracker.start();

        //shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                tracker.stop();
            }
        }));
        System.out.println("tracker start");

    }
}
