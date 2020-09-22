import java.util.Date;
import java.util.HashMap;

public class Order {

    protected final HashMap<Integer, OrderedItem> itemOrderedSerialNumToOrderedItemMap = new HashMap<Integer, OrderedItem>();

    protected Date date;
    public Order(Date date) {
        this.date = date;
    }

    public HashMap<Integer, OrderedItem> getItemOrderedSerialNumToOrderedItemMap() {
        return itemOrderedSerialNumToOrderedItemMap;
    }

    public Date getDate() {
        return date;
    }

    private void addItemToOrderMap(OrderedItem itemToAdd) throws DuplicateSerialIDException{
        if(!isItemExistsInOrder(itemToAdd.serialNumber)){
            itemOrderedSerialNumToOrderedItemMap.put(itemToAdd.serialNumber, itemToAdd);
        }
    }

    private double getAmountOfCertainItemByUnit(int itemSerialId) throws ItemNotFoundException {
        OrderedItem currentItem = getOrderedItemById(itemSerialId);
        double amountByUnit;
        if(currentItem != null){
            amountByUnit= currentItem.getAmountOfOrderedItemByUnits();
        }
        else{
            throw new ItemNotFoundException("No such Item", itemSerialId);
        }

        return amountByUnit;
    }

    private boolean isItemExistsInOrder(int itemSerialId){
        return itemOrderedSerialNumToOrderedItemMap.getOrDefault(itemSerialId, null) != null;
    }

    private OrderedItem getOrderedItemById(int itemSerialId){
        return itemOrderedSerialNumToOrderedItemMap.getOrDefault(itemSerialId, null);
    }

}
