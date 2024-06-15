import java.util.List;

public interface IWishlist {
    Long getId();
    String getUser();
    List<IProduct> getItems();
    boolean addItem(IProduct product);
    boolean removeItem(IProduct product);
}
