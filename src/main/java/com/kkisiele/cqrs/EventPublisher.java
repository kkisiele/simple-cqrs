package com.kkisiele.cqrs;

import com.kkisiele.cqrs.Event;

public interface EventPublisher {
    void publish(Event event);
}
