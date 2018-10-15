package com.kkisiele.cqrs.domain;

import com.kkisiele.cqrs.Event;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public abstract class AggregateRoot {
    private final List<Event> changes = new LinkedList<>();

    public abstract UUID id();

    protected void applyChange(Event event) {
        applyChange(event, true);
    }

    protected void applyChange(Event event, boolean isNew) {
        dispatch(event);
        if(isNew) {
            changes.add(event);
        }
    }

    private void dispatch(Event event) {
        try {
            Method method = getClass().getDeclaredMethod("handle", event.getClass());
            method.setAccessible(true);
            method.invoke(this, event);
        } catch (NoSuchMethodException ex) {
            //that is ok
        } catch (Exception ex) {
            throw new RuntimeException("Error dispatching to event handler", ex);
        }
    }

    public void loadFromEvents(Iterable<Event> events) {
        events.forEach(event -> applyChange(event, false));
    }

    public List<Event> uncommittedChanges() {
        return Collections.unmodifiableList(changes);
    }

    public void markChangesAsCommitted() {
        changes.clear();
    }
}
