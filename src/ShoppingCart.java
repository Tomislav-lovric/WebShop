import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ShoppingCart implements IShoppingCart{

    private Long id;
    private String user;
    private Date createdTime;
    private List<IShoppingItem> items;
    private List<IOrder> orderHistory;
    private PromotionCodeReader promotionCodeReader;

    public ShoppingCart(Long id, String user, PromotionCodeReader promotionCodeReader) {
        this.id = id;
        this.user = user;
        this.createdTime = new Date();
        this.items = new ArrayList<>();
        this.orderHistory = new ArrayList<>();
        this.promotionCodeReader = promotionCodeReader;
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
    public Date getCreatedTime() {
        return createdTime;
    }

    @Override
    public List<IShoppingItem> getItems() {
        // Here we first stream the list
        return items.stream()
                // Then we sort it
                .sorted((item1, item2) -> {
                    // But first we try sorting it by comparing producers
                    int sortedByProducer = item1.getProduct().getProducer().compareTo(item2.getProduct().getProducer());
                    if (sortedByProducer != 0) {
                        // If producers are different we return this comparison
                        return sortedByProducer;
                    } else {
                        // Otherwise we compare items by name and return that comparison
                        return item1.getProduct().getName().compareTo(item2.getProduct().getName());
                    }
                }).collect(Collectors.toList());
    }

    @Override
    public BigDecimal getTotalPrice() {
        // Here we simply stream through the items, get their total amounts, and sum them
        return items.stream()
                .map(item -> item.getTotalAmount())
                .reduce(BigDecimal.ZERO, (total, amount) -> total.add(amount));
    }

    @Override
    public boolean addItem(IProduct product, BigDecimal quantity) {
        // First we go through our items and check if item already exists in shopping cart
        for (IShoppingItem item : items) {
            if (item.getProduct().equals(product)) {
                // If it does we then create new quantity
                BigDecimal newQuantity = item.getQuantity().add(quantity);
                // Then we check if our new quantity doesn't exceed the stock amount of the product we are trying to add
                if (newQuantity.compareTo(product.getStockAmount()) <= 0) {
                    // If it doesn't we update our shopping item with new quantity
                    item.setQuantity(newQuantity);

                    // This is the way of doing it without adding setQuantity() method
                    /*
                    items.remove(item);
                    items.add(new ShoppingItem(item.getId(), item.getProduct(), newQuantity, item.getPrice()));
                    */
                    return true;
                } else {
                    // If our new quantity exceeds stock amount we return false
                    return false;
                }
            }
        }

        // If item doesn't exist in our shopping cart, we check if quantity of items we are trying to add doesn't
        // exceed stock amount, if it doesn't we return true
        if (product.getStockAmount().compareTo(quantity) >= 0) {
            items.add(new ShoppingItem((long) items.size() + 1, product, quantity, product.getDiscountedPrice()));
            return true;
        }

        // If everything before fails we return false
        return false;
    }

    @Override
    public boolean removeItem(IProduct product, BigDecimal quantity) {
        // First we go through our items and check if item already exists in shopping cart
        for (IShoppingItem item : items) {
            if (item.getProduct().equals(product)) {
                // If item exists we create new quantity
                BigDecimal newQuantity = item.getQuantity().subtract(quantity);
                // Then we check if new quantity is 0 or less
                if (newQuantity.compareTo(BigDecimal.ZERO) <= 0) {
                    // If it is we completely remove that item from our shopping cart
                    items.remove(item);
                } else {
                    // If it isn't we update our shopping item with new quantity
                    item.setQuantity(newQuantity);

                    // This is the way of doing it without adding setQuantity() method
                    /*
                    items.remove(item);
                    items.add(new ShoppingItem(item.getId(), item.getProduct(), newQuantity, item.getPrice()));
                    */
                }
                return true;
            }
        }

        // If item doesn't exist in our shopping cart we just return false
        return false;
    }

    @Override
    public void refresh() {
        for (IShoppingItem item : items) {
            // Here we simply get stock amount and actual price of product
            BigDecimal stockAmount = item.getProduct().getStockAmount();
            BigDecimal productPrice = item.getProduct().getDiscountedPrice();
            System.out.println(productPrice);
            // Then we check if quantity exceeds stock amount, and if it does we set it to stock amount
            if (item.getQuantity().compareTo(stockAmount) > 0) {
                item.setQuantity(stockAmount);
            }
            // Here we check if prices match, if they don't we update our item with products price
            if (item.getPrice().compareTo(productPrice) != 0) {
                item.setPrice(productPrice);
            }
        }
    }

    @Override
    public boolean checkOut() {
        // Here we first go through all items in our list
        for (IShoppingItem item : items) {
            // Then we check if any of theirs quantities exceeds stock amount of the product itself, and if it does
            // we return false
            if (item.getQuantity().compareTo(item.getProduct().getStockAmount()) > 0) {
                return false;
            }
            // Otherwise we update our actual product with its new stock amount after we "buy" our items
            BigDecimal newStockAmount = item.getProduct().getStockAmount().subtract(item.getQuantity());
            item.getProduct().setStockAmount(newStockAmount);
        }
        // Then we create our order and add it to the order history. We also have to use new ArrayList<>(items)
        // because we are clearing the list later, and we have to create copy of the list
        IOrder order = new Order(
                (long) (orderHistory.size() + 1),
                user,
                new Date(),
                new ArrayList<>(items),
                getTotalPrice());

        orderHistory.add(order);
        // And finally if it's all good we clear our shopping cart and return true
        items.clear();
        return true;
    }

    @Override
    public List<IOrder> getOrderHistory() {
        return orderHistory;
    }

    @Override
    public BigDecimal getTotalPriceWithPromotionCode(String code) {
        // Here we simply get total price
        BigDecimal totalPrice = getTotalPrice();
        if (promotionCodeReader.isCodeValid(code)) {
            // Then after checking that code is valid we get our discount of that code
            BigDecimal discount = promotionCodeReader.getDiscount(code);
            // Then we calculate our discount factor and apply it to total price, which we then return
            BigDecimal discountFactor = BigDecimal.ONE.subtract(discount.divide(BigDecimal.valueOf(100)));
            return totalPrice.multiply(discountFactor);
        }
        // If code fails we just return regular total price without discount applied
        return totalPrice;
    }
}
