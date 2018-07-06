package com.creditease.geb.pavo.scheduler.remoting.mock.nio;

public class SelectionKey {

    public static final int OP_READ    =0;
    public static final int OP_WRITE   =1;
    public static final int OP_ACCEPT  =2;
    public static final int OP_CONNECT =3;
    // -- Attachments --

    private volatile Object attachment = null;

    private  int[] readyOps =new int[4];

    private  volatile int[] interestOps = new int[4];


    private SelectableChannel channel;

    private Selector selector;


    public SelectionKey(SelectableChannel channel, Selector selector) {
        this.channel = channel;
        this.selector = selector;
    }

    public final boolean isReadable(){
        return readyOps[OP_READ] != 0;
    }


    public final boolean isAcceptable(){
        return readyOps[OP_ACCEPT] != 0;
    }


    public final boolean isConnectable(){
        return readyOps[OP_CONNECT] != 0;
    }

    public final boolean isWritable(){
        return readyOps[OP_WRITE] != 0;
    }


    /**
     * Retrieves the current attachment.
     *
     * @return  The object currently attached to this key,
     *          or <tt>null</tt> if there is no attachment
     */
    public final Object attachment() {
        return attachment;
    }



    public final Object attach(Object ob) {
        return this.attachment = ob;
    }


    public void setReadyOps(int[] readyOps) {
        for(int i =0; i < 4; i++){ //复位
            this.readyOps[i] = 0;
        }
        for(int i : readyOps){
            this.readyOps[i] = 1;
        }
    }


    public void setInterestOps(int[] interestOps) {
        for(int i =0; i < 4; i++){ //复位
            this.interestOps[i] = 0;
        }
        for(int i : interestOps){
            this.interestOps[i] = 1;
        }
    }


    public Selector selector(){
        return this.selector;
    }

    public SelectableChannel channel(){
        return this.channel;
    }


    public void cancel(){
      selector().cancel(this);
    }
}
