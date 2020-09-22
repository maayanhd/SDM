import jaxb.schema.generated.SDMDiscount;
import jaxb.schema.generated.SDMOffer;
import jaxb.schema.generated.ThenYouGet;

import java.util.HashMap;
import java.util.HashSet;

public class OrPromotion extends BasicPromotion {
    private final HashSet<Integer> idToProductToChoose = new HashSet<Integer>();
    private final HashMap<Integer, Double> productToChooseIdToQuantity = new HashMap<Integer, Double>();
    private final HashMap<Integer, Double> productIdToAdditionalPricePerUnit = new HashMap<Integer, Double>();

    public OrPromotion(String promotionName, String operator, int requiredItemForPromotionId, double requiredQuantityForPromotion) {
        super(promotionName, operator, requiredItemForPromotionId, requiredQuantityForPromotion);
        // TODO: 20/09/2020 missing manually filling up collections
    }

    public OrPromotion(SDMDiscount JAXBDiscount) {
        super(JAXBDiscount);
        ThenYouGet itemsToGet = JAXBDiscount.getThenYouGet();
        itemsToGet.getSDMOffer().stream()
                .forEach(sdmOffer -> assignPromotionFields(sdmOffer));
    }

    private void assignPromotionFields(SDMOffer sdmOffer) {
        int itemId = sdmOffer.getItemId();
        this.productToChooseIdToQuantity.put(itemId, sdmOffer.getQuantity());
        this.idToProductToChoose.add(itemId);
        this.productIdToAdditionalPricePerUnit.put(itemId, (double)(sdmOffer.getForAdditional()));
    }

    public double calcPromotionAdditionalPrice(int itemParticipatedSerialNum) {
        double additionalPrice= 0;

        if(productToChooseIdToQuantity.containsKey(itemParticipatedSerialNum) && productIdToAdditionalPricePerUnit.containsKey(itemParticipatedSerialNum)){
            additionalPrice = productToChooseIdToQuantity.get(itemParticipatedSerialNum) * productIdToAdditionalPricePerUnit.get(itemParticipatedSerialNum);
        }

        return additionalPrice;
    }
}