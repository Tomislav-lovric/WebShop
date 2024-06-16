import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author luka
 */
public interface IShoppingCart {

    /**
     *
     * @return Unique identifier of the shopping cart
     */
    Long getId();

    /**
     *
     * @return Owner of the shopping cart
     */
    String getUser();

    /**
     *
     * @return Date and time of shopping cart creation
     */
    Date getCreatedTime();

    /**
     *
     * @return List of all items in the shopping cart
     * List returns all items sorted alphabetically by Producer and Product name
     */
    List<IShoppingItem> getItems();
    
    /**
     *
     * @return Total price of the shopping cart
     */
    BigDecimal getTotalPrice();

    /**
     *
     * @param product Product to put into the shopping cart
     * @param quantity Quantity of selected product
     * @return True if the operation was successful, else False
     * 
     * This function must assure that every product in shopping cart don't have 
     * duplicate items. One product, one ShoppingCartItem. 
     * 
     */
    boolean addItem(IProduct product, BigDecimal quantity);

    /**
     *
     * @param product Product to be removed from the shopping cart
     * @param quantity Quantity to be deducted from the shopping cart item
     * @return True if the operation was successful, else False
     * 
     * This function must assure that quantity of the shopping cart item can't 
     * be less then zero. If the shopping cart quantity is zero, shopping cart item
     * must be removed from the items list.
     */
    boolean removeItem(IProduct product, BigDecimal quantity);

    /**
     * Updates the shopping cart items with actual price and quantity. If the
     * quantity of shopping card item is greater the stock amount of the product, 
     * quantity must be updated with the current stock amount.
     */
    void refresh();

    /**
     * 
     * @return True if the operation was successful else False
     * 
     * Updates the stock amount of the products, and invalidates current shopping
     * cart
     */
    boolean checkOut();
    
    List<IOrder> getOrderHistory();

    BigDecimal getTotalPriceWithPromotionCode(String code);
}




