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


            }else if (input.equals("2")){
                Transaction.ShowAllTransaction(c1.getId());
            }
            else if (input.equals("3")){
                Account.CreateSavingAccount(c1.getId());
            }else if (input.equals("4")){

                Withdraw.WithDrawmoney(c1.getId());
            }else if (input.equals("5")){
                for (Account acc :useraccounts){
                    System.out.println(acc.toString());
                }
                Boolean hasAccount = false;

                do {

                        System.out.println("to Which account you want to deposit(enter account Id)");
                        input = kb.nextLine();

                    for (Account acc : useraccounts) {
                        if (input.equals(acc.getAccount_number())) {
                            System.out.println("enter amount :");
                            String amount = kb.nextLine();
                            Deposit.Depositmoney(acc, Double.parseDouble(amount));
                            hasAccount = true;
                        }
                    }
                }
                while(!hasAccount);



            }else if (input.equals("6")){
                for (Account acc :useraccounts){
                    System.out.println(acc.toString());
                }
                Boolean hasAccount = false;
                System.out.println("enter account number you want to transfer from :");
                input = kb.nextLine();
                do {


                    for (Account acc : useraccounts) {

                        if (input.equals(acc.getAccount_number())&&acc.getStatus().equals("Active")) {
                            System.out.println("enter amount :");
                            String amount = kb.nextLine();
                            if (acc.getBalance()>=0||(acc.getBalance()<0 &&Double.parseDouble(amount)<=100)){
                                System.out.println("enter receiver Account number :");
                                input = kb.nextLine();
                                Transfer.TransferMoney(acc,Double.parseDouble(amount),input);
                            }else if (acc.getBalance()<0 &&Double.parseDouble(amount) >100){
                                do {
                                    System.out.println("u cant Transfer more than 100, enter amount again :");
                                    amount = kb.nextLine();
                                    if (Double.parseDouble(amount)<101) {
                                        System.out.println("enter receiver Account number :");
                                        input = kb.nextLine();
                                        Transfer.TransferMoney(acc, Double.parseDouble(amount), input);
                                        break;
                                    }
                                }while (Double.parseDouble(amount)>100);

                            }


                            hasAccount = true;
                            break;
                        }
                    }
                    if (!hasAccount) {
                        System.out.println("enter again ,either your account locked or you entered wrong number :");
                        input = kb.nextLine();
                    }

                }
                while(!hasAccount);



            }

        }while (!input.equals("7"));
        System.exit(0);
    }


}
