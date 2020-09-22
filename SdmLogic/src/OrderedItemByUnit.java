import SDMItemsInterface.ICalculable;
import SDMItemsInterface.IDiscrete;
import jaxb.schema.generated.SDMItem;
import jaxb.schema.generated.SDMSell;

public class OrderedItemByUnit extends OrderedItem implements IDiscrete, ICalculable {

    public OrderedItemByUnit(TypeOfMeasure typeToMeasureBy, String name, int serialNumber, int pricePerUnit, int amountOfOrderedItemByUnits) {
        super(typeToMeasureBy, name, serialNumber, pricePerUnit, amountOfOrderedItemByUnits);
    }

    public OrderedItemByUnit(SDMItem JAXBEItem, SDMSell JAXBEItemPricing, int amountOfOrderedItemByUnits) throws IllegalArgumentException, ArithmeticException {
        super(JAXBEItem, JAXBEItemPricing, amountOfOrderedItemByUnits);
    }

    public OrderedItemByUnit(AvailableItemInStore availableItemInStore, int amountOfOrderedItemByUnits) throws IllegalArgumentException, ArithmeticException {
        super(availableItemInStore, amountOfOrderedItemByUnits);
    }


    @Override
    public void increaseAmountOfItemOrderedByUnits(int amountToIncrease) {
        this.amountOfOrderedItemByUnits +=  amountToIncrease;
    }

    @Override
    public double calcTotalPriceOfItemOrdered() {
        return calcTotalAmountOfItemOrdered() * pricePerUnit;
    }

    @Override
    public double calcTotalAmountOfItemOrdered() {
        return (double)amountOfOrderedItemByUnits;
    }

    @Override
    public String toString() {
        return "OrderedItemByUnit{" +
                "pricePerUnit=" + pricePerUnit +
                ", typeToMeasureBy=" + typeToMeasureBy +
                ", name='" + name + '\'' +
                ", serialNumber=" + serialNumber +
                '}';
    }
}
