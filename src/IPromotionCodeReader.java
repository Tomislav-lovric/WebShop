import java.io.IOException;
import java.math.BigDecimal;

public interface IPromotionCodeReader {
    void loadPromotionCodes(String filePath) throws IOException;
    boolean isCodeValid(String code);
    BigDecimal getDiscount(String code);
}
