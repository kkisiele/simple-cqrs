package com.kkisiele.cqrs;

import com.kkisiele.cqrs.Command;

public interface CommandSender {
    void send(Command command);
}
