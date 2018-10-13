import com.kkisiele.cqrs.FakeBus;
import com.kkisiele.cqrs.InMemoryEventStore;
import com.kkisiele.cqrs.domain.InventoryItem;
import com.kkisiele.cqrs.domain.Repository;
import org.junit.Assert;
import org.junit.Test;

public class RepositoryTest {
    private final Repository<InventoryItem> repository = new Repository<>(InventoryItem.class, new InMemoryEventStore(new FakeBus()));

    @Test
    public void test() {
        InventoryItem inventoryItem = new InventoryItem("macbook");
        repository.save(inventoryItem);

        InventoryItem inventoryItem2 = repository.getById(inventoryItem.id());
        Assert.assertEquals(inventoryItem.id(), inventoryItem2.id());
        Assert.assertEquals(inventoryItem.isActivated(), inventoryItem2.isActivated());
    }
}
