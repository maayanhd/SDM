import SDMItemsInterface.ICalculable;

import java.util.*;
import java.util.stream.Collectors;

public class MapperAndStorage {
    private final HashMap<Integer, Location> shopSerialIdToLocation = new HashMap<>();
    private final HashMap<Location, Store> locationToStore = new HashMap<>();
    private final HashMap<Integer, Store> storeSerialIdToStore = new HashMap<>();
    private final HashMap<Integer, AvailableItemInStore> itemSerialIdToItem = new HashMap<>();
    private final HashSet<Integer> storesSerialId = new HashSet<>();
    private final HashSet<Integer> itemsSerialIdSet = new HashSet<>();
    private final HashSet<Integer> ordersSerialIdSet = new HashSet<>();
    private final HashMap<Integer, ClosedOrder>  orderSerialIdToClosedOrder = new HashMap<>();
    private final HashMap<Integer, Customer>  idToCustomer = new HashMap<>();
    private final HashMap<String, BasicPromotion> nameToPromotion = new HashMap<>();
    private int currentOrderSerialIdInStorage;
    private static int counter =0;

    public HashMap<Location, Store> getLocationToStore() {
        return locationToStore;
    }

    public HashMap<Integer, Location> getShopSerialIdToLocation() {
        return shopSerialIdToLocation;
    }

    public HashMap<Integer, Store> getStoreSerialIdToStore() {
        return storeSerialIdToStore;
    }

    public HashMap<Integer, Customer> getIdToCustomer() {
        return idToCustomer;
    }

    public HashMap<String, BasicPromotion> getNameToPromotion() {
        return nameToPromotion;
    }

    public MapperAndStorage() {
        currentOrderSerialIdInStorage= ++counter;
    }

    public static int getCounter() {
        return counter;
    }

    public int getNumberOfShopsSellsItem(int itemSerialId){

        int numOfShopsApply = storeSerialIdToStore.values().stream()
                // Filtering the shops that sells the item with the given serial id
                .filter(store -> store.getItemsSerialIdToAvailableItemInStore().containsKey(itemSerialId))
                // Mapping every store apply to whole numbers and summing
                .collect(Collectors.reducing(0 ,e -> 1, Integer::sum));
        return numOfShopsApply;

    }

    public int sumAllPricesOfAnItemInShops(int itemSerialId){
        int sumOfPricesOfGivenItem =0;

        HashSet<AvailableItemInStore> filteredItems =
                (HashSet<AvailableItemInStore>)  storeSerialIdToStore.values().stream()
                .filter(store -> store.getItemsSerialIdToAvailableItemInStore().containsKey(itemSerialId))
                .map(store -> store.getItemsSerialIdToAvailableItemInStore().get(itemSerialId))
                .collect(Collectors.toSet());

        for (AvailableItemInStore filteredItem:filteredItems) {
            sumOfPricesOfGivenItem += filteredItem.getPricePerUnit();
        }

        return sumOfPricesOfGivenItem;
    }

    public double calcAvgPriceOfAnItemInSDM(int itemSerialId){
        return sumAllPricesOfAnItemInShops(itemSerialId) / getTotalAmountOfShopsSellingACertainItem(itemSerialId);
    }

    public double calcDeliveriesProfit(Store storeOrNull){
        return storeOrNull.getOrdersSerialId().stream()
                .mapToDouble(closedOrderId -> getOrderSerialIdToClosedOrder().get(closedOrderId).getDeliveryPriceAfterOrder())
                .sum();
    }

    public int getIdOfShopWithCheapestItem(int itemSerialId){
        Set<Store> filteredStores = storeSerialIdToStore.values().stream()
                .filter(store -> store.getItemsSerialIdToAvailableItemInStore().containsKey(itemSerialId))
                .collect(Collectors.toSet());

        // Converting to ICaculable to calc price and generating a map of SDMItemsInterface.ICalculable to Store
        ICalculable calculableAvailableItem = null;
        HashMap<ICalculable, Store> calculableItemsToStore = new HashMap<ICalculable, Store>();
        for (Store filteredStore: filteredStores) {
            calculableAvailableItem = (ICalculable)filteredStore.getItemsSerialIdToAvailableItemInStore().get(itemSerialId);
            calculableItemsToStore.put(calculableAvailableItem,filteredStore);
        }

        // Finding the minimal value of the item compared by its calculated price and returning the matching store using the map we created
        ICalculable calculableCheapestItem = calculableItemsToStore.keySet().stream()
                .min(Comparator.comparing(ICalculable::calcTotalPriceOfItemOrdered))
                .get();

        return calculableItemsToStore.get(calculableCheapestItem).getSerialNumber();
    }

    public double getAmountSoldOfCertainItem(int itemSerialId){
        return getItemBySerialId(itemSerialId).getTotalAmountSold();
    }

