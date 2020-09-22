public class DuplicateLocationException extends Exception {
    Location invalidLocation;
    String name;

    public String getName() {
        return name;
    }

    public DuplicateLocationException(String message, Location invalidLocation, String name) {
        super(message);
        this.invalidLocation = invalidLocation;
        this.name = name;
    }

    public DuplicateLocationException(String message, Location invalidLocation) {
        super(message);
        this.invalidLocation = invalidLocation;
    }

    public Location getInvalidLocation() {
        return invalidLocation;
    }

    public void setInvalidLocation(Location invalidLocation) {
        this.invalidLocation = invalidLocation;
    }
}
