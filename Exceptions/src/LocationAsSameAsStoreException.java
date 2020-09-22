public class LocationAsSameAsStoreException extends Exception{
    private Location storeLocation;
    private Location userLocation;

    public LocationAsSameAsStoreException(String message, Location storeLocation, Location userLocation) {
        super(message);
        this.storeLocation = storeLocation;
        this.userLocation = userLocation;
    }

    public Location getStoreLocation() {
        return storeLocation;
    }

    public Location getUserLocation() {
        return userLocation;
    }
}
