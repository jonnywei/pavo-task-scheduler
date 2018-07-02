package com.creditease.geb.pavo.scheduler.remoting;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * client and server communication object
 *
 *
 */
public class RemotingCommand implements Serializable {
    private static final long serialVersionUID = -7214675682869427730L;

    private static final AtomicInteger ID_GEN = new AtomicInteger(0);

    /**
     * 命令头信息
     */
    private int code;

    private int type; //request or response

    private int id;


    private RemotingCommand(){

    }

    public  enum RemotingCommandType {
        REQUEST(0),
        RESPONSE(1);
        private int code;
        RemotingCommandType(int code){
            this.code= code;
        }
        public int code() {
            return this.code;
        }
    }

    public enum RequestCode{
        // 心跳
        PING(100);
        private int code;
        RequestCode(int code) {
            this.code = code;
        }
        public int code() {
            return this.code;
        }
    }

    public enum ResponeCode{
        // 心跳
        P0NG(100);
        private int code;
        ResponeCode(int code) {
            this.code = code;
        }
        public int code() {
            return this.code;
        }
    }


    public static RemotingCommand createRequestCommand(int code){
        RemotingCommand cmd = new RemotingCommand();
        cmd.setCode(code);
        cmd.setType(RemotingCommandType.REQUEST.code());
        cmd.setId(ID_GEN.getAndIncrement());
        return cmd;
    }

    public static RemotingCommand createResponseCommand(int code){
        RemotingCommand cmd = new RemotingCommand();
        cmd.setCode(code);
        cmd.setType(RemotingCommandType.RESPONSE.code());
        cmd.setId(ID_GEN.getAndIncrement());
        return cmd;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
