package com.kkisiele.cqrs.infrastructure;

import com.kkisiele.cqrs.Command;

public interface CommandSender {
    void send(Command command);
}
