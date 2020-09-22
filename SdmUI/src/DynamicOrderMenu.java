import java.util.Scanner;
// TODO -    // Yet to be implemented
public class DynamicOrderMenu extends OrderMenu{

    public DynamicOrderMenu(MapperAndStorage mapperAndStorage, DetailsPrinter detailsPrinter) {
        super(mapperAndStorage, detailsPrinter);
    }

    public Location getLocationInput() throws ExitException {
        boolean creationWenSuccessfully = true;
        int xCoordinate, yCoordinate;
        Location returnedLocation =null;

        do{
            try{
                Scanner sc = new Scanner(System.in);
                System.out.println("Please enter the location for delivery:");
                System.out.println("coordinate x:");
                xCoordinate = getCoordinateInput(sc);
                System.out.println("coordinate y:");
                yCoordinate = getCoordinateInput(sc);
                returnedLocation = new Location(xCoordinate, yCoordinate);
                if(isLocationOccupied(returnedLocation) || mapperAndStorage.getLocationToStoresMap().containsKey(returnedLocation)){
                    System.out.println("delivery location is occupied by a store, please try again");
                    creationWenSuccessfully = false;
                }else{
                    creationWenSuccessfully = true;
                }
            } catch (ExitException e){
                throw new ExitException("Exiting...", "Pressed q");
            }
            catch (InvalidCoordinateException e) {
                System.out.println(e.getMessage());
                System.out.println("referred to: " +"("+ e.getCoordinateX() + ", " + e.getCoordinateY() + ")");
                creationWenSuccessfully = false;
            }
        }while(!creationWenSuccessfully);

        return returnedLocation;
   }


    private boolean isLocationOccupied(Location deliveryLocation){
        return false;
    }

    public void makeDynamicOrder(){
        System.out.println("yet to be implemented");
    }

    public void getItemsInputUntilQuitSign(OpenedDynamicOrder openedOrder){
        System.out.println("yet to be implementer");
    }

}
