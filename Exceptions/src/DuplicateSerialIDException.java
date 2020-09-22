public class DuplicateSerialIDException extends Exception {

    int objectId;
    String objectName;

    public DuplicateSerialIDException(String message, int objectId, String objectName) {
        super(message);
        this.objectId = objectId;
        this.objectName = objectName;
    }

    public String getObjectName() {
        return objectName;
    }

    public int getObjectId() {
        return objectId;
    }
}
