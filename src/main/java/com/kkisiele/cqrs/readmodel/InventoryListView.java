package com.kkisiele.cqrs.readmodel;

import com.kkisiele.cqrs.domain.InventoryItemCreated;
import com.kkisiele.cqrs.domain.InventoryItemDeactivated;
import com.kkisiele.cqrs.domain.InventoryItemRenamed;

public final class InventoryListView {
    private final BullShitDatabase database;

    public InventoryListView(BullShitDatabase database) {
        this.database = database;
    }

    public void handle(InventoryItemCreated event) {
        database.list.add(new InventoryItemListDto(event.id(), event.name()));
    }

    public void handle(InventoryItemDeactivated event) {
        database.list.removeIf(inventoryItem -> inventoryItem.id == event.id());
    }

    public void handle(InventoryItemRenamed event) {
        InventoryItemListDto itemList = database.list
                .stream()
                .filter(inventoryItem -> inventoryItem.id == event.id())
                .findFirst().get();
        itemList.name = event.newName();
    }
}
