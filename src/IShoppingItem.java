import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author luka
 */
public interface IShoppingItem {

    /**
     *
     * @return Unique identifier of the shopping item
     */
    Long getId();

    /**
     *
     * @return Selected product in shopping cart
     */
    IProduct getProduct();

    /**
     *
     * @return Selected quantity of the product
     */
    BigDecimal getQuantity();

    /**
     *
     * @return Single price of the product 
     */
    BigDecimal getPrice();

    /**
     *
     * @return Last modification time and date of the item
     */
    Date getLastModifyTime();

    /**
     *
     * @return Total price amount for this item 
     */
    BigDecimal getTotalAmount();

    void setQuantity(BigDecimal quantity);

    void setPrice(BigDecimal price);
    
}




