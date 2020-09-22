import SDMItemsInterface.ICalculable;
import SDMItemsInterface.IWeightable;
import jaxb.schema.generated.SDMItem;
import jaxb.schema.generated.SDMSell;

public class OrderedItemByWeight extends OrderedItem implements IWeightable, ICalculable {
    private double amountOfOrderedItemInWeight;
    private static  final int quantityAmountByUnitPerOrder = 1;

    public OrderedItemByWeight(TypeOfMeasure typeToMeasureBy, String name, int serialNumber, int pricePerUnit, double amountOfOrderedItemInWeight) {
        super(typeToMeasureBy, name, serialNumber, pricePerUnit, quantityAmountByUnitPerOrder);
        this.amountOfOrderedItemInWeight = amountOfOrderedItemInWeight;
    }

    public OrderedItemByWeight(SDMItem JAXBEItem, SDMSell JAXBEItemPricing, double amountOfOrderedItemInWeight) throws IllegalArgumentException, ArithmeticException {
        super(JAXBEItem, JAXBEItemPricing, quantityAmountByUnitPerOrder);
        this.amountOfOrderedItemInWeight = amountOfOrderedItemInWeight;
    }

    public OrderedItemByWeight(AvailableItemInStore availableItemInStore, int amountOfOrderedItemByUnits, double amountOfOrderedItemInWeight) throws IllegalArgumentException, ArithmeticException {
        super(availableItemInStore, amountOfOrderedItemByUnits);
        this.amountOfOrderedItemInWeight = amountOfOrderedItemInWeight;
    }

    public double getAmountOfOrderedItemInWeight() {
        return amountOfOrderedItemInWeight;
    }

    @Override
    public double calcTotalPriceOfItemOrdered() {
        return amountOfOrderedItemInWeight * pricePerUnit;
    }

    @Override
    public double calcTotalAmountOfItemOrdered() {
        return amountOfOrderedItemInWeight;
    }

    @Override
    public void increaseAmountOfItemOrderedByWeight(double amountToIncrease) {
        amountOfOrderedItemInWeight +=amountToIncrease;
    }
}
