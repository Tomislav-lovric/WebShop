import java.math.BigDecimal;

public class WebShop {
    public static void main(String[] args) {
        try {
            // Creating some products
            IProduct apple = new Product(
                    "FreshFruit",
                    "apple",
                    "A big green apple",
                    BigDecimal.valueOf(1),
                    BigDecimal.valueOf(10),
                    BigDecimal.valueOf(10));
            IProduct orange = new Product(
                    "FreshFruit",
                    "orange",
                    "Juicy orange",
                    BigDecimal.valueOf(2),
                    BigDecimal.valueOf(20));

            // Creating shopping cart
            IShoppingCart shoppingCart = new ShoppingCart(1L, "Tom");

            // Adding items into our shopping cart
            System.out.println("Adding of apple successful: " + shoppingCart.addItem(apple, BigDecimal.valueOf(1)));
            System.out.println("Adding of orange successful: " + shoppingCart.addItem(orange, BigDecimal.valueOf(10)));
            System.out.println("Adding of apple again successful: " + shoppingCart.addItem(apple, BigDecimal.valueOf(10)));

            // Displaying total price of our shopping cart
            System.out.println("Total price of shopping cart: " + shoppingCart.getTotalPrice());

            // Removing items from our shopping cart
            System.out.println("Removing of orange successful: " + shoppingCart.removeItem(orange, BigDecimal.valueOf(5)));

            // Displaying total price of our shopping cart after removing 5 oranges
            System.out.println("Total price of shopping cart: " + shoppingCart.getTotalPrice());

            // Adding 10% discount to oranges
            orange.setDiscount(BigDecimal.valueOf(10));

            // Refreshing our shopping cart
            shoppingCart.refresh();

            // Displaying total price of our shopping cart after refreshing it
            System.out.println("Total price of shopping cart: " + shoppingCart.getTotalPrice());

            // Printing all our items in our shopping cart
            for (IShoppingItem item : shoppingCart.getItems()) {
                System.out.println(
                        "Item: " + item.getProduct().getName() + ", Quantity: " + item.getQuantity() +
                                ", Price: " + item.getPrice() + ", Total Amount: " + item.getTotalAmount());
            }

            // Checking out
            System.out.println("Check out successful: " + shoppingCart.checkOut());

            // Displaying total price of our shopping cart after checkout
            System.out.println("Total price of shopping cart: " + shoppingCart.getTotalPrice());

            // Displaying order history
            for (IOrder order : shoppingCart.getOrderHistory()) {
                System.out.println("Order ID: " + order.getId() + ", User: " + order.getUser() +
                        ", Date: " + order.getOrderDate() + ", Total Price: " + order.getTotalPrice());
            }

            // Adding reviews to apple product
            apple.addReview(new Review("Tom", "Apple was fresh", 5));
            apple.addReview(new Review("John", "Apple was fresh", 5));

            // Displaying apple reviews
            for (IReview review : apple.getReviews()) {
                System.out.println("User: " + review.getUser() + ", Comment: " + review.getComment() +
                        ", Rating: " + review.getRating());
            }

        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }
}