package com.kkisiele.cqrs.command;

import com.kkisiele.cqrs.Command;

public final class CreateInventoryItem implements Command {
    private final String name;

    public CreateInventoryItem(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }
}
