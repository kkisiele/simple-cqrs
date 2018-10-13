package com.kkisiele.cqrs.command;

import java.util.UUID;

public final class CheckInItemsToInventory implements Command {
    private final UUID id;
    private final int count;

    public CheckInItemsToInventory(UUID id, int count) {
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
