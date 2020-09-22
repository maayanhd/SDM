import jaxb.schema.generated.SDMDiscount;
import jaxb.schema.generated.SDMOffer;
import jaxb.schema.generated.ThenYouGet;

import java.util.HashMap;
import java.util.HashSet;

public class AndPromotion extends BasicPromotion {
    private final HashSet<Integer> idProductToGetSet = new HashSet<Integer>();
    private final HashMap<Integer, Double> productToGetIdToQuantity = new HashMap<Integer, Double>();
    private final HashMap<Integer, Double> productIdToAdditionalPricePerUnit = new HashMap<Integer, Double>();

    public AndPromotion(String promotionName, String operator, int requiredItemForPromotionId, double requiredQuantityForPromotion, ThenYouGet itemsToGet) {
        super(promotionName, operator, requiredItemForPromotionId, requiredQuantityForPromotion);
        // TODO: 20/09/2020  update manually fields if even needed
    }

    public AndPromotion(SDMDiscount JAXBDiscount) {
        super(JAXBDiscount);
        updateFieldsWrapperMethod(JAXBDiscount);
    }

    private void updateFieldsWrapperMethod(SDMDiscount JAXBDiscount){
    ThenYouGet itemsToGet = JAXBDiscount.getThenYouGet();
        itemsToGet.getSDMOffer().stream()
                .forEach(sdmOffer -> assignPromotionFields(sdmOffer));
    }

    private void assignPromotionFields(SDMOffer sdmOffer) {
        int itemId = sdmOffer.getItemId();
        this.productToGetIdToQuantity.put(itemId, sdmOffer.getQuantity());
        this.idProductToGetSet.add(itemId);
        this.productIdToAdditionalPricePerUnit.put(itemId, (double)(sdmOffer.getForAdditional()));
    }

        public double calcPromotionAdditionalPrice() {
            double additionalPrice= 0;

            if(!productToGetIdToQuantity.isEmpty() && !productIdToAdditionalPricePerUnit.isEmpty() &&
                    productToGetIdToQuantity.keySet().size() == productIdToAdditionalPricePerUnit.keySet().size()){
                additionalPrice =
                productToGetIdToQuantity.keySet().stream()
                        .mapToDouble(itemId -> productToGetIdToQuantity.get(itemId) * productIdToAdditionalPricePerUnit.get(itemId))
                        .sum();
            }

            return additionalPrice;
        }
}
