import java.util.Date;
import java.util.LinkedList;

//TODO finish it
public class ClosedDynamicOrder extends ClosedOrder {
    private int totalAmountOfStores;
    private final LinkedList<Store> visitedShopsList = new LinkedList<Store>();

    public ClosedDynamicOrder(Date date, int totalAmountOfItemTypes, int totalAmountOfItemsByUnit, double deliveryPriceAfterOrder, double totalPriceOfOrder, double totalPriceOfItems, int totalAmountOfStores) {
        super(date, totalAmountOfItemTypes, deliveryPriceAfterOrder, totalAmountOfItemsByUnit, totalPriceOfOrder, totalPriceOfItems);
        this.totalAmountOfStores = totalAmountOfStores;
    }

    public int getTotalAmountOfStores() {
        return totalAmountOfStores;
    }

    public void setTotalAmountOfStores(int totalAmountOfStores) {
        this.totalAmountOfStores = totalAmountOfStores;
    }

    public LinkedList<Store> getVisitedShopsList() {
        return visitedShopsList;
    }
}
