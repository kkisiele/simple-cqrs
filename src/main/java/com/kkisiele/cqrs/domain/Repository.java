package com.kkisiele.cqrs.domain;

import com.kkisiele.cqrs.EventPublisher;
import com.kkisiele.cqrs.EventStore;

import java.util.UUID;

public class Repository<T extends AggregateRoot> {
    private final Class<T> clazz;
    private final EventStore eventStore;
    private final EventPublisher eventPublisher;

    public Repository(Class<T> clazz, EventStore eventStore, EventPublisher eventPublisher) {
        this.clazz = clazz;
        this.eventStore = eventStore;
        this.eventPublisher = eventPublisher;
    }

    public void save(T aggregate) {
        eventStore.saveEvents(aggregate.id(), aggregate.uncommittedChanges());
        eventPublisher.publish(aggregate.uncommittedChanges());
        aggregate.markChangesAsCommitted();
    }

    public T getById(UUID id) {
        T aggregate = null;
        try {
            aggregate = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        aggregate.loadFromEvents(eventStore.getEventsForAggregate(id));

        return aggregate;
    }
}
