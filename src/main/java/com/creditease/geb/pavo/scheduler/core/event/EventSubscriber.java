package com.creditease.geb.pavo.scheduler.core.event;

public class EventSubscriber {

    private String id;

    private Observer observer;

    public EventSubscriber(String id, Observer observer) {
        this.id = id;
        this.observer = observer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Observer getObserver() {
        return observer;
    }

    public void setObserver(Observer observer) {
        this.observer = observer;
    }
}
