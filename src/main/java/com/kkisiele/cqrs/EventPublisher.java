package com.kkisiele.cqrs;

public interface EventPublisher {
    void publish(Event event);
}
