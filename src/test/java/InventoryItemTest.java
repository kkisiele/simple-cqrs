import com.kkisiele.cqrs.domain.InventoryItem;
import org.junit.Assert;
import org.junit.Test;

public class InventoryItemTest {
    @Test
    public void itemIdentifierIsAssigned() {
        InventoryItem inventoryItem = new InventoryItem("bleee");
        Assert.assertNotNull(inventoryItem.id());
    }
}
