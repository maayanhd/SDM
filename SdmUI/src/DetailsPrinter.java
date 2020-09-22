import SDMItemsInterface.ICalculable;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

public class DetailsPrinter {

    private MapperAndStorage mapper;

    public DetailsPrinter(MapperAndStorage mapper) {
        this.mapper = mapper;
    }

    public static DecimalFormat getDf2Digit() {
        return df2Digit;
    }

    private static DecimalFormat df2Digit = new DecimalFormat("0.00");

    public void showStoresDetailsFilteredByParameters(boolean isShowingItemsDetailsOfStore, boolean isShowingOrderDetails){
        mapper.getStoreSerialIdToShopMap().values().stream()
                .forEach(store ->{
                        System.out.println("Store Serial number: " + store.getSerialNumber() + "\n" +
                                            "Name of store: " + store.getName());
                if(isShowingItemsDetailsOfStore) {
                    System.out.println("Available items in stock: ");
                    showAvailableItemsInStoreDetails(store.getSerialNumber(), true);
                }
                if(isShowingOrderDetails) {
                    System.out.println("past orders and purchases in current store:");
                    if(!store.getOrdersSerialId().isEmpty()){
                        showOrderDetailsBySerialIdSet(store.getOrdersSerialId());
                    }
                    else{
                        System.out.println("sorry, no past orders yet");
                    }
                }

                System.out.println("PPK: " + store.getDeliveryPpk());
                    mapper.calcDeliveriesProfit(store);
                    System.out.println("Total deliveries profit: " + DetailsPrinter.getDf2Digit().format(mapper.calcDeliveriesProfit(store))  + "\n");
                });
        }

    private void showOrderDetailsBySerialIdSet(HashSet<Integer> ordersSerialId){
        ordersSerialId.stream()
                .map(orderSerialId -> mapper.getOrderBySerialId(orderSerialId))
                .forEach(closedOrder -> {
                    if (closedOrder instanceof ClosedStaticOrder) {
                        showOrderHistoryByPolymorphicType((ClosedStaticOrder) closedOrder);
                    } else {
                        showOrderHistoryByPolymorphicType((ClosedDynamicOrder) closedOrder);
                    }
                        });
    }

    public void showAvailableItemsInStoreDetails(int storeSerialId, boolean isShowingItemsDetailsOfStore) {
        Store currentStore = mapper.getStoreBySerialId(storeSerialId);

        if(isShowingItemsDetailsOfStore){
            mapper.getStoreBySerialId(storeSerialId).getItemsSerialIdToAvailableItemInStore().values().stream()
                    .forEach(availableItem->showAvailableItemDetails(availableItem, true, currentStore));
                }
    }

    public void showItemsInSystemAndPricesOfStore(int storeSerialId) throws ItemNotFoundException{
        // Assuming at this point the serial number exists - design by contract
        Store currentShop = mapper.getStoreBySerialId(storeSerialId);
        if(currentShop != null){
            showAvailableItemsInStoreDetails(currentShop.getSerialNumber(), true);
        }
        else {
            throw new ItemNotFoundException("no store matching the given id in the system", storeSerialId, mapper.getStoreBySerialId(storeSerialId).getName());
        }
    }

    public void showItemsInSystem(){
        mapper.getItemSerialIdToItem().values().stream()
                .forEach(itemInSystem -> {
                    System.out.println("Serial number: " + itemInSystem.getSerialNumber());
                    System.out.println("Name of product: " + itemInSystem.getName());
                    System.out.println("Purchase category: " + itemInSystem.getTypeToMeasureBy().getMeaning());
                    System.out.println("Number of shops selling the product: " + mapper.getTotalAmountOfShopsSellingACertainItem(itemInSystem.serialNumber));
                    System.out.println("Average price of product: " + df2Digit.format(mapper.calcAvgPriceOfAnItemInSDM(itemInSystem.serialNumber)) );
                    System.out.println("amount of pieces sold of this product : " + itemInSystem.getTotalAmountSold() + "\n\n");
                });
    }

    public String dateToStrOfCertainFormat(Date givenDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM-hh:mm");
        dateFormat.setLenient(false);

        return dateFormat.format(givenDate);
    }

