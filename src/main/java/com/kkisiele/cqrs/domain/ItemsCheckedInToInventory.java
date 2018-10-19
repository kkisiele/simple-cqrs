package com.kkisiele.cqrs.domain;

import com.kkisiele.cqrs.Event;

import java.util.UUID;

public final class ItemsCheckedInToInventory implements Event {
    private final UUID id;
    private final int count;

    public ItemsCheckedInToInventory(UUID id, int count) {
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
