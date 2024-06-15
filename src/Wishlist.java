import java.util.ArrayList;
import java.util.List;

public class Wishlist implements IWishlist{
    private Long id;
    private String user;
    private List<IProduct> items;

    public Wishlist(Long id, String user) {
        this.id = id;
        this.user = user;
        this.items = new ArrayList<>();
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
    public List<IProduct> getItems() {
        return items;
    }

    @Override
    public boolean addItem(IProduct product) {
        if (!items.contains(product)) {
            items.add(product);
        }
        return false;
    }

    @Override
    public boolean removeItem(IProduct product) {
        return items.remove(product);
    }
}
