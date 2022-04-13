import archive.ItemPurchaseArchiveEntry;
import archive.PurchasesArchive;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import shop.Item;
import shop.Order;
import shop.ShoppingCart;
import shop.StandardItem;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;


public class PurchasesArchiveTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;


    @Test
    public void printItemPurchaseStatisticsTest() {

    }

    @Test
    public void getHowManyTimesHasBeenItemSold() {
        StandardItem item = new StandardItem(1, "name", 350, "category", 5);
        PurchasesArchive purchasesArchive = new PurchasesArchive();
        Assertions.assertEquals(0, purchasesArchive.getHowManyTimesHasBeenItemSold(item));


        final ShoppingCart cart = new ShoppingCart();
        final String customerName = "customer";
        final String customerAddress = "customerAddress";
        Order order = new Order(cart, customerName, customerAddress);
        ArrayList<Order> orderItems = new ArrayList<>();
        orderItems.add(order);
        ArrayList<Item> itemsArr = new ArrayList<>();
        itemsArr.add(item);
        order.setItems(itemsArr);

        ItemPurchaseArchiveEntry itemPurchaseArchiveEntry = new ItemPurchaseArchiveEntry(item);
        HashMap<Integer, ItemPurchaseArchiveEntry> itemPurchaseArchive = new HashMap<>();

        itemPurchaseArchive.put(item.getID(), itemPurchaseArchiveEntry);

        PurchasesArchive purchasesArchive2 = new PurchasesArchive(itemPurchaseArchive, orderItems);

        Assertions.assertEquals(1, purchasesArchive2.getHowManyTimesHasBeenItemSold(item));
    }

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void printOutput_stdOutRedirected_correctMessageCaptured() {//test println
        System.out.println("hello");
        assertEquals("hello\n", outContent.toString());

    }

    @Test
    public void doPrintItemPurchaseStatistics(){
        ArrayList<Order> mockArrayListOrders = Mockito.mock(ArrayList.class);

        ItemPurchaseArchiveEntry mockItemPurchaseArchiveEntry = Mockito.mock(ItemPurchaseArchiveEntry.class);

        HashMap<Integer, ItemPurchaseArchiveEntry> itemPurchaseArchive = new HashMap<>();

        PurchasesArchive purchasesArchive = new PurchasesArchive(itemPurchaseArchive, mockArrayListOrders);

        purchasesArchive.printItemPurchaseStatistics();

        assertEquals("ITEM PURCHASE STATISTICS:\n", outContent.toString());
    }

    @Test
    public void doPutOrderToPurchasesArchive(){
        final ShoppingCart cart = new ShoppingCart();
        final String customerName = "customer";
        final String customerAddress = "customerAddress";
        Order order = new Order(cart, customerName, customerAddress);

        StandardItem standardItemMock = Mockito.mock(StandardItem.class);

        ArrayList<Item> itemsArr = new ArrayList<>();
        itemsArr.add(standardItemMock);
        order.setItems(itemsArr);

        PurchasesArchive purchasesArchive = new PurchasesArchive();
        Assertions.assertEquals(0, purchasesArchive.getHowManyTimesHasBeenItemSold(standardItemMock));

        purchasesArchive.putOrderToPurchasesArchive(order);

        Assertions.assertEquals(1, purchasesArchive.getHowManyTimesHasBeenItemSold(standardItemMock));
        Assertions.assertEquals(1, purchasesArchive.getItemPurchaseArchive().size());


        purchasesArchive.putOrderToPurchasesArchive(order);

        Assertions.assertEquals(2, purchasesArchive.getHowManyTimesHasBeenItemSold(standardItemMock));

        Assertions.assertEquals(1, purchasesArchive.getItemPurchaseArchive().size());



    }
}
