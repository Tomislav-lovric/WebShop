import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface IOrder {
    Long getId();
    String getUser();
    Date getOrderDate();
    List<IShoppingItem> getItems();
    BigDecimal getTotalPrice();
}
