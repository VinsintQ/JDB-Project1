import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Withdraw extends Transaction {

    private String account_number;

    public Withdraw(int transaction_id, double amount, LocalDateTime date, String type,String account_number) {
        super(transaction_id, amount, date, type);
        this.account_number =account_number;

    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    @Override
    public String toString() {
        return getTransaction_id()+","+getAmount()+","+getDate()+","+getType()+","+getAccount_number();
    }

    public static double WithDrawmoney(int user_id){
        String input;
        Scanner kb = new Scanner(System.in);
       List<Account> accounts= Account.getAccounts(user_id);
       if (accounts.size()==1){
           System.out.println("Enter amount :");
           input = kb.nextLine();
           if (Double.parseDouble(input)>0){
               double balance = accounts.get(0).getBalance();
               Withdraw c1 = new Withdraw(1001,Double.parseDouble(input),LocalDateTime.now(),"Withdraw",accounts.get(0).getAccount_number());
               try {
                   BufferedWriter Writer = new BufferedWriter(new FileWriter("Customer-"+user_id+".txt",true));
                   Writer.write(c1.toString());
                   Writer.close();
               } catch (Exception e) {
                   throw new RuntimeException(e);
               }
              balance=balance-Double.parseDouble(input);
               System.out.println("Withdraw done new balance is "+balance+"  enter to go back ....");
               input= kb.nextLine();
           }

       }
        System.out.println("Choose account to withdraw");
        for (Account a :accounts){
            System.out.println(a.toString());
        }

         return 123;

    }
}
