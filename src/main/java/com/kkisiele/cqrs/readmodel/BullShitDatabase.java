package com.kkisiele.cqrs.readmodel;

import java.util.*;

public final class BullShitDatabase {
    public List<InventoryItemListDto> list = new LinkedList<>();
    public Map<UUID, InventoryItemDetailsDto> details = new HashMap<>();
}
