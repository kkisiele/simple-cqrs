package com.kkisiele.cqrs.readmodel;

import com.kkisiele.cqrs.FakeBus;
import com.kkisiele.cqrs.domain.InventoryItemCreated;
import com.kkisiele.cqrs.domain.InventoryItemDeactivated;
import com.kkisiele.cqrs.domain.InventoryItemRenamed;
import com.kkisiele.cqrs.domain.ItemsCheckedInToInventory;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class InventoryItemDetailViewTest {
    private final BullShitDatabase database = new BullShitDatabase();
    private final FakeBus bus = new FakeBus();
    private final InventoryItemDetailView detailView = new InventoryItemDetailView(bus, database);
    private UUID id;

    @Before
    public void setup() {
        id = UUID.randomUUID();
        bus.publish(new InventoryItemCreated(id, "iphone"));
    }

    public InventoryItemDetailsDto itemDetails() {
        return database.details.get(id);
    }

    @Test
    public void inventoryItemIsCreated() {
        assertEquals(1, database.details.size());
        assertEquals(id, itemDetails().id);
        assertEquals("iphone", itemDetails().name);
    }

    @Test
    public void inventoryItemIsRemoved() {
        bus.publish(new InventoryItemDeactivated(id));
        assertEquals(0, database.details.size());
    }

    @Test
    public void inventoryItemIsRenamed() {
        bus.publish(new InventoryItemRenamed(id, "iphoneX"));
        assertEquals("iphoneX", itemDetails().name);
    }

    @Test
    public void inventoryItemsAreCheckedInToInventory() {
        assertEquals(0, itemDetails().currentCount);

        bus.publish(new ItemsCheckedInToInventory(id, 3));
        assertEquals(3, itemDetails().currentCount);

        bus.publish(new ItemsCheckedInToInventory(id, 2));
        assertEquals(5, itemDetails().currentCount);
    }
}
