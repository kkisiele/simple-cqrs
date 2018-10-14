package com.kkisiele.cqrs;

public interface CommandSender {
    void send(Command command);
}
