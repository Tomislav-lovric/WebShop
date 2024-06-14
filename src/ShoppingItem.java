import java.math.BigDecimal;
import java.util.Date;

public class ShoppingItem implements IShoppingItem{

    private Long id;
    private IProduct product;
    private BigDecimal quantity;
    private BigDecimal price;
    private Date lastModifyTime;

    public ShoppingItem(Long id, IProduct product, BigDecimal quantity, BigDecimal price) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.lastModifyTime = new Date();
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public IProduct getProduct() {
        return product;
    }

    @Override
    public BigDecimal getQuantity() {
        return quantity;
    }

    @Override
    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    @Override
    public BigDecimal getTotalAmount() {
        return quantity.multiply(price);
    }

    @Override
    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
        this.lastModifyTime = new Date();
    }

    @Override
    public void setPrice(BigDecimal price) {
        this.price = price;
        this.lastModifyTime = new Date();
    }

}
