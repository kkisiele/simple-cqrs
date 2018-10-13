package com.kkisiele.cqrs.command;

import com.kkisiele.cqrs.domain.InventoryItem;
import com.kkisiele.cqrs.domain.Repository;

public class InventoryCommandHandlers {
    private final Repository<InventoryItem> repository;

    public InventoryCommandHandlers(Repository<InventoryItem> repository) {
        this.repository = repository;
    }

    public void handle(CreateInventoryItem command) {
        var item = new InventoryItem(command.name());
        repository.save(item);
    }

    public void handle(DeactivateInventoryItem command) {
        var item = repository.getById(command.id());
        item.deactivate();
        repository.save(item);
    }

    public void handle(CheckInItemsToInventory command) {
        var item = repository.getById(command.id());
        item.checkIn(command.count());
        repository.save(item);

    }
}
