import jaxb.schema.generated.IfYouBuy;
import jaxb.schema.generated.SDMDiscount;

public abstract class BasicPromotion {
      protected String promotionName;
      protected String operator;
      protected int requiredItemForPromotionId;
      protected double requiredQuantityForPromotion;

      public BasicPromotion(String promotionName, String operator, int requiredItemForPromotionId, double requiredQuantityForPromotion) {
            this.promotionName = promotionName;
            this.operator = operator;
            this.requiredItemForPromotionId = requiredItemForPromotionId;
            this.requiredQuantityForPromotion = requiredQuantityForPromotion;
      }

      public BasicPromotion(SDMDiscount JAXBDiscount) {
            IfYouBuy promotionCondition = JAXBDiscount.getIfYouBuy();
            this.promotionName = JAXBDiscount.getName();
            this.requiredItemForPromotionId = promotionCondition.getItemId();
            this.requiredQuantityForPromotion = promotionCondition.getQuantity();
            this.operator = JAXBDiscount.getThenYouGet().getOperator();
      }

      public String getPromotionName() {
            return promotionName;
      }

      public void setPromotionName(String promotionName) {
            this.promotionName = promotionName;
      }

      public String getOperator() {
            return operator;
      }

      public void setOperator(String operator) {
            this.operator = operator;
      }

      public int getRequiredItemForPromotionId() {
            return requiredItemForPromotionId;
      }

      public void setRequiredItemForPromotionId(int requiredItemForPromotionId) {
            this.requiredItemForPromotionId = requiredItemForPromotionId;
      }

      public double getRequiredQuantityForPromotion() {
            return requiredQuantityForPromotion;
      }

      public void setRequiredQuantityForPromotion(double requiredQuantityForPromotion) {
            this.requiredQuantityForPromotion = requiredQuantityForPromotion;
      }
}
