import SDMItemsInterface.IDiscrete;
import SDMItemsInterface.IWeightable;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

public class StaticOrderMenu extends OrderMenu{

    public StaticOrderMenu(MapperAndStorage mapperAndStorage, DetailsPrinter detailsPrinter) {
        super(mapperAndStorage, detailsPrinter);
    }

    public void makeStaticOrder() throws ExitException {
        boolean isShowingItemDetails = false,
                isShowingOrdersDetails = false;
        boolean isAskingToExit;
        detailsPrinter.showStoresDetailsFilteredByParameters(isShowingItemDetails, isShowingItemDetails);
        int shopSerialId = getSerialIdOfShopInput();
        Date dateInput = null;
        boolean isDateInputCorrectParsingwise = false;

        do{
            try{
                dateInput = getDateInput();
                isDateInputCorrectParsingwise = true;
            }catch (ParseException e) {
                System.out.println("Incorrect date and time format, please enter date and time in the following format: dd/mm-hh:mm" );
                isDateInputCorrectParsingwise = false;
            }
        }while(!isDateInputCorrectParsingwise);

        Store visitedStore = mapperAndStorage.getStoreSerialIdToShopMap().get(shopSerialId);
        Location deliveryLocation = getLocationInput(visitedStore.getLocationOfShop());
        OpenedStaticOrder openedOrder = new OpenedStaticOrder(dateInput, visitedStore);
        try{
            getItemInputUntilQuitSign(visitedStore, openedOrder);
        } catch(ExitException e){
            System.out.println("Processing..." + "\n");
        }

        boolean isApprovingOrderInput = false,
                isAnswerCorrectInFormat = false;
        printSumDetailsOfOrder(openedOrder, visitedStore.getDeliveryPpk() , visitedStore.getLocationOfShop(), deliveryLocation);

        do{
            try{
                isApprovingOrderInput = getWhetherUserApprovesOrderInput();
                isAnswerCorrectInFormat = true;
            } catch(IOException e){
                System.out.println("please try again and press on the y button for yes and the n button for no");
                isAnswerCorrectInFormat = false;
            }
        }while(!isAnswerCorrectInFormat);

        if(isApprovingOrderInput){
            ClosedStaticOrder closedOrder =  openedOrder.closeOrder(deliveryLocation);
            mapperAndStorage.addClosedOrderToHistory(closedOrder);
            mapperAndStorage.addClosedOrderToStoreHistory(closedOrder);
            visitedStore.getOrdersSerialId().add(closedOrder.serialNumber);
        }
    }

    public void getItemInputUntilQuitSign(Store currentStore, OpenedStaticOrder openedOrder) throws ExitException {
        boolean isAskingToQuite = false;
        Scanner sc= new Scanner(System.in);
        do{
            try{
                // print items
                detailsPrinter.showItemsInSystemAndPricesOfStore(currentStore.getSerialNumber());
                // input item serial id
                int itemSerialId = getItemSerialIdInput(currentStore.getSerialNumber());
                // bring item - get quantity or weight
                // input quantity if item
                double quantity = getQuantityOfItemToBuyInput(currentStore.getItemsSerialIdToAvailableItemInStore().get(itemSerialId).typeToMeasureBy);
                OrderedItem chosenItem = openedOrder.getItemOrderedSerialNumToOrderedItemMap().getOrDefault(itemSerialId, null);
                // if exists update
                if (chosenItem != null){
                    updateItemByType(chosenItem, quantity);
                }
                else{
                    chosenItem = instantiateItemByType(currentStore, itemSerialId, quantity);
                    openedOrder.itemOrderedSerialNumToOrderedItemMap.put(itemSerialId, chosenItem);
                }
            } // IF Q END METHOD
            catch(ItemNotFoundException e){
                System.out.println("no such an item, please try again");
            }
            catch(ExitException e){
                isAskingToQuite = true;
                throw new ExitException("Exiting...", "pressed q");
            }
        }while(!isAskingToQuite);

    }

