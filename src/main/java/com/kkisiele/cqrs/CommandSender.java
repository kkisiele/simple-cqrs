package com.kkisiele.cqrs;

import com.kkisiele.cqrs.command.Command;

public interface CommandSender {
    <T extends Command> void send(T command);
}
