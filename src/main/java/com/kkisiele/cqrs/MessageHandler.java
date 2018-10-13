package com.kkisiele.cqrs;

public interface MessageHandler<T extends Message> {
    void handle(T message);
}
