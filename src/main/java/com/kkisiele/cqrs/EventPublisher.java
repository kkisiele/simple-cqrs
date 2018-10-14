package com.kkisiele.cqrs;

import com.kkisiele.cqrs.domain.Event;

public interface EventPublisher {
    void publish(Event event);

    default void publish(Iterable<Event> events) {
        for(Event event : events) {
            publish(event);
        }
    }
}
