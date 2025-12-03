import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LandingPage {

    private Customer c1;

    public LandingPage(Customer c1) {
        this.c1 = c1;
    }

    public void viewSeting(){
        Scanner kb = new Scanner(System.in);
        String input;



        do {
            List<Account> useraccounts = Account.getAccounts(c1.getId());
            System.out.println("1 - View My Accounts");
            System.out.println("2 - View My Transactions");
            System.out.println("3 - Create Saving Account");
            System.out.println("4 - Withdraw");
            System.out.println("5 - Deposit");
            System.out.println("6 - Transfer");
            System.out.println("7 - Log out ");
            input= kb.nextLine();

            if (input.equals("1")){


                for (Account acc :useraccounts){
                    System.out.println(acc.toString());
                }

                System.out.println("enter  to go back :-");
                input = kb.nextLine();


            }else if (input.equals("3")){
                Account.CreateSavingAccount(c1.getId());
            }else if (input.equals("4")){

                Withdraw.WithDrawmoney(c1.getId());
            }else if (input.equals("5")){
                for (Account acc :useraccounts){
                    System.out.println(acc.toString());
                }
                Boolean hasAccount = false;

                do {
                    System.out.println("to Which account you want to deposit(CHK | SAV)");
                    input = kb.nextLine();

                    for (Account acc : useraccounts) {
                        if (input.equals(acc.getAccount_type())) {
                            System.out.println("enter amount :");
                            String amount = kb.nextLine();
                            Deposit.Depositmoney(acc, Double.parseDouble(amount));
                            hasAccount = true;
                        }
                    }
                }
                while(!hasAccount);



            }

        }while (!input.equals("7"));
        System.exit(0);
    }


}
