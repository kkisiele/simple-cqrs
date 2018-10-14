package com.kkisiele.cqrs.readmodel;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public final class ReadModelFacade {
    private final BullShitDatabase database;

    public ReadModelFacade(BullShitDatabase database) {
        this.database = database;
    }

    public List<InventoryItemListDto> getInventoryItems() {
        return Collections.unmodifiableList(database.list);
    }

    public InventoryItemDetailsDto getInventoryItemDetails(UUID id) {
        return database.details.get(id);
    }
}
