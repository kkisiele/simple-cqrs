package com.kkisiele.cqrs.readmodel;

import com.kkisiele.cqrs.FakeBus;
import com.kkisiele.cqrs.domain.InventoryItemCreated;
import com.kkisiele.cqrs.domain.InventoryItemDeactivated;
import com.kkisiele.cqrs.domain.InventoryItemRenamed;

final class InventoryListView {
    private final BullShitDatabase database;

    public InventoryListView(FakeBus bus, BullShitDatabase database) {
        this.database = database;
        bus.registerHandler(InventoryItemCreated.class, this::handle);
        bus.registerHandler(InventoryItemDeactivated.class, this::handle);
        bus.registerHandler(InventoryItemRenamed.class, this::handle);
    }

    private void handle(InventoryItemCreated event) {
        database.list.add(new InventoryItemListDto(event.id(), event.name()));
    }

    private void handle(InventoryItemDeactivated event) {
        database.list.removeIf(inventoryItem -> inventoryItem.id == event.id());
    }

    private void handle(InventoryItemRenamed event) {
        InventoryItemListDto itemList = database.list
                .stream()
                .filter(inventoryItem -> inventoryItem.id == event.id())
                .findFirst().get();
        itemList.name = event.newName();
    }
}
