package com.kkisiele.cqrs.command;

import com.kkisiele.cqrs.domain.InventoryItem;
import com.kkisiele.cqrs.domain.Repository;

public final class CommandHandlers {
    private final Repository<InventoryItem> repository;

    public CommandHandlers(Repository<InventoryItem> repository) {
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

    public void handle(RemoveItemsFromInventory command) {
        var item = repository.getById(command.id());
        item.remove(command.count());
        repository.save(item);
    }

    public void handle(RenameInventoryItem command) {
        var item = repository.getById(command.id());
        item.changeName(command.newName());
        repository.save(item);
    }
}
