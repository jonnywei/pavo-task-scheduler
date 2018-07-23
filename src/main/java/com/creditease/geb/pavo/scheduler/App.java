package com.creditease.geb.pavo.scheduler;

import com.creditease.geb.pavo.scheduler.executor.ExecutorBootstrap;
import com.creditease.geb.pavo.scheduler.tracker.TrackerBootstrap;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws InterruptedException {

        TrackerBootstrap.main(null);
//        TrackerBootstrap.main(null);
//        ExecutorBootstrap.main(null);
//        ExecutorBootstrap.main(null);
        ExecutorBootstrap.main(null);

        System.out.println("over");
        Thread.sleep(100000);
    }
}
