package com.kkisiele.cqrs;

@FunctionalInterface
public interface MessageHandler<T extends Message> {
    void handle(T message);
}
