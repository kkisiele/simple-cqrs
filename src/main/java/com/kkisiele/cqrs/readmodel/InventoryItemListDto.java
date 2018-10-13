package com.kkisiele.cqrs.readmodel;

import java.util.UUID;

public final class InventoryItemListDto {
    public UUID id;
    public String name;

    public InventoryItemListDto(UUID id, String name) {
        this.id = id;
        this.name = name;
    }
}
