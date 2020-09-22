import jaxb.schema.generated.SDMStore;

import java.util.HashMap;
import java.util.HashSet;

public class Store {

    private final HashMap<Integer, AvailableItemInStore> itemsSerialIdToAvailableItemInStore = new HashMap<Integer, AvailableItemInStore>();
    private final HashSet<Integer> itemsSerialIdSet = new HashSet<Integer>();
    private final HashSet<Integer> ordersSerialId= new HashSet<Integer>();

    private int deliveryPpk;
    private int serialNumber = 0;
    private String name;
    private Location locationOfShop;
    private HashMap<String, BasicPromotion> promotionNameToPromotion = new HashMap<>();

    public Store(String name, int deliveryPpk, Location locationOfShop, int serialNumber) {
        this.name = name;
        this.deliveryPpk = deliveryPpk;
        this.locationOfShop = locationOfShop;
        this.serialNumber = ++counter;
    }

    public HashSet<Integer> getOrdersSerialId() {
        return ordersSerialId;
    }

    public HashSet<Integer> getItemsSerialIdSet() {
        return itemsSerialIdSet;
    }

    public Store(SDMStore JAXBEStore) throws IllegalArgumentException, InvalidCoordinateException, DuplicateSerialIDException {
        this.name = JAXBEStore.getName();
        this.serialNumber = JAXBEStore.getId();
        this.locationOfShop = new Location(JAXBEStore.getLocation());
        this.deliveryPpk = JAXBEStore.getDeliveryPpk();
        if(deliveryPpk <= 0){
            throw new IllegalArgumentException("Illegal delivery PPK, must be a positive number");
        }

        this.locationOfShop = new Location(JAXBEStore.getLocation().getX(), JAXBEStore.getLocation().getY());
        this.serialNumber = JAXBEStore.getId();
    }
    private static int counter =0;

    public HashMap<Integer, AvailableItemInStore> getItemsSerialIdToAvailableItemInStore() {
        return itemsSerialIdToAvailableItemInStore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDeliveryPpk() {
        return deliveryPpk;
    }

    public void setDeliveryPpk(int deliveryPpk) {
        this.deliveryPpk = deliveryPpk;
    }

    public void setLocationOfShop(Location locationOfShop) {
        this.locationOfShop = locationOfShop;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Location getLocationOfShop() {
        return locationOfShop;
    }

    private void addItemToShop(AvailableItemInStore itemToAdd) throws DuplicateSerialIDException {
        if(isItemIdUnique(itemToAdd.serialNumber)){
            itemsSerialIdToAvailableItemInStore.put(itemToAdd.serialNumber, itemToAdd);
        }
    }

    @Override
    public String toString() {
        return  "Shop Details: " + "\n" +
                "SerialNumber: " + serialNumber + "\n" +
                "Name: '" + name +"\n" +
                "DeliveryPpk: " + deliveryPpk + "\n" +
                "Location: " + locationOfShop.toString() + "\n";
    }

    public boolean isItemIdUnique(int itemSerialId){
        return itemsSerialIdToAvailableItemInStore.getOrDefault(new Integer(itemSerialId), null) == null;
    }

    public AvailableItemInStore getItemBySerialId(int itemSerialId){
        return itemsSerialIdToAvailableItemInStore.getOrDefault(itemSerialId, null);
    }
}
