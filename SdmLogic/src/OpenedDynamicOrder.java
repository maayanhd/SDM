import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class OpenedDynamicOrder extends OpenedOrder {

    private final HashMap<Store, LinkedList<OrderedItem>> storeToSelectedItemsListMap = new HashMap<Store, LinkedList<OrderedItem>>();
    private final MapperAndStorage storageAndMapper;

    public OpenedDynamicOrder(Date date, MapperAndStorage storageAndMapper) {
        super(date);
        this.storageAndMapper = storageAndMapper;
    }

    @Override
    public double calcTotalDeliveryPrice(Location deliveryDestination) throws DuplicateLocationException, InvalidCoordinateException {
        double totalDeliveryPrice =0;
        for (Store visitedStore: storeToSelectedItemsListMap.keySet()) {
            totalDeliveryPrice += calcDeliveryPriceFromStore(deliveryDestination, visitedStore);
        }

        return totalDeliveryPrice;
    }

    @Override
    public double calcTotalPriceOfOrder(Location deliveryDestination) throws DuplicateLocationException, InvalidCoordinateException {
         double totalPriceOfOrder = calcTotalPriceOfItems();

         for (Store visitedStore: storeToSelectedItemsListMap.keySet()){
                totalPriceOfOrder +=  calcTotalDeliveryPrice(deliveryDestination);
          }

         return totalPriceOfOrder;
    }

    private int calcTotalAmountOfStores(){
        return storeToSelectedItemsListMap.keySet().size();
    }

    public ClosedDynamicOrder closeOrder(Location deliveryLocation) throws DuplicateLocationException, InvalidCoordinateException {
        int totalAmountOfItemTypes = calcTotalAmountOfItemTypes(),
            totalAmountOfItemsByUnit = calcTotalAmountOfItemsByUnit();
        double totalDeliveryPrice = calcTotalDeliveryPrice(deliveryLocation),
                totalPriceOfOrder = calcTotalPriceOfOrder(deliveryLocation);

        ClosedDynamicOrder closedOrder = new ClosedDynamicOrder(date, totalAmountOfItemTypes, totalAmountOfItemsByUnit, 0,
                totalPriceOfOrder,calcTotalPriceOfItems(),calcTotalAmountOfStores());

        return closedOrder;
    }

    private void updateItemsByCheapest(){
        HashSet<OrderedItem> orderedItemHashSet = new HashSet<OrderedItem>(itemOrderedSerialNumToOrderedItemMap.values());
        HashMap<Store, LinkedList<OrderedItem>> shopToCheapestItems = storageAndMapper.getMapOfShopWithCheapestItemsFromSet(orderedItemHashSet);

        storeToSelectedItemsListMap.clear();
        storeToSelectedItemsListMap.putAll(shopToCheapestItems);
    }
}
