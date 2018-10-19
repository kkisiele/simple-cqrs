package com.kkisiele.cqrs;

import com.kkisiele.cqrs.command.*;
import com.kkisiele.cqrs.domain.*;
import com.kkisiele.cqrs.infrastructure.FakeBus;
import com.kkisiele.cqrs.infrastructure.InMemoryEventStore;
import com.kkisiele.cqrs.readmodel.BullShitDatabase;
import com.kkisiele.cqrs.readmodel.InventoryItemDetailView;
import com.kkisiele.cqrs.readmodel.InventoryListView;
import com.kkisiele.cqrs.readmodel.ReadModelFacade;

public class Application {
    public final CommandSender command;
    public final ReadModelFacade readModelFacade;

    public Application() {
        var bus = new FakeBus();
        var repository = new Repository<>(InventoryItem.class, new InMemoryEventStore(bus));
        var database = new BullShitDatabase();

        var commands = new CommandHandlers(repository);
        bus.registerHandler(CheckInItemsToInventory.class, commands::handle);
        bus.registerHandler(CreateInventoryItem.class, commands::handle);
        bus.registerHandler(DeactivateInventoryItem.class, commands::handle);
        bus.registerHandler(RemoveItemsFromInventory.class, commands::handle);
        bus.registerHandler(RenameInventoryItem.class, commands::handle);

        var detail = new InventoryItemDetailView(database);
        bus.registerHandler(InventoryItemCreated.class, detail::handle);
        bus.registerHandler(InventoryItemDeactivated.class, detail::handle);
        bus.registerHandler(InventoryItemRenamed.class, detail::handle);
        bus.registerHandler(ItemsCheckedInToInventory.class, detail::handle);
        bus.registerHandler(ItemsRemovedFromInventory.class, detail::handle);

        var list = new InventoryListView(database);
        bus.registerHandler(InventoryItemCreated.class, list::handle);
        bus.registerHandler(InventoryItemRenamed.class, list::handle);
        bus.registerHandler(InventoryItemDeactivated.class, list::handle);

        readModelFacade = new ReadModelFacade(database);
        command = bus;
    }
}
