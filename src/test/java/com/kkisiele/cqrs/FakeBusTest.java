package com.kkisiele.cqrs;

import com.kkisiele.cqrs.command.CreateInventoryItem;
import com.kkisiele.cqrs.command.DeactivateInventoryItem;
import com.kkisiele.cqrs.command.InventoryCommandHandlers;
import com.kkisiele.cqrs.domain.Event;
import com.kkisiele.cqrs.domain.InventoryItem;
import com.kkisiele.cqrs.domain.InventoryItemCreated;
import com.kkisiele.cqrs.domain.Repository;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class FakeBusTest {
    private FakeBus fakeBus = new FakeBus();
    private InMemoryEventStore eventStore = new InMemoryEventStore(fakeBus);
    private Repository<InventoryItem> repository = new Repository<>(InventoryItem.class, eventStore);
    private InventoryCommandHandlers commandHandlers = new InventoryCommandHandlers(repository);

    @Before
    public void setup() {
        fakeBus.registerHandler(CreateInventoryItem.class, commandHandlers::handle);
        fakeBus.registerHandler(DeactivateInventoryItem.class, commandHandlers::handle);
    }

    @Test
    public void createInventoryItem() {
        fakeBus.send(new CreateInventoryItem("iphone"));
        assertEquals(1, eventStore.aggregateIds().size());
    }

    @Test
    public void eventsAreHandled() {
        List<Event> receivedEvents = new ArrayList<>();
        Event event = new InventoryItemCreated(UUID.randomUUID(), "iphone");

        fakeBus.registerHandler(event.getClass(), message -> receivedEvents.add(message));
        fakeBus.publish(event);

        assertEquals(1, receivedEvents.size());
        assertEquals(event, receivedEvents.get(0));
    }
}
