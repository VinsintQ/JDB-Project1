
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Withdraw extends Transaction {

    private String account_number;

    public Withdraw(int transaction_id, double amount, LocalDateTime date, String type, String account_number, double Balance_after) {
        super(transaction_id, amount, date, type, Balance_after);
        this.account_number = account_number;

    }

    public String getAccount_number() {
        return account_number;
    }

    public static double CalcAccountWithdraw(String account_id){
        double total=0;
        String line ;
        try {
            BufferedReader Reader = new BufferedReader(new FileReader("Customer-"+account_id+".txt"));
                        while ((line = Reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                LocalDate today = LocalDate.now();

                if (line.split(",")[3].equals("Withdraw")) {
                    LocalDate transactionDate = LocalDateTime.parse(line.split(",")[2]).toLocalDate();

                    if (transactionDate.equals(today)) {
                        total += Double.parseDouble(line.split(",")[1]);
                    }
                }
            }

            Reader.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return total;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public static DebitCard createCard(String type) {
        switch (type) {
            case "MASTERCARD":
                return new MasterCard();
            case "TITANIUM":
                return new MastercardTitanium();
            default:
                return new MastercardPlatinum();
        }
    }

    @Override
    public String toString() {
        return getTransaction_id() + "," + getAmount() + "," + getDate() + "," + getType() + "," + getAccount_number();
    }

    public static boolean CheckLocked(Account account) {

        if (account.getStatus().equals("locked")) {
            return true;
        }
        return false;


    }

    public static void WithDrawmoney(int user_id) {
        String input;
        Scanner kb = new Scanner(System.in);
        List<Account> accounts = Account.getAccounts(user_id);
            System.out.println("Choose account to withdraw (enter account number)");
            for (Account a : accounts) {
                System.out.println(a.toString());
            }
            input = kb.nextLine();
           if (input.isEmpty()){
               return;
           }
            for (Account a : accounts) {

                if (input.toUpperCase().equals(a.getAccount_number())) {

                    if (CheckLocked(a)) {
                        System.out.println("your account is locked pay the amount then it will be active......");
                        input = kb.nextLine();
                    } else {
                        double withdrawlimit = createCard(a.getCardType()).WithdrawLimitPerDay();
                        double totalwithrawed=   CalcAccountWithdraw(a.getAccount_number());
                        System.out.println("Enter amount : your limit ("+(withdrawlimit-totalwithrawed)+")");
                        input = kb.nextLine();
                        if (input.isEmpty()){

                            WithDrawmoney(user_id);
                            return;
                        }

                        while (Double.parseDouble(input)>(withdrawlimit-totalwithrawed)){
                            System.out.println("you exceeds the limit ,remaining limit :"+(withdrawlimit-totalwithrawed));
                            System.out.println("Enter amount : ,or press enter to go back");
                            input = kb.nextLine();
                            if (input.isEmpty()){

                                WithDrawmoney(user_id);
                                return;
                            }
                        }

                        if (Double.parseDouble(input) > 0) {
                            double balance = a.getBalance();
                            while (a.getOver_draft_count() == 1 && Integer.parseInt(input) > 100) {
                                System.out.println("u cant withdraw more than 100 , this is your second overdraft");
                                input = kb.nextLine();
                            }
                            balance = balance - Double.parseDouble(input);
                            if (balance < 0) {
                                a.setOver_draft_count(a.getOver_draft_count() + 1);
                                balance = balance - 35;
                                if (a.getOver_draft_count() == 2) {
                                    a.setStatus("locked");
                                }
                            }

                            int id = HomePage.generateId("Customer-"+a.getAccount_number()+".txt");
                            Withdraw c1 = new Withdraw(id, Double.parseDouble(input), LocalDateTime.now(), "Withdraw", a.getAccount_number(), balance);
                            try {
                                BufferedWriter Writer = new BufferedWriter(new FileWriter("Customer-" + a.getAccount_number() + ".txt", true));
                                Writer.write("\n" + c1.toString() + "," + balance);
                                Writer.close();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }

                            int linetoUpdate = FileUpdate.findLineByAccountId("Accounts.txt", Integer.parseInt(a.getAccount_number()));
                            String updatedline = c1.account_number + "," + user_id + "," + balance + "," + a.getStatus() + "," + a.getAccount_type() + "," + a.getOver_draft_count()+","+a.getCardType();
                            FileUpdate.updateLine("Accounts.txt", linetoUpdate, updatedline);
                            System.out.println("Withdraw done new balance is " + balance + "  enter to go back ....");
                            input = kb.nextLine();
                        }
                    }
                }


            }


    }



    }

