package com.kkisiele.cqrs.domain;

import com.kkisiele.cqrs.EventStore;

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
