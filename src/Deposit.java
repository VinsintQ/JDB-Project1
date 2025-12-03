import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Deposit extends Transaction{

    private String Account_number;
    public Deposit(int transaction_id, double amount, LocalDateTime date, String type, Double Balance_after,String Account_number) {
        super(transaction_id, amount, date, type, Balance_after);
        this.Account_number = Account_number;
    }

    public String getAccount_number() {
        return Account_number;
    }

    public void setAccount_number(String account_number) {
        Account_number = account_number;
    }

    @Override
    public String toString() {
        return getTransaction_id()+","+getAmount()+","+getDate()+","+getType()+","+getAccount_number();
    }

    public static void Depositmoney(Account account , double amount){

      account.setBalance(account.getBalance()+amount);
      if (account.getBalance()>0){
          account.setStatus("active");
          account.setOver_draft_count(0);
      }
      int id = HomePage.generateId("Customer-"+account.getUser_id()+".txt");
      Deposit d1 = new Deposit(id,amount,LocalDateTime.now(),"Deposit",account.getBalance(),account.getAccount_number());
        try {
            BufferedWriter Writer = new BufferedWriter(new FileWriter("Customer-"+account.getUser_id()+".txt",true));
            Writer.write("\n" + d1.toString()+","+ account.getBalance());
            Writer.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        int linetoUpdate= FileUpdate.findLineByAccountId("Accounts.txt",Integer.parseInt(account.getAccount_number()));
        String updatedline = account.getAccount_number()+","+account.getUser_id()+","+account.getBalance()+","+account.getStatus()+","+account.getAccount_type()+","+account.getOver_draft_count();
        FileUpdate.updateLine("Accounts.txt",linetoUpdate, updatedline);
        System.out.println("Deposit done new balance is "+account.getBalance()+"  enter to go back ....");
        Scanner kb = new Scanner(System.in);
       String input= kb.nextLine();

    }
}
