package com.kkisiele.cqrs.readmodel;

import java.util.*;

public final class BullShitDatabase {
    public final List<InventoryItemListDto> list = new LinkedList<>();
    public final Map<UUID, InventoryItemDetailsDto> details = new HashMap<>();
}
