package com.creditease.geb.pavo.scheduler.core.event.simple;

import com.creditease.geb.pavo.scheduler.core.event.Event;
import com.creditease.geb.pavo.scheduler.core.event.EventCenter;
import com.creditease.geb.pavo.scheduler.core.event.EventSubscriber;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * inJVM event center
 */
public class SimpleEventCenter implements EventCenter {

    private ConcurrentHashMap<String,Set<EventSubscriber>> TOPICS = new ConcurrentHashMap<>();
    @Override
    public void subscribe(EventSubscriber subscriber, String... topics) {
        for(String topic : topics){
            Set<EventSubscriber> eventSubscriberSet = TOPICS.get(topic);
            if(eventSubscriberSet == null){
                eventSubscriberSet = new HashSet<>();
                TOPICS.putIfAbsent(topic, eventSubscriberSet);
            }
            eventSubscriberSet.add(subscriber);
        }
    }

    @Override
    public void unsubscribe(EventSubscriber subscriber, String... topics) {
        for(String topic : topics){
            Set<EventSubscriber> eventSubscriberSet = TOPICS.get(topic);
            if(eventSubscriberSet != null){
                eventSubscriberSet.remove(subscriber);
            }
        }
    }

    @Override
    public void publishSync(Event event) {
        Set<EventSubscriber> eventSubscriberSet = TOPICS.get(event.getTopic());
        if(eventSubscriberSet != null){
           for(EventSubscriber subscriber : eventSubscriberSet){
               subscriber.getObserver().onObserverd(event);
           }
        }
    }

    @Override
    public void publishAsync(Event event) {

    }
}
