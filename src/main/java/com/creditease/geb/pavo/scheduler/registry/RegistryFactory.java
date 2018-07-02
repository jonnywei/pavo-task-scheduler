package com.creditease.geb.pavo.scheduler.registry;

import com.creditease.geb.pavo.scheduler.core.AppContext;
import com.creditease.geb.pavo.scheduler.registry.simple.SimpleRegistry;

/**
 * 注册工厂
 */
public class RegistryFactory {

    //测试用，单例，SimpleRegistry 整个程序一个
    private static SimpleRegistry simpleRegistry = new SimpleRegistry();

    public static Registry getRegistry(AppContext appContext){
        return simpleRegistry;
    }
}
