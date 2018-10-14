package com.kkisiele.cqrs.domain;

import com.kkisiele.cqrs.infrastructure.EventStore;

import java.util.UUID;

public class Repository<T extends AggregateRoot> {
    private final Class<T> clazz;
    private final EventStore eventStore;

    public Repository(Class<T> clazz, EventStore eventStore) {
        this.clazz = clazz;
        this.eventStore = eventStore;
    }

    public void save(T aggregate) {
        eventStore.saveEvents(aggregate.id(), aggregate.uncommittedChanges());
        aggregate.markChangesAsCommitted();
    }

    public T getById(UUID id) {
        T aggregate = aggregateNewInstance();
        aggregate.loadFromEvents(eventStore.getEventsForAggregate(id));
        return aggregate;
    }

    private T aggregateNewInstance() {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception ex) {
            throw new RuntimeException("Create instance error", ex);
        }
    }
}
