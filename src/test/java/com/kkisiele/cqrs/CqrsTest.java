package com.kkisiele.cqrs;

import com.kkisiele.cqrs.command.*;
import com.kkisiele.cqrs.readmodel.InventoryItemDetailsDto;
import com.kkisiele.cqrs.readmodel.InventoryItemListDto;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CqrsTest {
    private final Application app = new Application();

    @Test
    public void createInventoryItem() {
        app.command.send(new CreateInventoryItem("iPhone"));

        assertInventoryItem("iPhone", 0);
    }

    @Test
    public void deactivateInventoryItem() {
        app.command.send(new CreateInventoryItem("iPhone"));
        assertInventoryItem("iPhone", 0);

        UUID id = app.readModelFacade.getInventoryItems().get(0).id;
        app.command.send(new DeactivateInventoryItem(id));
        assertEquals(0, app.readModelFacade.getInventoryItems().size());
        assertNull(app.readModelFacade.getInventoryItemDetails(id));
    }

    @Test
    public void renameInventoryItem() {
        app.command.send(new CreateInventoryItem("iPhone"));
        assertInventoryItem("iPhone", 0);

        UUID id = app.readModelFacade.getInventoryItems().get(0).id;
        app.command.send(new RenameInventoryItem(id, "iPhoneX"));
        assertInventoryItem("iPhoneX", 0);
    }

    @Test
    public void checkInAndRemoveItemsToInventory() {
        app.command.send(new CreateInventoryItem("iPhone"));
        assertInventoryItem("iPhone", 0);

        UUID id = app.readModelFacade.getInventoryItems().get(0).id;

        app.command.send(new CheckInItemsToInventory(id, 3));
        assertInventoryItem("iPhone", 3);

        app.command.send(new CheckInItemsToInventory(id, 2));
        assertInventoryItem("iPhone", 5);

        app.command.send(new RemoveItemsFromInventory(id, 1));
        assertInventoryItem("iPhone", 4);

        app.command.send(new RemoveItemsFromInventory(id, 4));
        assertInventoryItem("iPhone", 0);
    }

    @Test(expected = IllegalStateException.class)
    public void removeItemsToInventory() {
        app.command.send(new CreateInventoryItem("iPhone"));
        assertInventoryItem("iPhone", 0);

        UUID id = app.readModelFacade.getInventoryItems().get(0).id;
        app.command.send(new RemoveItemsFromInventory(id, 1));
    }

    private void assertInventoryItem(String name, int count) {
        assertEquals(1, app.readModelFacade.getInventoryItems().size());
        InventoryItemListDto itemList = app.readModelFacade.getInventoryItems().get(0);
        assertEquals(name, itemList.name);
        InventoryItemDetailsDto itemDetails = app.readModelFacade.getInventoryItemDetails(itemList.id);
        assertEquals(name, itemDetails.name);
        assertEquals(count, itemDetails.currentCount);
    }
}
