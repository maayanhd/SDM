import jaxb.schema.generated.SDMItem;
import jaxb.schema.generated.SDMSell;

import java.text.DecimalFormat;
import java.util.Objects;

public class OrderedItem extends AvailableItemInStore {

    protected int amountOfOrderedItemByUnits = 0;

    public OrderedItem(TypeOfMeasure typeToMeasureBy, String name, Integer serialNumber, int pricePerUnit, int amountOfOrderedItemByUnits) {
        super(typeToMeasureBy, name, pricePerUnit);
        this.amountOfOrderedItemByUnits = amountOfOrderedItemByUnits;
        this.totalAmountSold += amountOfOrderedItemByUnits;
    }

    public OrderedItem(SDMItem JAXBEItem, SDMSell JAXBEItemPricing, int amountOfOrderedItemByUnits) throws IllegalArgumentException, ArithmeticException {
        super(JAXBEItem, JAXBEItemPricing);
        this.amountOfOrderedItemByUnits = amountOfOrderedItemByUnits;
    }

    public OrderedItem(AvailableItemInStore availableItemInStore, int amountOfOrderedItemByUnits) throws IllegalArgumentException, ArithmeticException {
        super(availableItemInStore.typeToMeasureBy, availableItemInStore.name, availableItemInStore.pricePerUnit);
        this.amountOfOrderedItemByUnits = amountOfOrderedItemByUnits;
    }

    public double getAmountOfOrderedItemByUnits() {
        return amountOfOrderedItemByUnits;
    }

    public void setAmountOfOrderedItemByUnits(int amountOfOrderedItemByUnits) {
        this.amountOfOrderedItemByUnits = amountOfOrderedItemByUnits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderedItem)) return false;
        if (!super.equals(o)) return false;
        OrderedItem that = (OrderedItem) o;
        return amountOfOrderedItemByUnits == that.amountOfOrderedItemByUnits;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