    public HashMap<Store, LinkedList<OrderedItem>> getMapOfShopWithCheapestItemsFromSet(HashSet<OrderedItem> selectedItems) {
        HashMap<Store, LinkedList<OrderedItem>> cheapestItemsInSystem = new HashMap<Store, LinkedList<OrderedItem>>();

        for (OrderedItem selectedItem : selectedItems) {
            int currentShopOffersCheapestId = getIdOfShopWithCheapestItem(selectedItem.serialNumber);

            Store currentShopOffersCheapestItem = storeSerialIdToStore.get(currentShopOffersCheapestId);
            AvailableItemInStore cheapestItem = currentShopOffersCheapestItem.getItemsSerialIdToAvailableItemInStore().get(selectedItem.serialNumber);
            OrderedItem orderedItem = selectedItem instanceof OrderedItemByWeight ?
                    new OrderedItemByWeight(cheapestItem, selectedItem.amountOfOrderedItemByUnits, ((OrderedItemByWeight) selectedItem).getAmountOfOrderedItemInWeight()) :
                    new OrderedItemByUnit(cheapestItem, selectedItem.amountOfOrderedItemByUnits);

            if (cheapestItemsInSystem.containsKey(currentShopOffersCheapestItem)) {
                cheapestItemsInSystem.get(currentShopOffersCheapestItem).add(orderedItem);
            }
            else{
                LinkedList<OrderedItem> listToAdd = (LinkedList<OrderedItem>) Arrays.asList(orderedItem);
                cheapestItemsInSystem.put(currentShopOffersCheapestItem, listToAdd);
            }
        }

        return cheapestItemsInSystem;
    }

    public int getTotalAmountOfShopsSellingACertainItem(int itemSerialId){
        int totalAmountOfStoresSelling =
                 storeSerialIdToStore.values().stream()
                        .filter(store -> store.getItemsSerialIdToAvailableItemInStore().containsKey(itemSerialId))
                        .map(store -> store.getItemsSerialIdToAvailableItemInStore().get(itemSerialId))
                        .collect(Collectors.reducing(0 ,e -> 1, Integer::sum));

        return totalAmountOfStoresSelling;
    }

    public void addClosedOrderToStoreHistory(ClosedOrder closedOrder){
        getOrdersSerialIdSet().add(closedOrder.serialNumber);
    }

    private boolean isItemExists(int itemSerialId){
        return itemSerialIdToItem.getOrDefault(itemSerialId, null) !=null;
    }

    public boolean isStoreExists(int storeSerialId){
        return storeSerialIdToStore.getOrDefault(storeSerialId, null) !=null;
    }

    public ClosedOrder getOrderBySerialId(int orderSerialId){
        return orderSerialIdToClosedOrder.getOrDefault(orderSerialId, null);
    }

    public void addClosedOrderToHistory(ClosedOrder closedOrder){
        orderSerialIdToClosedOrder.put(closedOrder.serialNumber, closedOrder);
    }

    public boolean isLocationOccupiedByStore(Location certainLocation){
        return locationToStore.getOrDefault(certainLocation, null) != null;
    }

    public AvailableItemInStore getItemBySerialId(int itemSerialId){
        return itemSerialIdToItem.getOrDefault(itemSerialId, null);
    }

    public HashMap<Integer, ClosedOrder> getOrderSerialIdToClosedOrder() {
        return orderSerialIdToClosedOrder;
    }

    public BasicItem getEntityItemBySerialId(int entityItemSerialId){
        return itemSerialIdToItem.getOrDefault(entityItemSerialId,null);
    }

    public Store getStoreBySerialId (int storeSerialId){
        return storeSerialIdToStore.getOrDefault(storeSerialId,null);
    }

    public Store getStoreByLocation (Location storeLocation){
        return locationToStore.getOrDefault(storeLocation,null);
    }

    public HashMap<Location, Store> getLocationToStoresMap() {
        return locationToStore;
    }

    public HashMap<Integer, Store> getStoreSerialIdToShopMap() {
        return storeSerialIdToStore;
    }

    public HashMap<Integer, AvailableItemInStore> getItemSerialIdToItem() {
        return itemSerialIdToItem;
    }

    public HashSet<Integer> getStoresSerialId() {
        return storesSerialId;
    }

    public HashSet<Integer> getItemsSerialIdSet() {
        return itemsSerialIdSet;
    }

    public HashSet<Integer> getOrdersSerialIdSet() {
        return ordersSerialIdSet;
    }

    public int getCurrentOrderSerialIdInStorage() {
        return currentOrderSerialIdInStorage;
    }

    public void setCurrentOrderSerialIdInStorage(int currentOrderSerialIdInStorage) {
        currentOrderSerialIdInStorage = currentOrderSerialIdInStorage;
    }

    public boolean areThereOrdersInSystem() {
        return getOrderSerialIdToClosedOrder().isEmpty() == false;
    }

    public boolean areThereSystemItemsNotBeingSelledInStores() {
        boolean areThereNoneStoreRelatedItemsInSystem = false;

        for (int itemSerialId : getItemsSerialIdSet()) {

            int numberOfShopsItemIsNotAvailable =
                    getStoreSerialIdToShopMap().values().stream()
                            .filter(store -> !store.getItemsSerialIdToAvailableItemInStore().containsKey(itemSerialId))
                            .map(e -> 1)
                            .reduce(0, Integer::sum);
            if (numberOfShopsItemIsNotAvailable == getLocationToStoresMap().values().size()){
                areThereNoneStoreRelatedItemsInSystem = true;
                break;
            }
        }

        return areThereNoneStoreRelatedItemsInSystem;
    }

    public boolean areThereStoresWithNoAvailableItems() {
        int emptyStores =
                getStoreSerialIdToShopMap().values().stream()
                        .filter(store -> store.getItemsSerialIdToAvailableItemInStore().isEmpty())
                        .map(filteredStore -> 1)
                        .reduce(0, Integer::sum);

        return emptyStores != 0;
    }

    public void assClosedOrderToHistory(ClosedStaticOrder closedOrder) {
        getOrderSerialIdToClosedOrder().putIfAbsent(closedOrder.serialNumber, closedOrder);
    }
}
