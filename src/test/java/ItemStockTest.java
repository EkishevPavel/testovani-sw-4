import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import shop.Item;
import shop.StandardItem;
import storage.ItemStock;

public class ItemStockTest {
    @Test
    public void createItemStock(){
        final int id = 10;
        final String name = "name";
        final float price = 3000;
        final String category = "category";
        final int loyaltyPoints = 10;
        final int count = 0;
        StandardItem itemForItemStock = new StandardItem(id, name, price, category, loyaltyPoints);
        ItemStock itemStock = new ItemStock(itemForItemStock);
        Item itemFromItemStock = itemStock.getItem();
        Assertions.assertEquals(itemForItemStock, itemFromItemStock);
        Assertions.assertEquals(count, itemStock.getCount());
    }

    @ParameterizedTest
    @ValueSource(ints = {1,-1,10})
    public void doIncreaseItemCount(int a){
        StandardItem itemForItemStock = new StandardItem(1, "name", 350, "category", 5);
        ItemStock itemStock = new ItemStock(itemForItemStock);
        int expectedItemCount = itemStock.getCount() + a;
        itemStock.IncreaseItemCount(a);
        Assertions.assertEquals(expectedItemCount, itemStock.getCount());
    }
    @ParameterizedTest
    @ValueSource(ints = {4,0,-2})
    public void doDecreaseItemCount(int a){
        final int startCount = 5;
        StandardItem itemForItemStock = new StandardItem(1, "name", 350, "category", 5);
        ItemStock itemStock = new ItemStock(itemForItemStock);
        itemStock.IncreaseItemCount(startCount);

        int expectedItemCount = itemStock.getCount() - a;
        itemStock.decreaseItemCount(a);
        Assertions.assertEquals(expectedItemCount, itemStock.getCount());
    }
}
