package com.kkisiele.cqrs.infrastructure;

import com.kkisiele.cqrs.Message;

@FunctionalInterface
public interface MessageHandler<T extends Message> {
    void handle(T message);
}
