package com.creditease.geb.pavo.scheduler.remoting.mock.processor;

import com.creditease.geb.pavo.scheduler.remoting.mock.IoChannel;
import com.creditease.geb.pavo.scheduler.remoting.mock.nio.MockSelector;
import com.creditease.geb.pavo.scheduler.remoting.mock.nio.SelectionKey;
import com.creditease.geb.pavo.scheduler.remoting.mock.nio.Selector;

import java.util.Iterator;
import java.util.Set;

public class EventHandlerLoop extends Thread{
        
        private Selector selector ;
        IoProcessor processor;
        public EventHandlerLoop(String name, IoProcessor processor) {
            super(name);
            setDaemon(true);
            this.processor = processor;
            this.selector = new MockSelector();
        }

        @Override
        public void run() {
            while(true){
                try {
                    selector.selectNow();
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()){
                        SelectionKey key = iterator.next();
                        iterator.remove();

                        if(key.isAcceptable()){
                            key.resetAcceptable();
                            doAccept(key);
                        }
                        if(key.isConnectable()){
                            key.resetConnectable();
                            doConnect(key);
                        }
                        if(key.isReadable()){
                            key.resetReadable();
                            doRead(key);
                        }
                        if(key.isWritable()){
                            key.resetWritable();
                            doWrite(key);
                        }
                    }
                  
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

    private void doAccept(SelectionKey key) {
        processor.accept(key);
    }

    private void doRead(SelectionKey key) {
        IoChannel channel = (IoChannel) key.attachment();
        processor.read(channel);
    }


    private void doConnect(SelectionKey key){
            processor.connect(key);
    }

    private void doWrite(SelectionKey key){
        IoChannel channel = (IoChannel) key.attachment();
        processor.flush(channel);
    }

    public Selector selector(){
            return this.selector;
    }
}