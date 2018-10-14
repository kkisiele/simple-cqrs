package com.kkisiele.cqrs;

import com.kkisiele.cqrs.command.Command;
import com.kkisiele.cqrs.domain.Event;

import java.util.*;

public class FakeBus implements EventPublisher, CommandSender {
    private final Map<Class, List<MessageHandler>> handlers = new HashMap<>();
    private final List<Object> instances = new ArrayList<>();

    public void registerEventHandlers(Object instance) {
        instances.add(instance);
    }

    public <T extends Message> void registerHandler(Class<T> cmd, MessageHandler<T> handler) {
        handlers.put(cmd, Arrays.asList(handler));
    }

    @Override
    public void publish(Event event) {
        for(MessageHandler handler : handlers.getOrDefault(event.getClass(), Collections.emptyList())) {
            handler.handle(event);
        }

        for(Object i : instances) {
            new EventHandle(i).dispatch(event);
        }
    }

    @Override
    public <T extends Command> void send(T command) {
        handlers.get(command.getClass()).get(0).handle(command);
    }
}

