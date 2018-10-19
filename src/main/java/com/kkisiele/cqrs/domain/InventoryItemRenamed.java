package com.kkisiele.cqrs.domain;

import com.kkisiele.cqrs.Event;

import java.util.UUID;

public final class InventoryItemRenamed implements Event {
    private final UUID id;
    private final String newName;

    public InventoryItemRenamed(UUID id, String newName) {
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
