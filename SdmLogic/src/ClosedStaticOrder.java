import java.util.Date;

public class ClosedStaticOrder extends ClosedOrder {
     private Store usedStore;

    public ClosedStaticOrder(Date date, int totalAmountOfItemTypes, int totalAmountOfItemsByUnit, double deliveryPriceAfterOrder, double totalPriceOfOrder, double totalPriceOfItems, Store usedStore) {
        super(date, totalAmountOfItemTypes, deliveryPriceAfterOrder, totalAmountOfItemsByUnit, totalPriceOfOrder, totalPriceOfItems);
        this.usedStore = usedStore;
    }

    public Store getUsedStore() {
        return usedStore;
    }

    public void setUsedStore(Store usedStore) {
        this.usedStore = usedStore;
    }
}
