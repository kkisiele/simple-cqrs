package com.kkisiele.cqrs;

import com.kkisiele.cqrs.domain.Event;

public interface EventPublisher {
    void publish(Event event);
}
