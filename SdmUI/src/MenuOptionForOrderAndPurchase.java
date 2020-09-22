
public class MenuOptionForOrderAndPurchase extends OrderMenu {
    private MapperAndStorage storageMapper;

    public MenuOptionForOrderAndPurchase(MapperAndStorage mapperAndStorage, DetailsPrinter detailsPrinter) {
        super(mapperAndStorage, detailsPrinter);
        this.storageMapper = storageMapper;
    }

    public void printOrderTypeChoiceMenu(){
        OrderOption.STATIC_ORDER.printOption();
        OrderOption.DYNAMIC_ORDER.printOption();
    }

//    public void orderAndPurchase() {
//        boolean isProcessWentSuccessfully = false;
//
//        do {
//            printOrderTypeChoiceMenu();
//            int optionNum = getOptionInput(OrderOption.ORDER_ENUM_BEHAVIOUR);
//            if (OrderOption.ORDER_ENUM_BEHAVIOUR.containsChoiceNum(optionNum)) {
//                try {
//                    chooseOrderTypeMenu(optionNum);
//                } catch(ExitException e){
//                    isProcessWentSuccessfully = true;
//                }
//            } else {
//                System.out.println("no such option, please try again");
//            }
//        }while (!isProcessWentSuccessfully);
//
//    }

//    public void chooseOrderTypeMenu(int numOption) throws ExitException {
//        switch(convertNumOptionToOrderEnum(numOption)){
//            case STATIC_ORDER:
//                StaticOrderMenu staticOrderMenu = new StaticOrderMenu(mapperAndStorage,detailsPrinter);
//                staticOrderMenu.makeStaticOrder();
//                break;
//            case DYNAMIC_ORDER:
//                DynamicOrderMenu dynamicOrderMenu = new DynamicOrderMenu(mapperAndStorage, detailsPrinter);
//                dynamicOrderMenu.makeDynamicOrder();
//                break;
//            default:
//                throw new IllegalStateException("Unexpected value: " + convertNumOptionToOrderEnum(numOption));
//        }
//    }

    private OrderOption convertNumOptionToOrderEnum(int chosenOptionNum) {
        for(OrderOption option : OrderOption.values())
        {
            if(option.getOptionNum() == chosenOptionNum)
            {
                return option;
            }
        }
        return null;
    }

    @Override
    int getOptionInput(IOptionWiseFunctional optionwiseBasicEnumOption) {
        return super.getOptionInput(OrderOption.ORDER_ENUM_BEHAVIOUR);
    }
}
