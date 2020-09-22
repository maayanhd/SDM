import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class OrderMenu extends BasicMenu {

    protected MapperAndStorage mapperAndStorage;
    DetailsPrinter detailsPrinter;

    public OrderMenu(MapperAndStorage mapperAndStorage, DetailsPrinter detailsPrinter) {
        this.mapperAndStorage = mapperAndStorage;
        this.detailsPrinter = detailsPrinter;
    }

    public enum OrderOption implements IOptionWiseFunctional {
        ORDER_ENUM_BEHAVIOUR("Order enum behaviour", 0),
        STATIC_ORDER("Static Order", 1),
        DYNAMIC_ORDER("Dynamic Order", 2);

        private final String meaning;
        private final int optionNum;

        public String getMeaning() {
            return meaning;
        }

        public int getOptionNum() {
            return optionNum;
        }

        OrderOption(String meaning, int optionNum) {
            this.meaning = meaning;
            this.optionNum = optionNum;
        }

        public void printOption(){
            System.out.printf("%d. %s\n%n", this.optionNum, this.meaning);
        }

        @Override
        public boolean numberEqualsToOptionNum(int numToCompare) {
            return numToCompare == this.optionNum;
        }

        public boolean containsChoiceNum(int choiceNum){
            return OrderOption.DYNAMIC_ORDER.optionNum == choiceNum || OrderOption.STATIC_ORDER.optionNum == choiceNum;
        }

    }

    public double getQuantityOfItemToBuyInput(TypeOfMeasure typeToMeasureBy) throws ExitException {
        System.out.println("Press q to exit at any moment...");
        Scanner sc = new Scanner(System.in);
        boolean isProcessWentSuccessfully = false;
        double quantityOfItem = 0;
        String inputStr;
        do{
            System.out.println("Please enter desired quantity:");
            inputStr = sc.next();
            String exitStr = inputStr.toLowerCase();
            if(exitStr.equals("q")){
                throw new ExitException("Exiting...","pressed q button");
            }

            boolean isCorrectParsingwise = typeToMeasureBy.equals(TypeOfMeasure.QUANTITY) ? tryParseInt(inputStr) : tryParseDouble(inputStr);
            if(isCorrectParsingwise){
                quantityOfItem = typeToMeasureBy.equals(TypeOfMeasure.QUANTITY) ? Integer.parseInt(inputStr) : Double.parseDouble(inputStr);
                if(quantityOfItem > 0){
                    isProcessWentSuccessfully = true;
                }
                else{
                    System.out.println("Quantity must be a positive number, please try again");
                }
            }
            else{
                System.out.println("Quantity must match the purchase category, please try again");
            }
        } while(!isProcessWentSuccessfully);

        return quantityOfItem;
    }

    @Override
    int getOptionInput(IOptionWiseFunctional optionwiseBasicEnumOption) {
        return super.getOptionInput(OrderOption.STATIC_ORDER);
    }

    public int getItemSerialIdInput(int serialIdStore) throws ExitException {
        boolean isProcessWentSuccessfully = false;
        int itemSerialId = 0;
        Scanner sc= new Scanner(System.in);
        String inputString;

        do{
            System.out.println("Please enter a unique item serial id:");
            inputString = sc.next();

            String exitStr = inputString.toLowerCase();
            if(exitStr.equals("q")){
                System.out.println("Exiting...");
                throw new ExitException("Exiting...","pressed q button");
            }
            if(tryParseInt(inputString)){
                itemSerialId = Integer.parseInt(inputString);
                if(mapperAndStorage.getStoreSerialIdToShopMap().get(serialIdStore).getItemsSerialIdToAvailableItemInStore().containsKey(itemSerialId)){
                    isProcessWentSuccessfully= true;
                }
                else{
                    System.out.println("there is no item with such serial Id, please try again");
                }
            }
            else{
                System.out.println("Serial id must be a whole number");
            }
        }while(!isProcessWentSuccessfully);

        return itemSerialId;
    }

    public boolean getWhetherUserApprovesOrderInput() throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("are you sure you want to make an order? y/n");
        String inputLine = sc.next().toLowerCase();

        if(!inputLine.equals("y") && !inputLine.equals("n")){
            throw new IOException("incorrect input, answer must be y/n");
        }

        return inputLine.equals("y");
    }

    public Date getDateInput() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM-HH:mm");
        dateFormat.setLenient(false);
        Scanner sc = new Scanner(System.in);

        System.out.println("Please enter a date:");

        return dateFormat.parse(sc.nextLine());
    }

    public int getCoordinateInput(Scanner sc) throws InvalidCoordinateException, ExitException {
        boolean isProcessWentSuccessfully = false;
        int coordinate = -1;
        String strInput;

        do{
            strInput = sc.next();
            String exitStr = strInput.toLowerCase();
            if(exitStr.equals("q")){
                System.out.println("Exiting...");
                throw new ExitException("Exiting...","pressed q button");
            }
            if(tryParseInt(strInput))
            {
                coordinate = Integer.parseInt(strInput);
                if(coordinate >= 1 && coordinate <= 50){
                    isProcessWentSuccessfully = true;
                }
                else{
                    System.out.println("Coordinate must be in range between 1 to 50, please try again");
                }
            }
            else{
                System.out.println("coordinate must be a number (whole number),  please try again");
            }

        } while(!isProcessWentSuccessfully);

        if(!isProcessWentSuccessfully){
            throw new InvalidCoordinateException("The coordinates must be in the range between 1 to 50", coordinate);
        }

        return coordinate;
    }
}