    private OrderedItem instantiateItemByType(Store currentStore, int chosenItemSerialId, double quantity) throws ItemNotFoundException {
        AvailableItemInStore chosenAvailableItem = currentStore.getItemBySerialId(chosenItemSerialId);
        OrderedItem orderedItem = null;

        if(chosenAvailableItem != null){
            orderedItem = chosenAvailableItem.typeToMeasureBy.equals(TypeOfMeasure.QUANTITY) ?
                    new OrderedItemByUnit(chosenAvailableItem,(int)quantity) : new OrderedItemByWeight(chosenAvailableItem, 1, quantity);
        }
        else{
            throw new ItemNotFoundException("no such an item matching the given serial id", chosenItemSerialId, mapperAndStorage.getItemBySerialId(chosenItemSerialId).getName());
        }

        return orderedItem;
    }

    private void updateItemByType(OrderedItem itemToUpdate, double quantity){
        if(itemToUpdate instanceof OrderedItemByWeight) {
            IWeightable weightableItemToUpdate = ((OrderedItemByWeight) itemToUpdate);
            weightableItemToUpdate.increaseAmountOfItemOrderedByWeight(quantity);
        }
        else{
            IDiscrete discreteItemToUpdate = ((OrderedItemByUnit)itemToUpdate);
            discreteItemToUpdate.increaseAmountOfItemOrderedByUnits((int)quantity);
        }
    }


    public int getSerialIdOfShopInput() throws ExitException{
        Scanner sc = new Scanner(System.in);
        int storeSerialId = 0;
        boolean isProcessWentSuccessfully = false;
        String strInput;

        do{
            System.out.println("Please enter a unique shop serial Id: ");
            strInput = sc.next();

            if(tryParseInt(strInput)){
                storeSerialId= Integer.parseInt(strInput);
                if(mapperAndStorage.getStoreSerialIdToShopMap().containsKey(storeSerialId)){
                    isProcessWentSuccessfully = true;
                }
                else{
                    System.out.println("There is no store with such an id, please try again");
                }
            }
            else{
                System.out.println("The serial id must be a whole number");
            }
        }while(!isProcessWentSuccessfully);

        return storeSerialId;
    }

    public void printSumDetailsOfOrder(OpenedStaticOrder openedOrder, int PPK, Location storeLocation, Location deliveryDestination){
        detailsPrinter.showItemsDetailsOfOpenedOrder(openedOrder);
        System.out.println("Price Per Kilometer: " + PPK);
        System.out.println("Air distance from store: " + DetailsPrinter.getDf2Digit().format(deliveryDestination.getAirDistanceToOtherLocation(storeLocation)));
        System.out.println("Delivery price: " + DetailsPrinter.getDf2Digit().format(openedOrder.calcTotalDeliveryPrice(deliveryDestination)));
    }

    public Location getLocationInput(Location storeLocation) throws ExitException {
        boolean creationWenSuccessfully = true;
        Scanner sc = new Scanner(System.in);
        int xCoordinate, yCoordinate;
        Location returnedLocation =null;

        do{
            try{
                System.out.println("Please enter the location for delivery:");
                System.out.println("coordinate x:");
                xCoordinate = getCoordinateInput(sc);
                System.out.println("coordinate y:");
                yCoordinate = getCoordinateInput(sc);
                returnedLocation = new Location(xCoordinate, yCoordinate);
                if(returnedLocation.equals(storeLocation) || mapperAndStorage.getLocationToStoresMap().containsKey(returnedLocation)){
                    System.out.println("delivery location is occupied by a store, please try again");
                    creationWenSuccessfully = false;
                }else{
                    creationWenSuccessfully = true;
                }
            } catch (InvalidCoordinateException e) {
                System.out.println("referred to: " +"("+ e.getCoordinateX() + ", " + e.getCoordinateY() + ")");
                creationWenSuccessfully = false;
            }
            catch( ExitException e) {
                throw new ExitException("Exiting...", "Pressed q");
            }
        }while(!creationWenSuccessfully);

        return returnedLocation;
    }
}
