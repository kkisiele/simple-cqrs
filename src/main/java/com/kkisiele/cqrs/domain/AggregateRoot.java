package com.kkisiele.cqrs.domain;

import java.lang.reflect.Method;
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
        invokeEventHandler(event);
        if(isNew) {
            changes.add(event);
        }
    }

    private void invokeEventHandler(Event event) {
        for(Method method : getClass ().getDeclaredMethods()) {
            if(method.getAnnotation(EventHandler.class) != null
                    && method.getParameterTypes().length == 1
                    && method.getParameterTypes()[0] == event.getClass()) {
                try {
                    method.setAccessible(true);
                    method.invoke(this, event);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                return;
            }
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
