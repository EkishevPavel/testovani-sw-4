import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import shop.StandardItem;

public class StandardItemTest {
    @Order(1)
    @Test
    public void createStandardItem(){
        final int id = 5;
        final String name = "item";
        final float price = 3500;
        final String category = "itemCategory";
        final int loyaltyPoints = 10;
        StandardItem standardItem = new StandardItem(id,name,price,category,loyaltyPoints);
        Assertions.assertEquals(id, standardItem.getID());
        Assertions.assertEquals(name, standardItem.getName());
        Assertions.assertEquals(price, standardItem.getPrice());
        Assertions.assertEquals(category, standardItem.getCategory());
        Assertions.assertEquals(loyaltyPoints, standardItem.getLoyaltyPoints());
    }
    @Order(2)
    @Test
    public void makeCopy(){
        final int id = 10;
        final String name = "item1";
        final float price = 300;
        final String category = "itemCategory1";
        final int loyaltyPoints = 1;
        StandardItem standardItem = new StandardItem(id,name,price,category,loyaltyPoints);
        StandardItem standardItemCopy = standardItem.copy();
        Assertions.assertEquals(standardItem, standardItemCopy);
    }
    @ParameterizedTest
    @CsvSource({"true, 10, 10", "false, 11, 11"})
    public void testEquals(boolean data, int loyaltyInput, int id){
        final String name = "item1";
        final float price = 300;
        final String category = "itemCategory1";
        final int loyaltyPoints = 10;
        StandardItem standardItem = new StandardItem(id,name,price,category,loyaltyPoints);
        StandardItem standardItem2 = new StandardItem(id,name,price,category,loyaltyInput);
        boolean equalRez = standardItem.equals(standardItem2);
        Assertions.assertEquals(data, equalRez);
    }
}
