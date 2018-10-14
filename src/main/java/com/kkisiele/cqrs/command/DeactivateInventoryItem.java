package com.kkisiele.cqrs.command;

import com.kkisiele.cqrs.Command;

import java.util.UUID;

public final class DeactivateInventoryItem implements Command {
    private final UUID id;

    public DeactivateInventoryItem(UUID id) {
        this.id = id;
    }

    public UUID id() {
        return id;
    }
}
