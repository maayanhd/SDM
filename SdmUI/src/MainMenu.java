public class MainMenu extends BasicMenu {
    private DetailsPrinter detailsPrinter;
    private SDMSystem SdmSystem;
    private final MenuOptionsForReadingXmlFile menuOptionsForReadingXMLFile;
    private final MenuOptionForOrderAndPurchase menuOptionsForOrderAndPurchase;
    private OrderMenu orderMenu;
    private boolean isLoadXmlProcessWentSuccessfully = false;

    public void printHeadline(){
        System.out.println(
                    "                                                                                                                                                                        \n" +
                            " @@@@@@   @@@  @@@  @@@@@@@   @@@@@@@@  @@@@@@@      @@@@@@@   @@@  @@@  @@@@@@@   @@@@@@@@  @@@@@@@      @@@@@@@@@@    @@@@@@   @@@@@@@   @@@  @@@  @@@@@@@@  @@@@@@@  \n" +
                            "@@@@@@@   @@@  @@@  @@@@@@@@  @@@@@@@@  @@@@@@@@     @@@@@@@@  @@@  @@@  @@@@@@@@  @@@@@@@@  @@@@@@@@     @@@@@@@@@@@  @@@@@@@@  @@@@@@@@  @@@  @@@  @@@@@@@@  @@@@@@@  \n" +
                            "!@@       @@!  @@@  @@!  @@@  @@!       @@!  @@@     @@!  @@@  @@!  @@@  @@!  @@@  @@!       @@!  @@@     @@! @@! @@!  @@!  @@@  @@!  @@@  @@!  !@@  @@!         @@!    \n" +
                            "!@!       !@!  @!@  !@!  @!@  !@!       !@!  @!@     !@!  @!@  !@!  @!@  !@!  @!@  !@!       !@!  @!@     !@! !@! !@!  !@!  @!@  !@!  @!@  !@!  @!!  !@!         !@!    \n" +
                            "!!@@!!    @!@  !@!  @!@@!@!   @!!!:!    @!@!!@!      @!@  !@!  @!@  !@!  @!@@!@!   @!!!:!    @!@!!@!      @!! !!@ @!@  @!@!@!@!  @!@!!@!   @!@@!@!   @!!!:!      @!!    \n" +
                            " !!@!!!   !@!  !!!  !!@!!!    !!!!!:    !!@!@!       !@!  !!!  !@!  !!!  !!@!!!    !!!!!:    !!@!@!       !@!   ! !@!  !!!@!!!!  !!@!@!    !!@!!!    !!!!!:      !!!    \n" +
                            "     !:!  !!:  !!!  !!:       !!:       !!: :!!      !!:  !!!  !!:  !!!  !!:       !!:       !!: :!!      !!:     !!:  !!:  !!!  !!: :!!   !!: :!!   !!:         !!:    \n" +
                            "    !:!   :!:  !:!  :!:       :!:       :!:  !:!     :!:  !:!  :!:  !:!  :!:       :!:       :!:  !:!     :!:     :!:  :!:  !:!  :!:  !:!  :!:  !:!  :!:         :!:    \n" +
                            ":::: ::   ::::: ::   ::        :: ::::  ::   :::      :::: ::  ::::: ::   ::        :: ::::  ::   :::     :::     ::   ::   :::  ::   :::   ::  :::   :: ::::     ::    \n" +
                            ":: : :     : :  :    :        : :: ::    :   : :     :: :  :    : :  :    :        : :: ::    :   : :      :      :     :   : :   :   : :   :   :::  : :: ::      :     \n" +
                            "                                                                                                                                                                        "
                       );
    }

    public MainMenu() {
        this.SdmSystem = new SDMSystem();
        this.detailsPrinter = new DetailsPrinter(SdmSystem.getStorageMapper());
        this.menuOptionsForReadingXMLFile = new MenuOptionsForReadingXmlFile();
        this.menuOptionsForOrderAndPurchase = new MenuOptionForOrderAndPurchase(SdmSystem.getStorageMapper(), detailsPrinter);
        this.orderMenu = new OrderMenu(SdmSystem.getStorageMapper(), detailsPrinter);
        boolean isLoadingXmlWentSuccessfully = false;
    }

    public enum MainMenuOption implements IOptionWiseFunctional  {
        MAIN_MENU_ENUM_BEHAVIOUR("Polymorphic base", 0),
        READ_FROM_XML_FILE("Read from XML file", 1),
        SHOW_STORE_DETAILS("Show store details", 2),
        SHOW_SYSTEM_ITEM_DETAILS("Show all available items", 3),
        ORDER_AND_PURCHASE("Order and purchase", 4),
        SHOW_ORDERS_HISTORY("Show order history", 5),
        EXIT("Exit", 6);

        private final String meaning;
        private final int optionNum;

        MainMenuOption(String meaning, int optionNum) {
            this.meaning = meaning;
            this.optionNum = optionNum;
        }

        public void printOption(){
            System.out.printf("%d. %s%n", this.optionNum,  this.meaning);
        }

        public boolean containsChoiceNum(int choiceNum){
            return MainMenuOption.READ_FROM_XML_FILE.optionNum == choiceNum || MainMenuOption.SHOW_STORE_DETAILS.optionNum == choiceNum
                    || MainMenuOption.SHOW_SYSTEM_ITEM_DETAILS.optionNum == choiceNum || MainMenuOption.ORDER_AND_PURCHASE.optionNum == choiceNum
                    || MainMenuOption.SHOW_ORDERS_HISTORY.optionNum == choiceNum || MainMenuOption.EXIT.optionNum == choiceNum;
        }

        public boolean numberEqualsToOptionNum(int numToCompare){
            return numToCompare == optionNum;
        }

        public String getMeaning() {
            return meaning;
        }

        public int getOptionNum() {
            return optionNum;
        }
    }

    public void printMainMenu(){
        MainMenuOption.READ_FROM_XML_FILE.printOption();
        MainMenuOption.SHOW_STORE_DETAILS.printOption();
        MainMenuOption.SHOW_SYSTEM_ITEM_DETAILS.printOption();
        MainMenuOption.ORDER_AND_PURCHASE.printOption();
        MainMenuOption.SHOW_ORDERS_HISTORY.printOption();
        MainMenuOption.EXIT.printOption();
    }

    public void chooseOptionFromMainMenuUntilExit(){
        boolean isAskingToQuite = false;

        do{
            printHeadline();
            printMainMenu();
            int chosenOption = getOptionInput(MainMenuOption.MAIN_MENU_ENUM_BEHAVIOUR);
            if(MainMenuOption.MAIN_MENU_ENUM_BEHAVIOUR.containsChoiceNum(chosenOption)){
                try {
                    chooseOptionsFromMenu(chosenOption);
                } catch (ExitException e) {
                    isAskingToQuite = true;
                }
            }
            else {
                System.out.println("There is no such an option, pleas try again");
            }
        }while(!isAskingToQuite);
    }

    @Override
    int getOptionInput(IOptionWiseFunctional polymorphicBasicEnumOption) {
        return super.getOptionInput(MainMenuOption.MAIN_MENU_ENUM_BEHAVIOUR);
    }

    public void chooseOptionsFromMenu(int chosenOption) throws ExitException {
        String xmlFailureToLoadFileNotice = "Sorry, an xml file wasn't loaded yet",
                chooseLoadingOptionNotice = "\n" + "choose option " + MainMenuOption.READ_FROM_XML_FILE.getOptionNum() + " to load an xml file to Super Duper Market";
        switch(convertOptionNumToEnum(chosenOption)){
            case READ_FROM_XML_FILE:
                SDMSystem newSystem = new SDMSystem();
                DetailsPrinter newDetailsPrinter = new DetailsPrinter(newSystem.getStorageMapper());
                boolean isLoadingXmlWentSuccessfully= menuOptionsForReadingXMLFile.readFromXmlFile(newSystem),
                        areThereNoneRelatedToStoresItems = newSystem.getStorageMapper().areThereSystemItemsNotBeingSelledInStores(),
                        areThereEmptyStores = newSystem.getStorageMapper().areThereStoresWithNoAvailableItems();

                if(isLoadingXmlWentSuccessfully){
                    if(!areThereNoneRelatedToStoresItems && !areThereEmptyStores){
                        SdmSystem = newSystem;
                        detailsPrinter = newDetailsPrinter;
                        orderMenu = new StaticOrderMenu(SdmSystem.getStorageMapper(), detailsPrinter);
                        System.out.println("Xml file loaded successfully to Super Duper Market :)\n");
                        isLoadXmlProcessWentSuccessfully = true;
                    }
                    else if(areThereNoneRelatedToStoresItems) {
                        isLoadingXmlWentSuccessfully = false;
                        System.out.println("Invalid xml file, every item on system should be sold at least at one store." + chooseLoadingOptionNotice);
                    }
                    else {
                        isLoadingXmlWentSuccessfully = false;
                        System.out.println("Invalid xml file, every store should offer at least one item for sell." + chooseLoadingOptionNotice);
                    }
                }
                else {
                    isLoadingXmlWentSuccessfully = false;
                System.out.println("loading process failed." + chooseLoadingOptionNotice);
                }
                break;
            case SHOW_STORE_DETAILS:
                if (isLoadXmlProcessWentSuccessfully) {
                    detailsPrinter.showStoresDetailsFilteredByParameters(true, true);
                } else {
                    System.out.println(xmlFailureToLoadFileNotice + chooseLoadingOptionNotice);
                }
                break;
            case SHOW_SYSTEM_ITEM_DETAILS:
                if (isLoadXmlProcessWentSuccessfully) {
                    detailsPrinter.showItemsInSystem();
                } else {
                    System.out.println(xmlFailureToLoadFileNotice + chooseLoadingOptionNotice);
                }
                break;
            case ORDER_AND_PURCHASE:
                if (isLoadXmlProcessWentSuccessfully) {
                    //menuOptionsForOrderAndPurchase.orderAndPurchase();
                    StaticOrderMenu staticOrderMenu = new StaticOrderMenu(SdmSystem.getStorageMapper(),detailsPrinter);
                    staticOrderMenu.makeStaticOrder();
                } else {
                    System.out.println(xmlFailureToLoadFileNotice + chooseLoadingOptionNotice);
                }
                break;
            case SHOW_ORDERS_HISTORY:
                if (isLoadXmlProcessWentSuccessfully) {
                    if(SdmSystem.getStorageMapper().areThereOrdersInSystem()){
                        detailsPrinter.showOrderHistoryOfAllStoresInSystem();
                    }
                    else{
                        System.out.println("No Orders yet, please choose another option");
                    }
                } else {
                    System.out.println(xmlFailureToLoadFileNotice + chooseLoadingOptionNotice);
                }
                break;
            case EXIT:
                throw new ExitException("Exit option chosen", "pressed Exit option");
                // TODO IF I HAVE ENOUGH TIME ADDING IT - i don't !
//            case UPDATE_ITEM_IN_STORE:
//                if(loadXmlSuccessfully == true)
//                {
//                    UpdatingProductsOfStoreMenu updatingProductsOfStoreMenu = new UpdatingProductsOfStoreMenu(base);
//                    updatingProductsOfStoreMenu.updatingProductsOfStore();
//                }
//                else
//                {
//                    System.out.println("Can't show orders history of Super Duper Market because an xml file wasn't loaded.\n Please choose option " +
//                            mainMenuOptions.READ_FROM_XML_FILE.getOptionNum() +" to load an xml file to Super Duper Market");
//                }
//                break;
        }
    }

    private MainMenuOption convertOptionNumToEnum(int chosenOptionNum) {
        for(MainMenuOption option : MainMenuOption.values())
        {
            if(option.getOptionNum() == chosenOptionNum)
            {
                return option;
            }
        }

        return null;
    }

}
