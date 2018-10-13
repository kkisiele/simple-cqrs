package com.kkisiele.cqrs;

import com.kkisiele.cqrs.domain.AggregateNotFoundException;
import com.kkisiele.cqrs.domain.Event;

import java.util.*;

public class InMemoryEventStore implements EventStore {
    private final EventPublisher publisher;
    private final Map<UUID, List<Event>> allEvents = new HashMap();

    public InMemoryEventStore(EventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void saveEvents(UUID aggregateId, Iterable<Event> events) {
        for(Event event : events) {
            allEvents.computeIfAbsent(aggregateId, uuid -> new ArrayList<>()).add(event);
            publisher.publish(event);
        }
    }

    @Override
    public Iterable<Event> getEventsForAggregate(UUID aggregateId) {
        if(!allEvents.containsKey(aggregateId)) {
            throw new AggregateNotFoundException();
        }

        return allEvents.get(aggregateId);
    }

    public Set<UUID> aggregateIds() {
        return allEvents.keySet();
    }
}
