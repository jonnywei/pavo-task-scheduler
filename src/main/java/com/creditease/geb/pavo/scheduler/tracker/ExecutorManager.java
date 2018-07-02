package com.creditease.geb.pavo.scheduler.tracker;

import com.creditease.geb.pavo.scheduler.executor.Executor;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * tracker 对应的 executor manager
 */
public class ExecutorManager {

    private ConcurrentHashMap<String/*nodeGroup*/, Set<Executor>> EXECUTOR_MAP = new ConcurrentHashMap<>();



}
