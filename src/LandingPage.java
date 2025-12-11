import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class LandingPage {

    private User c1;

    public LandingPage(User c1) {
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
            System.out.println("7 - Account Statement");
            System.out.println("8 - Exit ");
            if (c1.getUser_Role()=='B'){
                System.out.println("10 - Turn Accounts Active");
            }


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
                            double totalDeposit=   Deposit.CalcAccountDeposit(acc.getAccount_number(),c1);

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



            }
            else if (input.equals("6")){
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



            }else if (input.equals("10")){
                if (c1.getUser_Role() == 'B') {
                    List<User> inactiveUsers = ShowNotActiveUser(c1);
                    inactiveUsers.forEach(user -> System.out.println(user.getId() + "  :  " + user.getUser_Name()));

                    Scanner scanner = new Scanner(System.in);
                    System.out.print("Enter user ID to activate: ");
                    int idToActivate = scanner.nextInt();
                    scanner.nextLine();

                    activateUser(idToActivate);
                }


            }else if(input.equals("7")){

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

        }while (!input.equals("8"));
        System.exit(0);
    }


    public static List<User> ShowNotActiveUser(User b1){
        List<User> notActiveUsers = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("Users.txt"));
            String line;
            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(",");



                int userid = Integer.parseInt(parts[0]);
                String userName = parts[1];
                String firstName = parts[2];
                String lastName = parts[3];
                String password = parts[4];
                boolean Active = Boolean.parseBoolean(parts[5]);
                char userRole = parts[6].charAt(0);

                if (!Active) {
                    User user;

                    user = new Customer(userid, userName, firstName, lastName, password, Active, userRole);

                    notActiveUsers.add(user);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return notActiveUsers;
    }



    public static void activateUser(int userId) {
        String inputFile = "Users.txt";
        List<String> lines = new ArrayList<>();


        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        boolean found = false;
        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(",");


            int id = Integer.parseInt(parts[0]);
            if (id == userId) {
                parts[5] = "true";
                lines.set(i, String.join(",", parts));
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("User ID " + userId + " not found.");
            return;
        }

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(inputFile, false));
            for (String l : lines) {
                writer.write(l);
                writer.newLine();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {

                }
            }
        }

        System.out.println("User ID " + userId + " activated.");
    }

}

