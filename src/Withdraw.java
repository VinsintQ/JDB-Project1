import java.time.LocalDateTime;

public class Withdraw extends Transaction {

    private String account_number;

    public Withdraw(int transaction_id, double amount, LocalDateTime date, String type,String account_number) {
        super(transaction_id, amount, date, type);
        this.account_number =account_number;

    }

}