    public void showOrderHistoryByPolymorphicType(ClosedOrder closedOrder){
        System.out.println("Date: " + dateToStrOfCertainFormat(closedOrder.getDate()));
        System.out.println("Amount of products: " + closedOrder.getTotalAmountOfItemsByUnit());
        System.out.println("Total items cost: " +   df2Digit.format(closedOrder.getTotalPriceOfItems()));
        System.out.println("Delivery price: " + df2Digit.format(closedOrder.getDeliveryPriceAfterOrder()));
        System.out.println("Total cost (including delivery price) : " + df2Digit.format(closedOrder.getTotalPriceOfOrder()));
//        System.out.println("Shopping cart (): ");
//        closedOrder.getItemOrderedSerialNumToOrderedItemMap().values().stream()
//                .forEach(orderedItem -> showOrderedItemDetails(orderedItem, true));
//        System.out.println(closedOrder instanceof ClosedStaticOrder ? "shop visited: " : "shops visited");
//        LinkedList<Store> shopsVisited = new LinkedList<Store>();
//        if(closedOrder instanceof  ClosedStaticOrder){
//            shopsVisited.add((((ClosedStaticOrder) closedOrder).getUsedStore()));
//        }
//        else{
//            shopsVisited = ((ClosedDynamicOrder) closedOrder).getVisitedShopsList();
//            shopsVisited.stream()
//                    .forEach(store-> System.out.println(store.toString()));
//        }
    }

    public void showItemsDetailsOfOpenedOrder(OpenedStaticOrder openedOrder){
        for (OrderedItem orderedItem : openedOrder.itemOrderedSerialNumToOrderedItemMap.values()) {
            showOrderedItemDetails(orderedItem, true, openedOrder.getVisitedStore());
        }
    }

    public void showAvailableItemsInSystem(){
        mapper.getItemSerialIdToItem().values().stream()
                .forEach(availableItemInStore -> showAvailableItemDetails(availableItemInStore, true, null));
    }

    public void showAvailableItemDetails(AvailableItemInStore availableItem, boolean isStatic, Store visitedStoreOrNull){
        System.out.println("Serial number: " + availableItem.serialNumber);
        System.out.println("name of product: " + availableItem.getName());
        System.out.println("Purchase category: " + availableItem.typeToMeasureBy.getMeaning());
        System.out.println(isStatic && visitedStoreOrNull!= null ?
                "Price per unit: " + availableItem.getPricePerUnit() + "\n":
                "No such item in store" + "\n");
    }

    public void showOrderHistoryOfAllStoresInSystem(){
        System.out.println("Order History of all of the shops in system: " + "\n");
        mapper.getOrderSerialIdToClosedOrder().values().stream()
                .forEach(closedOrderInSystem -> {
                    System.out.println("Order serial number: " + closedOrderInSystem.getSerialNumber());
                    System.out.println("Date: " + closedOrderInSystem.getDate().toString());
                    System.out.println("bought in: ");
                    System.out.println("Store name: " + ((ClosedStaticOrder)closedOrderInSystem).getUsedStore().getName());
                    System.out.println("Store serial id: " + ((ClosedStaticOrder)closedOrderInSystem).getUsedStore().getSerialNumber());
                    System.out.println("Total quantity of purchased items: " + closedOrderInSystem.getTotalAmountOfItemsByUnit());
                    System.out.println("Total amount of purchased items types: " + closedOrderInSystem.getTotalAmountOfItemTypes());
                    System.out.println("Purchased items total cost: " + closedOrderInSystem.getTotalPriceOfItems());
                    System.out.println("Total delivery cost: " + closedOrderInSystem.getDeliveryPriceAfterOrder() + "\n");
                    System.out.println("Order total cost:  " + closedOrderInSystem.getTotalPriceOfOrder() + "\n");
                });
    }

    public void showOrderedItemDetails(OrderedItem orderedItem, boolean isStatic, Store visitedShopOrNull){

        showAvailableItemDetails((AvailableItemInStore) orderedItem, true, visitedShopOrNull);
        System.out.println("Amount by unit: " + orderedItem.amountOfOrderedItemByUnits);
        ICalculable calculableItem = orderedItem instanceof OrderedItemByWeight ? ((ICalculable)(OrderedItemByWeight)orderedItem) :
                ((ICalculable)(OrderedItemByUnit)orderedItem);
        System.out.println(String.format("Amount by %s: ", orderedItem.typeToMeasureBy.getMeaning()) + df2Digit.format(calculableItem.calcTotalAmountOfItemOrdered()));
        System.out.print("Price: " + df2Digit.format(calculableItem.calcTotalPriceOfItemOrdered()) + "\n\n");
    }
}
