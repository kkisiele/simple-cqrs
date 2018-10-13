package com.kkisiele.cqrs;

import com.kkisiele.cqrs.domain.Event;

import java.util.UUID;

public interface EventStore {
    void saveEvents(UUID aggregateId, Iterable<Event> events);
    Iterable<Event> getEventsForAggregate(UUID aggregateId);
}
