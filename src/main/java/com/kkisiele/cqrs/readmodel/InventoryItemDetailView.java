package com.kkisiele.cqrs.readmodel;

import com.kkisiele.cqrs.domain.*;

public final class InventoryItemDetailView {
    private final BullShitDatabase database;

    public InventoryItemDetailView(BullShitDatabase database) {
        this.database = database;
    }

    public void handle(InventoryItemCreated event) {
        database.details.put(event.id(), new InventoryItemDetailsDto(event.id(), event.name(), 0, 0));
    }

    public void handle(InventoryItemDeactivated event) {
        database.details.remove(event.id());
    }

    public void handle(InventoryItemRenamed event) {
        InventoryItemDetailsDto itemDetails = database.details.get(event.id());
        itemDetails.name = event.newName();
    }

    public void handle(ItemsCheckedInToInventory event) {
        InventoryItemDetailsDto itemDetails = database.details.get(event.id());
        itemDetails.currentCount += event.count();
    }

    public void handle(ItemsRemovedFromInventory event) {
        InventoryItemDetailsDto itemDetails = database.details.get(event.id());
        itemDetails.currentCount -= event.count();
    }
}
