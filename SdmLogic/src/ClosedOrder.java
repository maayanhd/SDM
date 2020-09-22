import SDMItemsInterface.ICalculable;

import java.util.Date;

public class ClosedOrder extends Order {
    protected int totalAmountOfItemTypes;
    protected int totalAmountOfItemsByUnit;
    protected double deliveryPriceAfterOrder;
    protected double totalPriceOfOrder;
    protected double totalPriceOfItems;
    protected int serialNumber;
    protected static int counter=0;

    public ClosedOrder(Date date, int totalAmountOfItemTypes, double deliveryPriceAfterOrder, int totalAmountOfItemsByUnit, double totalPriceOfOrder, double totalPriceOfItems) {
        super(date);
        this.totalAmountOfItemTypes = totalAmountOfItemTypes;
        this.totalAmountOfItemsByUnit = totalAmountOfItemsByUnit;
        this.totalPriceOfOrder = totalPriceOfOrder;
        this.totalPriceOfItems = totalPriceOfItems;
        this.serialNumber = ++counter;
        this.deliveryPriceAfterOrder = deliveryPriceAfterOrder;

    }

    public int getTotalAmountOfItemTypes() {
        return totalAmountOfItemTypes;
    }

    public void setTotalAmountOfItemTypes(int totalAmountOfItemTypes) {
        this.totalAmountOfItemTypes = totalAmountOfItemTypes;
    }

    public int getTotalAmountOfItemsByUnit() {
        return totalAmountOfItemsByUnit;
    }

    public void setTotalAmountOfItemsByUnit(int totalAmountOfItemsByUnit) {
        this.totalAmountOfItemsByUnit = totalAmountOfItemsByUnit;
    }

    public double getDeliveryPriceAfterOrder() {
        return deliveryPriceAfterOrder;
    }

    public void setDeliveryPriceAfterOrder(double deliveryPriceAfterOrder) {
        this.deliveryPriceAfterOrder = deliveryPriceAfterOrder;
    }

    public double getTotalPriceOfOrder() {
        return totalPriceOfOrder;
    }

    public void setTotalPriceOfOrder(double totalPriceOfOrder) {
        this.totalPriceOfOrder = totalPriceOfOrder;
    }

    public double getTotalPriceOfItems() {
        return totalPriceOfItems;
    }

    public void setTotalPriceOfItems(double totalPriceOfItems) {
        this.totalPriceOfItems = totalPriceOfItems;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    private double calcTotalAmountOfSoldItem(int itemSerialId){
        ICalculable calculableOrderedItem =(ICalculable) super.itemOrderedSerialNumToOrderedItemMap.get(itemSerialId);

        return calculableOrderedItem.calcTotalAmountOfItemOrdered();
    }
}
