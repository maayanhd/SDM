public class ItemNotFoundException extends Exception {
    private int serialId;
    private String name;

    public ItemNotFoundException(String message, int serialId, String name) {
        super(message);
        this.serialId = serialId;
        this.name = name;
    }

    public ItemNotFoundException(String message, int serialId) {
        super(message);
        this.serialId = serialId;
    }

    public ItemNotFoundException(String message) {
        super(message);
    }

    public int getSerialId() {
        return serialId;
    }

    public String getName() {
        return name;
    }
}
