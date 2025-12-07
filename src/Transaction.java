import java.io.BufferedReader;
import java.io.FileReader;
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
            BufferedReader Reader = new BufferedReader(new FileReader("Customer-"+account_id+".txt"));
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
    public  static  List<String> TodayTransactions(int account_id){

        List<String> today = ShowAllTransaction(account_id)
                .stream()
                .filter(t -> t.split(",")[2].startsWith(LocalDate.now().toString()))
                .toList();



             return today;

    }
    public  static  List<String> yesterdayTransactions(int account_id){

        List<String> yesterday = ShowAllTransaction(account_id)
                .stream()
                .filter(t -> t.split(",")[2].startsWith(LocalDate.now().minusDays(1).toString()))
                .toList();



        return yesterday;

    }

//    public  static  List<String> lastWeekTransactions(int account_id){
//
//        List<LocalDate> last_week = ShowAllTransaction(account_id).stream()
//                .filter(t->t.split(",")[2]).filter(t->t.);
//
//
//        return last_week;
//    }

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
