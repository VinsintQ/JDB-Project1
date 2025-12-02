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
            System.out.println("3 - Log out ");
            input= kb.nextLine();

            if (input.equals("1")){
                System.out.println("1");
//                viewAccounts(c1.getUser_Name());
                Account a1 = new Account();

                List<Account> my_account= a1.getAccounts(c1.getId());
                System.out.println(my_account.toString());
                System.out.println("enter  to go back :-");
                input = kb.nextLine();


            }
        }while (!input.equals("3"));
        System.exit(0);
    }

    public void viewAccounts(String user_name){


    }
}
