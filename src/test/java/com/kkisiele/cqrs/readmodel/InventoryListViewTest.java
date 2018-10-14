package com.kkisiele.cqrs.readmodel;

import com.kkisiele.cqrs.FakeBus;
import com.kkisiele.cqrs.domain.InventoryItemCreated;
import com.kkisiele.cqrs.domain.InventoryItemDeactivated;
import com.kkisiele.cqrs.domain.InventoryItemRenamed;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class InventoryListViewTest {
    private final BullShitDatabase database = new BullShitDatabase();
    private final FakeBus bus = new FakeBus();
    private UUID id;

    @Before
    public void setup() {
        id = UUID.randomUUID();
        bus.registerEventHandlers(new InventoryListView(database));
        bus.publish(new InventoryItemCreated(id, "iphone"));
    }

    @Test
    public void inventoryItemIsCreated() {
        assertEquals(1, database.list.size());
        InventoryItemListDto listItem = database.list.get(0);
        assertEquals(id, listItem.id);
        assertEquals("iphone", listItem.name);
    }

    @Test
    public void inventoryItemIsRemoved() {
        bus.publish(new InventoryItemDeactivated(id));
        assertEquals(0, database.list.size());
    }

    @Test
    public void inventoryItemIsRenamed() {
        bus.publish(new InventoryItemRenamed(id, "iphoneX"));
        assertEquals("iphoneX", database.list.get(0).name);
    }
}
