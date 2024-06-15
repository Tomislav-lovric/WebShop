import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Order implements IOrder{
    private Long id;
    private String user;
    private Date orderDate;
    private List<IShoppingItem> items;
    private BigDecimal totalPrice;

    public Order(Long id, String user, Date orderDate, List<IShoppingItem> items, BigDecimal totalPrice) {
        this.id = id;
        this.user = user;
        this.orderDate = orderDate;
        this.items = items;
        this.totalPrice = totalPrice;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getUser() {
        return user;
    }

    @Override
    public Date getOrderDate() {
        return orderDate;
    }

    @Override
    public List<IShoppingItem> getItems() {
        return items;
    }

    @Override
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
}
