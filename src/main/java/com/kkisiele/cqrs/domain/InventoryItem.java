package com.kkisiele.cqrs.domain;

import com.kkisiele.cqrs.EventHandler;

import java.util.UUID;

public final class InventoryItem extends AggregateRoot {
    private UUID id;
    private boolean activated;

    public InventoryItem() {
    }

    public InventoryItem(String name) {
        applyChange(new InventoryItemCreated(UUID.randomUUID(), name));
    }

    @EventHandler
    private void handle(InventoryItemCreated event) {
        id = event.id();
        activated = true;
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

    @EventHandler
    private void handle(InventoryItemDeactivated event) {
        activated = false;
    }

    public void checkIn(int count) {
        if(count <= 0) {
            throw new IllegalArgumentException("Must have a count greater than 0 to add to inventory");
        }
        applyChange(new ItemsCheckedInToInventory(id, count));
    }

    public void changeName(String newName) {
        if(newName == null || newName.trim().length() == 0) {
            throw new IllegalArgumentException("Name must be provided");
        }
        applyChange(new InventoryItemRenamed(id, newName));
    }
}
