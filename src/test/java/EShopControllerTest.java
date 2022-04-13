import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shop.*;
import storage.NoItemInStorage;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class EShopControllerTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
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
    public void startShopAndDoProcessTest(){// procesni test 1, nakup itemu - mazani itemu - test nedostatku itemu na sklade
        EShopController.startEShop();

        /* make up an artificial data */
        final String info = "STORAGE IS CURRENTLY CONTAINING:\n";
        final String firstItemInfo ="STOCK OF ITEM:  Item   ID 1   NAME Dancing Panda v.2   CATEGORY GADGETS   PRICE 5000.0   LOYALTY POINTS 5    PIECES IN STORAGE: 10\n";
        final String secondItemInfo = "STOCK OF ITEM:  Item   ID 2   NAME Dancing Panda v.3 with USB port   CATEGORY GADGETS   PRICE 6000.0   LOYALTY POINTS 10    PIECES IN STORAGE: 10\n";
        final String thirdItemInfo = "STOCK OF ITEM:  Item   ID 4   NAME Star Wars Jedi buzzer   CATEGORY GADGETS   ORIGINAL PRICE 500.0    DISCOUNTED PRICE 35000.0  DISCOUNT FROM Thu Aug 01 00:00:00 CEST 2013    DISCOUNT TO Sun Dec 01 00:00:00 CET 2013    PIECES IN STORAGE: 4\n";



        final String expectedString = info + firstItemInfo + secondItemInfo + thirdItemInfo;
        int[] itemCount = {10,10,4};


        Item[] storageItems = {
                new StandardItem(1, "Dancing Panda v.2", 5000, "GADGETS", 5),
                new StandardItem(2, "Dancing Panda v.3 with USB port", 6000, "GADGETS", 10),
                new DiscountedItem(4, "Star Wars Jedi buzzer", 500, "GADGETS", 30, "1.8.2013", "1.12.2013"),
        };

        // insert data to the storage
        for (int i = 0; i < storageItems.length; i++) {
           EShopController.getStorage().insertItems(storageItems[i], itemCount[i]);
        }

        Assertions.assertEquals(10, EShopController.getStorage().getItemCount(1));
        Assertions.assertEquals(10, EShopController.getStorage().getItemCount(2));
        Assertions.assertEquals(4, EShopController.getStorage().getItemCount(4));


        EShopController.getStorage().printListOfStoredItems();
        Assertions.assertEquals(expectedString, outContent.toString());

        ShoppingCart newCart = new ShoppingCart();
        newCart.addItem(storageItems[0]);

        Assertions.assertEquals(storageItems[0], newCart.getCartItems().get(0));


        int itemId = storageItems[0].getID();
        newCart.removeItem(itemId);

        Assertions.assertEquals(0, newCart.getCartItems().size());

        newCart.addItem(storageItems[0]);

        final String customerName = "customerName";
        final String customerAddress = "customerAddress";


        try {
            EShopController.purchaseShoppingCart(newCart, customerName, customerAddress);
            Assertions.assertEquals(customerName,EShopController.getArchive().getOrderArchive().get(0).getCustomerName());
            Assertions.assertEquals(customerAddress,EShopController.getArchive().getOrderArchive().get(0).getCustomerAddress());
            Assertions.assertEquals(storageItems[0], EShopController.getArchive().getOrderArchive().get(0).getItems().get(0));
            Assertions.assertEquals(9, EShopController.getStorage().getItemCount(storageItems[0]));
        } catch (NoItemInStorage noItemInStorage) {
            noItemInStorage.printStackTrace();
        }


        ShoppingCart newCart2 = new ShoppingCart();

        for (int i = 0; i <= 5; i++) {
            newCart2.addItem(storageItems[2]);
        }
        outContent.reset();
        try {
            EShopController.purchaseShoppingCart(newCart2, customerName, customerAddress); // zkusim koupit 6 itemu, v storage mam jen 4
        } catch (NoItemInStorage noItemInStorage) {
            Assertions.assertEquals("No item in storage", noItemInStorage.getMessage());
        }


    }

    @Test
    public void startShopAndPurchaseEmptyShoppingCart(){// procesni test 1, empty shopping cart
        EShopController.startEShop();
        ShoppingCart newCart = new ShoppingCart();
        final String customerName = "customerName";
        final String customerAddress = "customerAddress";


        String expectedError = "Error: shopping cart is empty\n";


        try {
            EShopController.purchaseShoppingCart(newCart, customerName, customerAddress);
            Assertions.assertEquals(expectedError,outContent.toString());
        } catch (NoItemInStorage noItemInStorage) {
            noItemInStorage.printStackTrace();
        }


    }
}
