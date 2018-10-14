package com.kkisiele.cqrs.infrastructure;

import com.kkisiele.cqrs.Event;

import java.util.List;
import java.util.UUID;

public interface EventStore {
    void saveEvents(UUID aggregateId, List<Event> events);
    List<Event> getEventsForAggregate(UUID aggregateId);
}
