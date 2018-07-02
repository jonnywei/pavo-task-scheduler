package com.creditease.geb.pavo.scheduler.core.event;

import java.util.HashMap;
import java.util.Map;

public class Event {

    private String topic;

    private Map<String, Object> params;

    public Event(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParam(String key, Object value) {
        if(this.params == null){
            this.params = new HashMap<>();
        }
        this.params.put(key, value);
    }
}
