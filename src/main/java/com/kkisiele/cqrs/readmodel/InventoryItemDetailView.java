package com.kkisiele.cqrs.readmodel;

import com.kkisiele.cqrs.FakeBus;
import com.kkisiele.cqrs.domain.InventoryItemCreated;
import com.kkisiele.cqrs.domain.InventoryItemDeactivated;
import com.kkisiele.cqrs.domain.InventoryItemRenamed;
import com.kkisiele.cqrs.domain.ItemsCheckedInToInventory;

final class InventoryItemDetailView {
    private final BullShitDatabase database;

    public InventoryItemDetailView(FakeBus bus, BullShitDatabase database) {
        this.database = database;
        bus.registerHandler(InventoryItemCreated.class, this::handle);
        bus.registerHandler(InventoryItemDeactivated.class, this::handle);
        bus.registerHandler(InventoryItemRenamed.class, this::handle);
        bus.registerHandler(ItemsCheckedInToInventory.class, this::handle);
    }

    private void handle(InventoryItemCreated event) {
        database.details.put(event.id(), new InventoryItemDetailsDto(event.id(), event.name(), 0, 0));
    }

    private void handle(InventoryItemDeactivated event) {
        database.details.remove(event.id());
    }

    private void handle(InventoryItemRenamed event) {
        InventoryItemDetailsDto itemDetails = database.details.get(event.id());
        itemDetails.name = event.newName();
    }

    private void handle(ItemsCheckedInToInventory event) {
        InventoryItemDetailsDto itemDetails = database.details.get(event.id());
        itemDetails.currentCount += event.count();
    }
}
