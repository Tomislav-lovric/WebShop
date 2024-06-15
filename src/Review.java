public class Review implements IReview{
    private String user;
    private String comment;
    private int rating;

    public Review(String user, String comment, int rating) {
        this.user = user;
        this.comment = comment;
        this.rating = rating;
    }

    @Override
    public String getUser() {
        return user;
    }

    @Override
    public String getComment() {
        return comment;
    }

    @Override
    public int getRating() {
        return rating;
    }
}
