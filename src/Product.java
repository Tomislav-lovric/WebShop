import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Product implements IProduct{

    private String producer;
    private String name;
    private String description;
    private BigDecimal price;
    private BigDecimal stockAmount;
    private BigDecimal discount;
    private List<IReview> reviews;

    public Product(String producer, String name, String description, BigDecimal price, BigDecimal stockAmount, BigDecimal discount) {
        this.producer = producer;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockAmount = stockAmount;
        setDiscount(discount);
        this.reviews = new ArrayList<>();
    }
    public Product(String producer, String name, String description, BigDecimal price, BigDecimal stockAmount) {
        this.producer = producer;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockAmount = stockAmount;
        this.discount = BigDecimal.ZERO;
    }


    @Override
    public String getProducer() {
        return producer;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public BigDecimal getStockAmount() {
        return stockAmount;
    }

    @Override
    public void setStockAmount(BigDecimal stockAmount) {
        this.stockAmount = stockAmount;
    }

    @Override
    public BigDecimal getDiscount() {
        return discount;
    }

    @Override
    public void setDiscount(BigDecimal discount) {
        // Before setting discount we have to check if it is between 0-99
        if (discount.compareTo(BigDecimal.ZERO) < 0 || discount.compareTo(BigDecimal.valueOf(99)) > 0) {
            throw new IllegalArgumentException("Discount has to be between 0 and 99");
        }
        // Then we check if it is a whole number by dividing discount with 1 and checking if it's reminder
        // equals 0
        if (discount.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) != 0) {
            throw  new IllegalArgumentException("Discount has to be a whole number");
        }
        this.discount = discount;
    }

    @Override
    public BigDecimal getDiscountedPrice() {
        // We get discounted price by first converting the whole number to a fraction (e.g. 10 becomes 0.1) then
        // we calculate discount factor (e.g. 1 - 0.1 = 0.9) and finally we apply it to discount price
        BigDecimal discountFactor = BigDecimal.ONE.subtract(discount.divide(BigDecimal.valueOf(100)));
        return price.multiply(discountFactor);
    }

    @Override
    public void addReview(IReview review) {
        if (review.getRating() < 0 || review.getRating() > 5) {
            throw new IllegalArgumentException("Rating has to be between 0 and 5");
        }
        reviews.add(review);
    }

    @Override
    public List<IReview> getReviews() {
        return reviews;
    }

    @Override
    public double getAverageRating() {
        // Here we just stream through our reviews to get sum of review ratings
        double totalRatings = reviews.stream()
                .map(review -> review.getRating())
                .reduce(0, (total, amount) -> total + amount);
        return totalRatings/reviews.size();
    }
}
