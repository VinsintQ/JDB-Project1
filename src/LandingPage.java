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
            System.out.println("1 - View My Accounts");
            System.out.println("2 - View My Transactions");
            System.out.println("3 - Create Saving Account");
            System.out.println("4 - Withdraw");
            System.out.println("5 - Deposit");
            System.out.println("6 - Transfer");
            System.out.println("7 - Log out ");
            input= kb.nextLine();

            if (input.equals("1")){

                List<Account> my_account= Account.getAccounts(c1.getId());
                for (Account acc :my_account){
                    System.out.println(acc.toString());
                }

                System.out.println("enter  to go back :-");
                input = kb.nextLine();


            }else if (input.equals("3")){
                Account.CreateSavingAccount(c1.getId());
            }else if (input.equals("4")){

                Withdraw.WithDrawmoney(c1.getId());
            }

        }while (!input.equals("7"));
        System.exit(0);
    }


}
