package com.kkisiele.cqrs.infrastructure;

import com.kkisiele.cqrs.domain.AggregateNotFoundException;
import com.kkisiele.cqrs.Event;

import java.util.*;

public final class InMemoryEventStore implements EventStore {
    private final EventPublisher publisher;
    private final Map<UUID, List<Event>> store = new HashMap();

    public InMemoryEventStore(EventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void saveEvents(UUID aggregateId, List<Event> events) {
        for(Event event : events) {
            store
                    .computeIfAbsent(aggregateId, uuid -> new LinkedList<>())
                    .add(event);
            publisher.publish(event);
        }
    }

    @Override
    public List<Event> getEventsForAggregate(UUID aggregateId) {
        List<Event> eventStream = store.get(aggregateId);
        if(eventStream == null) {
            throw new AggregateNotFoundException();
        }
        return Collections.unmodifiableList(eventStream);
    }
}
