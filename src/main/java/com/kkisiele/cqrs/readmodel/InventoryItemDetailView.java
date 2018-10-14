package com.kkisiele.cqrs.readmodel;

import com.kkisiele.cqrs.EventHandler;
import com.kkisiele.cqrs.domain.InventoryItemCreated;
import com.kkisiele.cqrs.domain.InventoryItemDeactivated;
import com.kkisiele.cqrs.domain.InventoryItemRenamed;
import com.kkisiele.cqrs.domain.ItemsCheckedInToInventory;

final class InventoryItemDetailView {
    private final BullShitDatabase database;

    public InventoryItemDetailView(BullShitDatabase database) {
        this.database = database;
    }

    @EventHandler
    private void handle(InventoryItemCreated event) {
        database.details.put(event.id(), new InventoryItemDetailsDto(event.id(), event.name(), 0, 0));
    }

    @EventHandler
    private void handle(InventoryItemDeactivated event) {
        database.details.remove(event.id());
    }

    @EventHandler
    private void handle(InventoryItemRenamed event) {
        InventoryItemDetailsDto itemDetails = database.details.get(event.id());
        itemDetails.name = event.newName();
    }

    @EventHandler
    private void handle(ItemsCheckedInToInventory event) {
        InventoryItemDetailsDto itemDetails = database.details.get(event.id());
        itemDetails.currentCount += event.count();
    }
}
