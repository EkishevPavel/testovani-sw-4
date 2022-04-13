import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import shop.Order;
import shop.ShoppingCart;

public class OrderTest {
    @Test
    public void createOrderWithDefaultState(){
        ShoppingCart cart = new ShoppingCart();
        final String customerName = "customer";
        String customerAddress = "customerAddress";
        Order order = new Order(cart, customerName, customerAddress);
        Assertions.assertEquals(cart.getCartItems() , order.getItems());
        Assertions.assertEquals(customerName, order.getCustomerName());
        Assertions.assertEquals(customerAddress, order.getCustomerAddress());

    }
    @Test
    public void createOrder(){
        final ShoppingCart cart = new ShoppingCart();
        final String customerName = "customer";
        final String customerAddress = "customerAddress";
        final int state = 10;
        Order order = new Order(cart, customerName, customerAddress, state);
        Assertions.assertEquals(cart.getCartItems() , order.getItems());
        Assertions.assertEquals(customerName, order.getCustomerName());
        Assertions.assertEquals(customerAddress, order.getCustomerAddress());
        Assertions.assertEquals(state, order.getState());
    }

}
