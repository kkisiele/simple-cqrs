package com.kkisiele.cqrs.domain;

import com.kkisiele.cqrs.Event;

import java.util.UUID;

public final class InventoryItemCreated implements Event {
    private final UUID id;
    private final String name;

    public InventoryItemCreated(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID id() {
        return id;
    }

    public String name() {
        return name;
    }
}
