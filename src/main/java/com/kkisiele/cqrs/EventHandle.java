package com.kkisiele.cqrs;

import com.kkisiele.cqrs.domain.Event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public final class EventHandle {
    private final Object instance;

    public EventHandle(Object instance) {
        this.instance = instance;
    }

    public void dispatch(Event event) {
        try {
            Method method = eventHandlerForClass(event.getClass());
            if(method == null) {
                return;
            }
            method.setAccessible(true);
            method.invoke(instance, event);
        } catch (IllegalAccessException | InvocationTargetException ex) {
            throw new RuntimeException("Cannot dispatch event", ex);
        }
    }

    private Method eventHandlerForClass(Class<? extends Event> eventClass) {
        List<Method> methods = eventHandlersForClass(eventClass);
        if (methods.size() > 1) {
            String msg = String.format("Cannot determine event handler, because there are %d matching handlers for event class %s", methods.size(), eventClass.getName());
            throw new RuntimeException(msg);
        }
        return methods.size() > 0 ? methods.get(0) : null;
    }

    private List<Method> eventHandlersForClass(Class<? extends Event> eventClass) {
        List<Method> result = new ArrayList<>();
        for (Method method : instance.getClass().getDeclaredMethods()) {
            if(isEventHandler(method, eventClass)) {
                result.add(method);
            }
        }
        return result;
    }

    private boolean isEventHandler(Method method, Class<? extends Event> eventClass) {
        if (isEventHandler(method)) {
            return method.getParameterTypes()[0] == eventClass;
        }
        return false;
    }

    private boolean isEventHandler(Method method) {
        return method.getAnnotation(EventHandler.class) != null && method.getParameterTypes().length == 1;
    }

}
