import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Transaction {


     private int transaction_id;
     private double amount ;
     private String date;
     private String type;
     private double Balance_after;

    public Transaction(int transaction_id, double amount, String date, String type,Double Balance_after) {
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

    public static void  ShowAllTransaction(int account_id){
        String line;
        try {
            BufferedReader Reader = new BufferedReader(new FileReader("Customer-"+account_id+".txt"));
            while ((line = Reader.readLine()) != null) {
                if (line.isEmpty()){
                    continue;
                }
                System.out.println(line);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Scanner kb = new Scanner(System.in);
        System.out.println("enter to go back ......");
        String next = kb.nextLine();
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
