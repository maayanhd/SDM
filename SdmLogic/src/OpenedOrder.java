import SDMItemsInterface.ICalculable;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

public abstract class OpenedOrder extends Order{
    public OpenedOrder(Date date) {
        super(date);
    }

    public abstract double calcTotalDeliveryPrice(Location deliveryDestination) throws DuplicateLocationException, InvalidCoordinateException;

    public abstract double calcTotalPriceOfOrder(Location deliveryDestination) throws DuplicateLocationException, InvalidCoordinateException;

    public double calcDeliveryPriceFromStore(Location deliveryDestination,  Store givenStore) {
        double deliveryPrice = 0;
        Location currShopLocation = givenStore.getLocationOfShop();

        if(!deliveryDestination.equals(currShopLocation)){
            double deliveryDistance= Point2D.distance(currShopLocation.getCoordinateX(), currShopLocation.getCoordinateY(), deliveryDestination.getCoordinateX(), deliveryDestination.getCoordinateY());
            deliveryPrice = deliveryDistance * givenStore.getDeliveryPpk();
        }

        // design by contract- at this point already checked delivery location is valid

        return deliveryPrice;
    }

    public double calcTotalPriceOfItems(){
        double totalPriceOfItems = 0;

        for (Map.Entry<Integer, OrderedItem> integerOrderedItemEntry : itemOrderedSerialNumToOrderedItemMap.entrySet()) {
            OrderedItem orderedItem = integerOrderedItemEntry.getValue();
            ICalculable calculableOrderedItem= orderedItem instanceof OrderedItemByWeight ? (ICalculable)(OrderedItemByWeight)orderedItem : (ICalculable) orderedItem;
            totalPriceOfItems+=calculableOrderedItem.calcTotalPriceOfItemOrdered();
        }

        return totalPriceOfItems;
    }

    public int calcTotalAmountOfItemsByUnit(){
        long  totalAmountOfItemsByUnit = itemOrderedSerialNumToOrderedItemMap.values().stream()
                .filter(orderedItem -> orderedItem.typeToMeasureBy == TypeOfMeasure.QUANTITY)
                .count();

        return  (int) totalAmountOfItemsByUnit;
    }

    @Override
    public String toString() {
        return "Order: status: open" + Arrays.toString(itemOrderedSerialNumToOrderedItemMap.values().toArray()) +
                " date:" + date.toString();
    }

    public int calcTotalAmountOfItemTypes(){
        return itemOrderedSerialNumToOrderedItemMap.keySet().size();
    }





}
