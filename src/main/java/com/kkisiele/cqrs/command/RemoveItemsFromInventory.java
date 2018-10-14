package com.kkisiele.cqrs.command;

import com.kkisiele.cqrs.Command;

import java.util.UUID;

public final class RemoveItemsFromInventory implements Command {
    private final UUID id;
    private final int count;

    public RemoveItemsFromInventory(UUID id, int count) {
        this.id = id;
        this.count = count;
    }

    public UUID id() {
        return id;
    }

    public int count() {
        return count;
    }
}
