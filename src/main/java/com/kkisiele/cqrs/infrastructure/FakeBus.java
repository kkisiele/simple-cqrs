package com.kkisiele.cqrs.infrastructure;

import com.kkisiele.cqrs.Message;
import com.kkisiele.cqrs.Command;
import com.kkisiele.cqrs.Event;

import java.util.*;

public class FakeBus implements EventPublisher, CommandSender {
    private final Map<Class<? extends Message>, List<MessageHandler>> handlers = new HashMap<>();

    public <T extends Message> void registerHandler(Class<T> cmd, MessageHandler<T> handler) {
        handlers.computeIfAbsent(cmd, aClass -> new LinkedList<>()).add(handler);
    }

    @Override
    public void publish(Event event) {
        handlers.get(event.getClass()).forEach(messageHandler -> messageHandler.handle(event));
    }

    @Override
    public void send(Command command) {
        List<MessageHandler> h = handlers.get(command.getClass());
        if(h == null) {
            throw new RuntimeException("No handler registered");
        }
        if(h.size() != 1) {
            throw new RuntimeException("Cannot send to more than one handler");
        }
        h.get(0).handle(command);
    }
}

