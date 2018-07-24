package com.creditease.geb.pavo.scheduler.remoting.mock.nio;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MockSelector extends Selector {

    List<SelectableChannel> channels = new ArrayList<>();

    protected HashSet<SelectionKey> keys = new HashSet<>();

    private Set<SelectionKey> selectedKeys = new HashSet<>();

    private ReentrantLock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();

    @Override
    public boolean open() {
        return false;
    }

    @Override
    public Set<SelectionKey> selectedKeys() {
        return selectedKeys;
    }

    @Override
    public int selectNow() {
     return select(500);
    }

    @Override
    public int select(long timeoutMills) {
        long start = System.currentTimeMillis();
        int count ;
        long remindTime = timeoutMills;
        while(true){
            try {
                lock.lock();
                condition.await(remindTime,TimeUnit.MILLISECONDS);
                count =doSelect();
                break;
            } catch (InterruptedException e) {
                 remindTime = System.currentTimeMillis() - start;
                 count =doSelect();
                 if(count > 0){
                     break;
                 }else {
                     if (remindTime < timeoutMills){
                     }else {
                         break;
                     }
                 }
            } finally {
                lock.unlock();
            }
        }

        return count;
    }


    private int doSelect(){
        for(SelectionKey key :keys){
            for(int i=0; i < key.readyOps().length; i++){
                if(key.readyOps()[i] == 1){
                    if(key.interestOps()[i] ==1){
                        selectedKeys.add(key);
                      //  key.readyOps()[i] =0; //复位
                    }
                }
            }
            //interest ops 和 readyops重叠
            //add to selectedKeys
        }
        return selectedKeys.size();
    }



    @Override
    protected SelectionKey register(SelectableChannel channel, Object att, int... ops) {

        SelectionKey selectionKey = new SelectionKey(channel, this);

        selectionKey.attach(att);
        selectionKey.setInterestOps(ops);
        channels.add(channel);
        keys.add(selectionKey);

        return selectionKey;
    }

    @Override
    public void cancel(SelectionKey selectionKey) {
        keys.remove(selectionKey);
        channels.remove(selectionKey.channel());
    }



}
