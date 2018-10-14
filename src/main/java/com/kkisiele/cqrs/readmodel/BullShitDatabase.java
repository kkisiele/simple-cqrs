package com.kkisiele.cqrs.readmodel;

import java.util.*;

class BullShitDatabase {
    public List<InventoryItemListDto> list = new LinkedList<>();
    public Map<UUID, InventoryItemDetailsDto> details = new HashMap<>();
}
