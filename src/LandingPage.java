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
            System.out.println("6 - Transfer to own account");
            System.out.println("7 - Transfer");
            System.out.println("8 - Account Statement");
            System.out.println("9 - Exit ");
            input= kb.nextLine();

            if (input.equals("1")){


                for (Account acc :useraccounts){
                    System.out.println(acc.toString());
                }

                System.out.println("enter  to go back :-");
                input = kb.nextLine();


            }else if (input.equals("2")){
                Scanner keyboard = new Scanner(System.in);
                 for (Account a:useraccounts){
                     System.out.println(a.toString());
                 }
                System.out.println("Enter account number:");
                 String i = kb.nextLine();
                for (Account a:useraccounts){
                    if (a.getAccount_number().equals(i)){
                        Transaction.ShowTransactionMenu(Integer.parseInt(i));
                    }
                }
                
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
                            double limit = Withdraw.createCard(acc.getCardType()).DepositLimitPerDay();
                            double totalDeposit=   Deposit.CalcAccountDeposit(acc.getAccount_number());

                            double remlimit =(limit-totalDeposit);
                            System.out.println("enter amount :(your limit "+remlimit+")");
                            String amount = kb.nextLine();
                            if (amount.isEmpty()){
                                viewSeting();
                            }



                            while (Double.parseDouble(amount)>remlimit){
                                System.out.println("you exceeds the limit , remaining limit :"+remlimit);
                                System.out.println("Enter amount : ,or press enter to go back");
                                amount = kb.nextLine();
                                if (amount.isEmpty()){
                                  viewSeting();
                                }
                            }

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
                        if (input.equals(acc.getAccount_number())&&acc.getStatus().equals("active")) {
                            double Transferlimit =Withdraw.createCard(acc.getCardType()).TransferLimitPerDayOwnAccount();
                            double totalTransfered= Transfer.CalcAccountTransferOwnAccount(acc.getAccount_number(),useraccounts);
                            System.out.println("enter amount : remaining limit ("+(Transferlimit-totalTransfered)+")");
                            String amount = kb.nextLine();
                            if (amount.isEmpty()){
                                viewSeting();
                                return;
                            }
                            while (Double.parseDouble(amount)>(Transferlimit-totalTransfered)){
                                System.out.println("you exceeds the limit ,remaining limit :"+(Transferlimit-totalTransfered));
                                System.out.println("Enter amount : , or press enter to go back");
                                amount = kb.nextLine();
                                if (amount.isEmpty()){
                                    viewSeting();
                                    return;
                                }
                            }
                            if (acc.getBalance()>=0||(acc.getBalance()<0 &&Double.parseDouble(amount)<=100)){
                                System.out.println("enter receiver Account number :");
                                input = kb.nextLine();
                                boolean match = false;
                                if (input.isEmpty()){
                                    viewSeting();
                                    break;
                                }
                                for(Account a:useraccounts){
                                    if (input.equals(a.getAccount_number())){
                                        match=true;
                                    }
                                }
                                while (!match){
                                    System.out.println("this is not your account,enter receiver Account number again :");
                                    input = kb.nextLine();
                                    for(Account a:useraccounts){
                                        if (input.equals(a.getAccount_number())){
                                            match=true;
                                        }
                                    }
                                }
                                Transfer.TransferMoney(acc,Double.parseDouble(amount),input);
                            }else if (acc.getBalance()<0 &&Double.parseDouble(amount) >100){
                                do {
                                    System.out.println("u cant Transfer more than 100, enter amount again :");
                                    amount = kb.nextLine();
                                    if (Double.parseDouble(amount)<101) {
                                        System.out.println("enter receiver Account number :");
                                        input = kb.nextLine();
                                        boolean match = false;
                                        for(Account a:useraccounts){
                                            if (input.equals(a.getAccount_number())){
                                                match=true;
                                            }
                                        }
                                        while (!match){
                                            System.out.println("this is not your account,enter receiver Account number again :");
                                            input = kb.nextLine();

                                            for(Account a:useraccounts){
                                                if (input.equals(a.getAccount_number())){
                                                    match=true;
                                                }
                                            }
                                        }



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
            else if (input.equals("7")){
                for (Account acc :useraccounts){
                    System.out.println(acc.toString());
                }
                Boolean hasAccount = false;
                System.out.println("enter account number you want to transfer from :");
                input = kb.nextLine();
                if (input.isEmpty()){
                    viewSeting();
                    break;
                }
                do {
                    for (Account acc : useraccounts) {

                        if (input.equals(acc.getAccount_number())&&acc.getStatus().equals("active")) {
                            double Transferlimit =Withdraw.createCard(acc.getCardType()).TransferLimitPerDay();
                            double totalTransfered= Transfer.CalcAccountTransfer(acc.getAccount_number(),useraccounts);
                            System.out.println("enter amount : remaining limit ("+(Transferlimit-totalTransfered)+")");
                            String amount = kb.nextLine();
                            if (amount.isEmpty()){
                                viewSeting();
                                return;
                            }
                            while (Double.parseDouble(amount)>(Transferlimit-totalTransfered)){
                                System.out.println("you exceeds the limit ,remaining limit :"+(Transferlimit-totalTransfered));
                                System.out.println("Enter amount : ,or press enter to go back");
                                amount = kb.nextLine();
                                if (amount.isEmpty()){
                                    viewSeting();
                                    return;

                                }
                            }
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



            }else if(input.equals("8")){

                for (Account acc :useraccounts){
                    System.out.println(acc.toString());
                }
                Boolean hasAccount = false;
                System.out.println("enter account number you want to show for :");
                Scanner keyboard = new Scanner(System.in);
                String in = keyboard.nextLine();
                for (Account acc :useraccounts){
                    if (acc.getAccount_number().equals(in)){
                        Account.AccountStatement(in);
                    };

                }



            }

        }while (!input.equals("9"));
        System.exit(0);
    }


}
