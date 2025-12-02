import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public  class Account {

private String account_number ;
private int user_id ;
private double balance;
private String status;
private String account_type;

    public Account(){

    }

    public Account(String account_number, int user_id, double balance, String status, String account_type) {
        this.account_number = account_number;
        this.user_id = user_id;
        this.balance = balance;
        this.status = status;
        this.account_type = account_type;
    }

    public List<Account> getAccounts(int user_id){
        List<Account> useraccounts =new ArrayList<>();
        String line ;
        Account a1 =null;
        try {
            BufferedReader Reader = new BufferedReader(new FileReader("Accounts.txt"));
            while ((line = Reader.readLine()) != null) {

                if(Integer.parseInt(line.split(",")[1])==user_id) {

                    String acc_deatails[]=line.split(",");
                     a1 = new Account(acc_deatails[0],Integer.parseInt(acc_deatails[1]),Double.parseDouble(acc_deatails[2]),acc_deatails[3],acc_deatails[4]);
                     useraccounts.add(a1);
                }
            }

            Reader.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return useraccounts;
    }


    @Override
    public String toString() {
        return account_number +" : "+balance +" : "+status+" : "+account_type;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }
}
