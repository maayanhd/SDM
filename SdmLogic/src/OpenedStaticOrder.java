import SDMItemsInterface.ICalculable;

import java.util.Date;

public class OpenedStaticOrder extends OpenedOrder {
    public Store getVisitedStore() {
        return visitedStore;
    }

    private Store visitedStore;

    public OpenedStaticOrder(Date date, Store currentStore) {
        super(date);
        this.visitedStore = currentStore;
    }

    public ClosedStaticOrder closeOrder(Location deliveryLocation) {
        int totalAmountOfItemTypes = calcTotalAmountOfItemTypes(),
            totalAmountOfItemsByUnit = calcTotalAmountOfItemsByUnit();
        double totalDeliveryPrice = calcTotalDeliveryPrice(deliveryLocation),
                totalPriceOfOrder = calcTotalPriceOfOrder(deliveryLocation);

        ClosedStaticOrder closedOrder = new ClosedStaticOrder(this.date, totalAmountOfItemTypes, totalAmountOfItemsByUnit
            ,totalDeliveryPrice,totalPriceOfOrder,calcTotalPriceOfItems(), visitedStore);
        // Update total amount sold
        for (OrderedItem item: getItemOrderedSerialNumToOrderedItemMap().values()) {
            double amountToAdd = item.getTotalAmountSold() + (item instanceof OrderedItemByUnit ?
                    ((ICalculable)(OrderedItemByUnit)item).calcTotalAmountOfItemOrdered() :
                    ((ICalculable)(OrderedItemByWeight)item).calcTotalAmountOfItemOrdered());
            item.setTotalAmountSold(amountToAdd);
            if(visitedStore != null)
                visitedStore.getItemsSerialIdToAvailableItemInStore().get(item.getSerialNumber()).setTotalAmountSold(amountToAdd);
            else
                System.out.println("null???");
        }
        return closedOrder;
    }

    @Override
    public String toString() {
        return "OpenedStaticOrder{" +
                "Visited Store:" + visitedStore +
                ", itemOrderedSerialNumToOrderedItemMap=" + itemOrderedSerialNumToOrderedItemMap +
                ", date=" + date +
                '}';
    }

    @Override
    public double calcTotalDeliveryPrice(Location deliveryDestination) {
        return calcDeliveryPriceFromStore(deliveryDestination, visitedStore);
    }

    @Override
    public double calcTotalPriceOfOrder(Location deliveryDestination)  {
        double totalPriceOfOrder = calcTotalDeliveryPrice(deliveryDestination) + calcTotalPriceOfItems();

        return totalPriceOfOrder;
    }
}
