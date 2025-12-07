import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public  class Account {

private String account_number ;
private int user_id ;
private double balance;
private String status;
private String account_type;
private int over_draft_count;
private String cardType;


    public static DebitCard createCard(int type) {
        switch (type) {
            case 1:
                return new MasterCard();
            case 2:
                return new MastercardTitanium();
            default:
                return new MastercardPlatinum();
        }
    }



    public int getOver_draft_count() {
        return over_draft_count;
    }

    public void setOver_draft_count(int over_draft_count) {
        this.over_draft_count = over_draft_count;
    }

    public Account(){

    }

    public Account(String account_number, int user_id, double balance, String status, String account_type,int over_draft_count,String cardType) {
        this.account_number = account_number;
        this.user_id = user_id;
        this.balance = balance;
        this.status = status;
        this.account_type = account_type;
        this.over_draft_count =over_draft_count;
        this.cardType=cardType;
    }



    public static DebitCard ChooseCard(){
        Scanner kb = new Scanner(System.in);
        String input;
        System.out.println("Choose card type :(1-MASTERCARD | 2-TITANIUM  | 3-PLATINUM)");
        input= kb.nextLine();
        if (input.equals("1")){
           return createCard(1);
        }else if (input.equals("2")){
          return   createCard(2);
        }else if (input.equals("3")){
          return   createCard(3);
        }
        System.out.println("valid input Enter Again ..");
       return null;

    }

    public static void CreateSavingAccount(int user_id){
        Scanner kb = new Scanner(System.in);
       List<Account> current_account = getAccounts(user_id);
        DebitCard card = ChooseCard();
       while (card==null){
          card= ChooseCard();
       }




       if (current_account.size()==1){
           try {
               int id = HomePage.generateId("Accounts.txt");
               FileWriter Writer = new FileWriter("Accounts.txt",true);
               Writer.write("\n"+id+","+user_id+",0,active,saving,0,"+card.getCardName());
               Writer.close();
           } catch (Exception e) {
               throw new RuntimeException(e);
           }
           System.out.println("created succesfuly,enter to go back ....");
           kb.nextLine();
       }else{
           System.out.println("you already have saving account, enter to go back .....");
           kb.nextLine();
       }
    }

    public static List<Account> getAccounts(int user_id){
        List<Account> useraccounts =new ArrayList<>();
        String line ;
        Account a1 =null;
        try {
            BufferedReader Reader = new BufferedReader(new FileReader("Accounts.txt"));
            while ((line = Reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                if(Integer.parseInt(line.split(",")[1])==user_id) {

                    String acc_deatails[]=line.split(",");
                     a1 = new Account(acc_deatails[0],Integer.parseInt(acc_deatails[1]),Double.parseDouble(acc_deatails[2]),acc_deatails[3],acc_deatails[4],Integer.parseInt(acc_deatails[5]),acc_deatails[6]);
                     useraccounts.add(a1);
                }
            }

            Reader.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return useraccounts;
    }


    public static Account getAccount(String account_number){
        String line;
        Account a1 =null;
        try {
            BufferedReader Reader = new BufferedReader(new FileReader("Accounts.txt"));
            while ((line = Reader.readLine()) != null) {


                if (line.trim().isEmpty()) {
                    continue;
                }

                if(line.split(",")[0].equals(account_number)) {

                    String acc_deatails[]=line.split(",");
                    a1 = new Account(acc_deatails[0],Integer.parseInt(acc_deatails[1]),Double.parseDouble(acc_deatails[2]),acc_deatails[3],acc_deatails[4],Integer.parseInt(acc_deatails[5]),acc_deatails[6]);

                }
            }

            Reader.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
       return a1;
    }

    @Override
    public String toString() {
        return "Account number : "+account_number +" , Blance : "+balance +" ("+status+") : "+account_type+" Account overdraft : "+over_draft_count+", card type :"+cardType;
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

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
}
