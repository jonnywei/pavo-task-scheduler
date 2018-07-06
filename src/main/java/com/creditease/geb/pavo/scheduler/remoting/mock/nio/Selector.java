package com.creditease.geb.pavo.scheduler.remoting.mock.nio;

import java.util.Set;

public abstract  class Selector {


    public abstract boolean open();

    public abstract Set<SelectionKey> selectedKeys();


    public abstract int selectNow();


    public abstract int select (long timeoutMills);


    protected abstract SelectionKey register(SelectableChannel channel, Object att, int... ops);


    public abstract void cancel(SelectionKey  selectionKey);



}
