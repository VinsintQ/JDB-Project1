import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Withdraw extends Transaction {

    private String account_number;

    public Withdraw(int transaction_id, double amount, String date, String type, String account_number, double Balance_after) {
        super(transaction_id, amount, date, type, Balance_after);
        this.account_number = account_number;

    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
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
        if (accounts.size() == 1) {
            if (CheckLocked(accounts.get(0))) {
                System.out.println("your account is locked pay the amount then it will be active ....");
                input = kb.nextLine();
            } else {
                System.out.println("Enter amount :");
                input = kb.nextLine();
                if (Double.parseDouble(input) > 0) {
                    double balance = accounts.get(0).getBalance();
                    while (accounts.get(0).getOver_draft_count() == 1 && Integer.parseInt(input) > 100) {
                        System.out.println("u cant withdraw more than 100 , this is your second overdraft");
                        input = kb.nextLine();
                    }
                    balance = balance - Double.parseDouble(input);
                    if (balance < 0) {
                        accounts.get(0).setOver_draft_count(accounts.get(0).getOver_draft_count() + 1);
                        balance = balance - 35;
                        if (accounts.get(0).getOver_draft_count() == 2) {
                            accounts.get(0).setStatus("locked");
                        }
                    }
                    Withdraw c1 = new Withdraw(1001, Double.parseDouble(input), LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), "Withdraw", accounts.get(0).getAccount_number(), balance);
                    try {
                        BufferedWriter Writer = new BufferedWriter(new FileWriter("Customer-" + user_id + ".txt", true));
                        Writer.write("\n" + c1.toString() + "," + balance);
                        Writer.close();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    int linetoUpdate = FileUpdate.findLineByAccountId("Accounts.txt", Integer.parseInt(accounts.get(0).getAccount_number()));
                    String updatedline = c1.account_number + "," + user_id + "," + balance + "," + accounts.get(0).getStatus() + "," + accounts.get(0).getAccount_type() + "," + accounts.get(0).getOver_draft_count();
                    FileUpdate.updateLine("Accounts.txt", linetoUpdate, updatedline);
                    System.out.println("Withdraw done new balance is " + balance + "  enter to go back ....");
                    input = kb.nextLine();
                }
            }

        } else {
            System.out.println("Choose account to withdraw (enter account number)");
            for (Account a : accounts) {
                System.out.println(a.toString());
            }
            input = kb.nextLine();
            for (Account a : accounts) {

                if (input.toUpperCase().equals(a.getAccount_number())) {

                    if (CheckLocked(a)) {
                        System.out.println("your account is locked pay the amount then it will be active......");
                        input = kb.nextLine();
                    } else {

                        System.out.println("Enter amount :");
                        input = kb.nextLine();

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
                            Withdraw c1 = new Withdraw(1001, Double.parseDouble(input), LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), "Withdraw", a.getAccount_number(), balance);
                            try {
                                BufferedWriter Writer = new BufferedWriter(new FileWriter("Customer-" + user_id + ".txt", true));
                                Writer.write("\n" + c1.toString() + "," + balance);
                                Writer.close();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }

                            int linetoUpdate = FileUpdate.findLineByAccountId("Accounts.txt", Integer.parseInt(a.getAccount_number()));
                            String updatedline = c1.account_number + "," + user_id + "," + balance + "," + a.getStatus() + "," + a.getAccount_type() + "," + a.getOver_draft_count();
                            FileUpdate.updateLine("Accounts.txt", linetoUpdate, updatedline);
                            System.out.println("Withdraw done new balance is " + balance + "  enter to go back ....");
                            input = kb.nextLine();
                        }
                    }
                }


            }
        }
    }



    }

