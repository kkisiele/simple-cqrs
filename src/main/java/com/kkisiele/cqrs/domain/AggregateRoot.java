package com.kkisiele.cqrs.domain;

import com.kkisiele.cqrs.EventHandle;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public abstract class AggregateRoot {
    private final List<Event> changes = new LinkedList<>();

    protected void applyChange(Event event) {
        applyChange(event, true);
    }

    protected void applyChange(Event event, boolean isNew) {
        new EventHandle(this).dispatch(event);
        if(isNew) {
            changes.add(event);
        }
    }

    public void loadFromEvents(Iterable<Event> events) {
        for(Event event : events) {
            applyChange(event, false);
        }
    }

    public Iterable<Event> uncommittedChanges() {
        return Collections.unmodifiableList(changes);
    }

    public abstract UUID id();
}
