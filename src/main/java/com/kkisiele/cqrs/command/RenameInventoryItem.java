package com.kkisiele.cqrs.command;

import com.kkisiele.cqrs.Command;

import java.util.UUID;

public final class RenameInventoryItem implements Command {
    private final UUID id;
    private final String newName;

    public RenameInventoryItem(UUID id, String newName) {
        this.id = id;
        this.newName = newName;
    }

    public UUID id() {
        return id;
    }

    public String newName() {
        return newName;
    }
}
