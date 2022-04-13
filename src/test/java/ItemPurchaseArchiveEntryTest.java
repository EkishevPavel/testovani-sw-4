import archive.ItemPurchaseArchiveEntry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import shop.StandardItem;
import org.mockito.Mockito;

public class ItemPurchaseArchiveEntryTest {
    @Test
    public void createItemPurchaseArchiveEntry(){
        StandardItem standardItem = Mockito.mock(StandardItem.class);
        ItemPurchaseArchiveEntry itemPurchaseArchiveEntry = new ItemPurchaseArchiveEntry(standardItem);

        Assertions.assertEquals(standardItem, itemPurchaseArchiveEntry.getRefItem());
        Assertions.assertEquals(1, itemPurchaseArchiveEntry.getSoldCount());
    }
    @Test
    public void createItemPurchaseArchiveEntryMock(){
        ItemPurchaseArchiveEntry itemPurchaseArchiveEntryMock = Mockito.mock(ItemPurchaseArchiveEntry.class);
        Assertions.assertNull(itemPurchaseArchiveEntryMock.getRefItem());
        Assertions.assertEquals(0, itemPurchaseArchiveEntryMock.getSoldCount());
    }
}
