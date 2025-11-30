import java.time.LocalDateTime;

public class Transaction {


     private int transaction_id;
     private double amount ;
     private LocalDateTime date;
     private String type;


    public Transaction(int transaction_id, double amount, LocalDateTime date, String type) {
        this.transaction_id = transaction_id;
        this.amount = amount;
        this.date = date;
        this.type = type;
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
