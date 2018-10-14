package com.kkisiele.cqrs.domain;

import com.kkisiele.cqrs.event.*;

import java.util.UUID;

public final class InventoryItem extends AggregateRoot {
    private UUID id;
    private boolean activated;
    private int count;

    public InventoryItem() {
    }

    public InventoryItem(String name) {
        applyChange(new InventoryItemCreated(UUID.randomUUID(), name));
    }

    private void handle(InventoryItemCreated event) {
        id = event.id();
        activated = true;
        count = 0;
    }

    @Override
    public UUID id() {
        return id;
    }

    public boolean isActivated() {
        return activated;
    }

    public void deactivate() {
        if(!activated) {
            throw new IllegalStateException("Already deactivated");
        }
        applyChange(new InventoryItemDeactivated(id));
    }

    private void handle(InventoryItemDeactivated event) {
        activated = false;
    }

    public void checkIn(int c) {
        if(c <= 0) {
            throw new IllegalArgumentException("Must have a count greater than 0 to add to inventory");
        }
        applyChange(new ItemsCheckedInToInventory(id, c));
    }

    private void handle(ItemsCheckedInToInventory event) {
        count += event.count();
    }

    public void remove(int c) {
        if(c <= 0) {
            throw new IllegalArgumentException("Cant remove negative count from inventory");
        }
        if(c > count) {
            throw new IllegalStateException("");
        }

        applyChange(new ItemsRemovedFromInventory(id, c));
    }

    private void handle(ItemsRemovedFromInventory event) {
        count -= event.count();
    }

    public void changeName(String newName) {
        if(newName == null || newName.trim().length() == 0) {
            throw new IllegalArgumentException("Name must be provided");
        }
        applyChange(new InventoryItemRenamed(id, newName));
    }
}
