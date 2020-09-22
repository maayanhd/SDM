public class ItemDoesNotExistInStoresException extends Exception{
    BasicItem item;

    public ItemDoesNotExistInStoresException(String message, BasicItem item) {
        super(message);
        this.item = item;
    }

    public BasicItem getItem() {
        return item;
    }
}
