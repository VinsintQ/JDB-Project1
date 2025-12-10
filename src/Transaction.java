import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Transaction {


     private int transaction_id;
     private double amount ;
     private LocalDateTime date;
     private String type;
     private double Balance_after;

    public Transaction(int transaction_id, double amount, LocalDateTime date, String type,Double Balance_after) {
        this.transaction_id = transaction_id;
        this.amount = amount;
        this.date = date;
        this.type = type;
        this.Balance_after =Balance_after;
    }

    public double getBalance_after() {
        return Balance_after;
    }

    public void setBalance_after(double balance_after) {
        Balance_after = balance_after;
    }

    public static List<String>  ShowAllTransaction(int account_id){
        String line;
        List<String> Transactions= new ArrayList<>();
        try {

            BufferedReader Reader;
            int id =Account.getAccount(String.valueOf(account_id)).getUser_id();
            String userRole =User.getUser(String.valueOf(id)).split(",")[6];
            if (userRole.equals("C")){
                Reader = new BufferedReader(new FileReader("Customer-"+account_id+".txt"));
            }else {
                Reader = new BufferedReader(new FileReader("Banker-"+account_id+".txt"));
            }

            while ((line = Reader.readLine()) != null) {
                if (line.isEmpty()){
                    continue;
                }
                Transactions.add(line);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
       return Transactions;
    }
    public  static  void TodayTransactions(int account_id){

        List<String> today = ShowAllTransaction(account_id)
                .stream()
                .filter(t -> t.split(",")[2].startsWith(LocalDate.now().toString()))
                .toList();



              today.forEach(t-> System.out.println(t));

    }
    public  static  void yesterdayTransactions(int account_id){

        List<String> yesterday = ShowAllTransaction(account_id)
                .stream()
                .filter(t -> t.split(",")[2].startsWith(LocalDate.now().minusDays(1).toString()))
                .toList();



         yesterday.forEach(t-> System.out.println(t));

    }


    public static void ShowTransactionMenu(int account_id){
        Scanner kb = new Scanner(System.in);
        String input;
        System.out.println("1 -today Transaction");
        System.out.println("2 -yesterday Transaction");
        System.out.println("3 -last week Transaction");
        System.out.println("4 -last 30 days Transaction");
        input = kb.nextLine();
        switch (input){
            case "1":
                TodayTransactions(account_id);
                System.out.println("Press Enter to continue...");
                try {
                    System.in.read();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "2":
                yesterdayTransactions(account_id);
                System.out.println("Press Enter to continue...");
                try {
                    System.in.read();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "3":
                lastWeekTransactions(account_id);
                System.out.println("Press Enter to continue...");
                try {
                    System.in.read();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "4":
                last30days(account_id);
                System.out.println("Press Enter to continue...");
                try {
                    System.in.read();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
        }

    }
    public static void lastWeekTransactions(int account_id) {

        LocalDateTime oneWeekAgo = LocalDateTime.now().minusDays(7);

         ShowAllTransaction(account_id)
                .stream()
                .filter(t -> {
                    String[] parts = t.split(",");
                    LocalDateTime dateTime = LocalDateTime.parse(parts[2]);
                    return dateTime.isAfter(oneWeekAgo);
                })
                .collect(Collectors.toList()).forEach(s -> System.out.println(s));
    }

    public static void last30days(int account_id) {

        LocalDateTime oneWeekAgo = LocalDateTime.now().minusDays(30);

        ShowAllTransaction(account_id)
                .stream()
                .filter(t -> {
                    String[] parts = t.split(",");
                    LocalDateTime dateTime = LocalDateTime.parse(parts[2]);
                    return dateTime.isAfter(oneWeekAgo);
                })
                .collect(Collectors.toList()).forEach(s -> System.out.println(s));
    }



    public int getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
