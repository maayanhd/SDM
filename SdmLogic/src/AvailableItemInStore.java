import jaxb.schema.generated.SDMItem;
import jaxb.schema.generated.SDMSell;

import java.util.Objects;

public class AvailableItemInStore extends BasicItem {

    protected final int pricePerUnit;
    protected double totalAmountSold = 0;


    public double getTotalAmountSold() {
        return totalAmountSold;
    }

    public void setTotalAmountSold(double totalAmountSold) {
        this.totalAmountSold = totalAmountSold;
    }

    public AvailableItemInStore(TypeOfMeasure typeToMeasureBy, String name, int pricePerUnit) {
        super(typeToMeasureBy, name);
        this.pricePerUnit = pricePerUnit;
    }

    public AvailableItemInStore(SDMItem JAXBEItem, SDMSell JAXBEItemPricing) throws IllegalArgumentException, ArithmeticException {
        super(JAXBEItem);
        int JAXBEItemPrice = JAXBEItemPricing.getPrice();
        this.pricePerUnit = JAXBEItemPrice;
        if(pricePerUnit <= 0){
            throw new ArithmeticException("Invalid price item value, must be a positive number!");
        }
    }

    @Override
    public String toString() {
        return "AvailableItemInStore{" +
                "pricePerUnit=" + pricePerUnit +
                ", typeToMeasureBy=" + typeToMeasureBy +
                ", name='" + name + '\'' +
                ", serialNumber=" + serialNumber +
                '}';
    }

    public int getPricePerUnit() {
        return pricePerUnit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AvailableItemInStore)) return false;
        if (!super.equals(o)) return false;
        AvailableItemInStore that = (AvailableItemInStore) o;
        return pricePerUnit == that.pricePerUnit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), pricePerUnit);
    }
}
