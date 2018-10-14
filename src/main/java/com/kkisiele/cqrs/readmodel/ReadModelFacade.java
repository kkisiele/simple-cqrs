package com.kkisiele.cqrs.readmodel;

import com.kkisiele.cqrs.FakeBus;

import java.util.Collections;
import java.util.List;

public final class ReadModelFacade {
    private final BullShitDatabase database = new BullShitDatabase();

    public ReadModelFacade(FakeBus bus) {
        bus.registerEventHandlers(new InventoryListView(database));
        bus.registerEventHandlers(new InventoryItemDetailView(database));
    }

    public List<InventoryItemListDto> getInventoryItems() {
        return Collections.unmodifiableList(database.list);
    }
}
