package com.kkisiele.cqrs.infrastructure;

import com.kkisiele.cqrs.Event;

public interface EventPublisher {
    void publish(Event event);
}
