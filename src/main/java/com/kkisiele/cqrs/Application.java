package com.kkisiele.cqrs;

import com.kkisiele.cqrs.command.*;
import com.kkisiele.cqrs.domain.InventoryItem;
import com.kkisiele.cqrs.domain.Repository;
import com.kkisiele.cqrs.event.*;
import com.kkisiele.cqrs.infrastructure.EventStore;
import com.kkisiele.cqrs.infrastructure.FakeBus;
import com.kkisiele.cqrs.infrastructure.InMemoryEventStore;
import com.kkisiele.cqrs.readmodel.BullShitDatabase;
import com.kkisiele.cqrs.readmodel.InventoryItemDetailView;
import com.kkisiele.cqrs.readmodel.InventoryListView;
import com.kkisiele.cqrs.readmodel.ReadModelFacade;

public class Application {
    public final FakeBus bus;
    public final ReadModelFacade readModelFacade;

    public Application() {
        bus = new FakeBus();
        EventStore eventStore = new InMemoryEventStore(bus);
        Repository<InventoryItem> repository = new Repository<>(InventoryItem.class, eventStore);

        var commands = new CommandHandlers(repository);
        bus.registerHandler(CheckInItemsToInventory.class, commands::handle);
        bus.registerHandler(CreateInventoryItem.class, commands::handle);
        bus.registerHandler(DeactivateInventoryItem.class, commands::handle);
        bus.registerHandler(RemoveItemsFromInventory.class, commands::handle);
        bus.registerHandler(RenameInventoryItem.class, commands::handle);

        var database = new BullShitDatabase();
        readModelFacade = new ReadModelFacade(database);

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
    }
}
