import jaxb.schema.generated.SDMCustomer;

public class Customer {

    private Location location;
    private String name;
    private int serialNumber;
    private static int counter = 0;

    public Customer(Location customerLocation, String customerName) {
        this.location = customerLocation;
        this.name = customerName;
        serialNumber = counter++;
    }

    public Customer(SDMCustomer JAXBCustomer) throws InvalidCoordinateException {
        this.location = new Location(JAXBCustomer.getLocation());
        this.name = JAXBCustomer.getName();
        this.serialNumber = JAXBCustomer.getId(); // TODO: 20/09/2020  check is id unique before adding
    }
}
