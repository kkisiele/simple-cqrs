package com.kkisiele.cqrs.infrastructure;

import com.kkisiele.cqrs.*;

import java.util.*;

public final class FakeBus implements EventPublisher, CommandSender {
    private final Map<Class<? extends Message>, List<MessageHandler>> routes = new HashMap<>();

    public <T extends Message> void registerHandler(Class<T> clazz, MessageHandler<T> handler) {
        var handlers = routes.computeIfAbsent(clazz, c -> new LinkedList<>());
        handlers.add(handler);
    }

    @Override
    public void publish(Event event) {
        routes.get(event.getClass()).forEach(messageHandler -> messageHandler.handle(event));
    }

    @Override
    public void send(Command command) {
        var handlers = routes.get(command.getClass());
        if(handlers == null) {
            throw new RuntimeException("No handler registered");
        }
        if(handlers.size() != 1) {
            throw new RuntimeException("Cannot send to more than one handler");
        }
        handlers.get(0).handle(command);
    }
}

