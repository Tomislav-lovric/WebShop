import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PromotionCodeReader implements IPromotionCodeReader{
    private Map<String, BigDecimal> promotionCodes;

    public PromotionCodeReader(String filePath) throws IOException {
        this.promotionCodes = new HashMap<>();
        loadPromotionCodes(filePath);
    }

    @Override
    public void loadPromotionCodes(String filePath) throws IOException {
        // Here we simply load our csv file
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Go through it line by line, splitting it with ";" delimiter and putting codes and discounts
            // into our map
            while ((line = br.readLine()) != null) {
                String[] values = line.trim().split(";");
                String code = values[0];
                BigDecimal discount = new BigDecimal(values[1].trim());
                promotionCodes.put(code, discount);
            }
        }
    }

    @Override
    public boolean isCodeValid(String code) {
        return promotionCodes.containsKey(code);
    }

    @Override
    public BigDecimal getDiscount(String code) {
        return promotionCodes.get(code);
    }
}
