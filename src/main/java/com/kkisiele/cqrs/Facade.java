package com.kkisiele.cqrs;

import com.kkisiele.cqrs.command.CreateInventoryItem;
import com.kkisiele.cqrs.command.DeactivateInventoryItem;
import com.kkisiele.cqrs.command.InventoryCommandHandlers;
import com.kkisiele.cqrs.domain.InventoryItem;
import com.kkisiele.cqrs.domain.Repository;

public class Facade {
    private FakeBus fakeBus = new FakeBus();
    private InMemoryEventStore eventStore = new InMemoryEventStore();
    private Repository<InventoryItem> repository = new Repository<>(InventoryItem.class, eventStore, fakeBus);
    private InventoryCommandHandlers commandHandlers = new InventoryCommandHandlers(repository);

    public Facade() {
        fakeBus.registerHandler(CreateInventoryItem.class, commandHandlers::handle);
        fakeBus.registerHandler(DeactivateInventoryItem.class, commandHandlers::handle);
    }


}
