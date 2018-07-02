package com.creditease.geb.pavo.scheduler.core.event;

/**
 * event center
 */
public interface EventCenter {

    /**
     * subscribe topics
     * @param subscriber
     * @param topics
     */
    void subscribe(EventSubscriber subscriber, String ... topics);


    /**
     * unsubscribe topics
     * @param subscriber
     * @param topics
     */
    void unsubscribe(EventSubscriber subscriber, String ... topics);


    /**
     * sync publish topic
     * @param event
     */
    void publishSync(Event event);

    /**
     * async publish topic
     * @param event
     */
    void publishAsync(Event event);
}
