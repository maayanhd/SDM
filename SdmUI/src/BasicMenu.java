import java.util.Scanner;

public abstract class BasicMenu {

    int getOptionInput(IOptionWiseFunctional optionwiseBasicEnumOption){
        Scanner sc = new Scanner(System.in);
        boolean isProcessWentSuccessfully = false;
        int optionNum = 0;
        String inputStr;
        do{
            System.out.println("Please choose an option number:");
                inputStr = sc.next();

                if(tryParseInt(inputStr)){
                    optionNum = Integer.parseInt(inputStr);
                    if(optionwiseBasicEnumOption.containsChoiceNum(optionNum)) {
                        isProcessWentSuccessfully = true;
                    }
                    else{
                        System.out.println("no such an option, please try again");
                    }
                }
                else {
                    System.out.println("option must be a whole number, please try again ");
                }
        }while(!isProcessWentSuccessfully);

        return optionNum;
    }

    public boolean tryParseInt(String inputString) {
        try {
            Integer.parseInt(inputString);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public boolean tryParseDouble(String inputString) {
        try {
            Double.parseDouble(inputString);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
