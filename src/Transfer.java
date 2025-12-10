import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Transfer extends Transaction {

    private String sender_account_id;
    private String recciever_id;

    public Transfer(int transaction_id, double amount, LocalDateTime date, String type, Double Balance_after, String sender_account_id, String recciever_id) {
        super(transaction_id, amount, date, type, Balance_after);
        this.sender_account_id = sender_account_id;
        this.recciever_id = recciever_id;

    }

    @Override
    public String toString() {
        return getTransaction_id()+","+getAmount()+","+getDate()+","+getType()+","+sender_account_id+","+recciever_id+","+getBalance_after();
    }


    public static  void TransferMoney(Account account, double amount, String recciever_id) {

        Account recciever_account =Account.getAccount(recciever_id);
        if (recciever_account==null){
            Scanner kb = new Scanner(System.in);
            System.out.println(" receiver account is not valid");
            String stop = kb.nextLine();

        }else {


            int id ;
            String userRole =User.getUser(String.valueOf(account.getUser_id())).split(",")[6];
            if (userRole.equals("C")){
                id = HomePage.generateId("Customer-"+account.getAccount_number()+".txt");
            }else {
                id = HomePage.generateId("Banker-"+account.getAccount_number()+".txt");
            }
            account.setBalance(account.getBalance() - amount);
            if (account.getBalance() < 0) {
                account.setBalance(account.getBalance() - 35);
                account.setOver_draft_count(account.getOver_draft_count() + 1);
                if (account.getOver_draft_count() == 2) {
                    account.setStatus("locked");
                }
            }
            Transfer Sender = new Transfer(id, amount, LocalDateTime.now(), "Transfer", (account.getBalance()), account.getAccount_number(), recciever_id);

            try {

                BufferedWriter Writer;

                if (userRole.equals("C")){
                    Writer = new BufferedWriter(new FileWriter("Customer-" + account.getAccount_number() + ".txt", true));
                }else {
                    Writer = new BufferedWriter(new FileWriter("Banker-" + account.getAccount_number() + ".txt", true));
                }

                Writer.write("\n" + Sender.toString());
                Writer.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            recciever_account.setBalance(recciever_account.getBalance() + amount);
            if (recciever_account.getBalance() >= 0) {
                recciever_account.setStatus("active");
                recciever_account.setOver_draft_count(0);
            }
            String Receiver_userRole =User.getUser(String.valueOf(recciever_account.getUser_id())).split(",")[6];
            int id_receiver;

            if (Receiver_userRole.equals("C")){
                id_receiver = HomePage.generateId("Customer-" + recciever_account.getAccount_number() + ".txt");
            }else {
                id_receiver = HomePage.generateId("Customer-" + recciever_account.getAccount_number() + ".txt");
            }

            Transfer Receiver = new Transfer(id_receiver, amount, LocalDateTime.now(), "Transfer", recciever_account.getBalance(), account.getAccount_number(), recciever_id);
            try {
                BufferedWriter Writer;
                if (userRole.equals("C")){
                     Writer = new BufferedWriter(new FileWriter("Customer-" + recciever_account.getAccount_number() + ".txt", true));
                }else {
                    Writer = new BufferedWriter(new FileWriter("Banker-" + recciever_account.getAccount_number() + ".txt", true));
                }

                Writer.write("\n" + Receiver.toString());
                Writer.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            int acc1 = FileUpdate.findLineByAccountId("Accounts.txt", Integer.parseInt(account.getAccount_number()));
            int acc2 = FileUpdate.findLineByAccountId("Accounts.txt", Integer.parseInt(recciever_id));


            FileUpdate.updateLine("Accounts.txt", acc1, account.getAccount_number() + "," + account.getUser_id() + "," + account.getBalance() + "," + account.getStatus() + "," + account.getAccount_type() + "," + account.getOver_draft_count()+","+account.getCardType());
            FileUpdate.updateLine("Accounts.txt", acc2, recciever_account.getAccount_number() + "," + recciever_account.getUser_id() + "," + recciever_account.getBalance() + "," + recciever_account.getStatus() + "," + recciever_account.getAccount_type() + "," + recciever_account.getOver_draft_count()+","+recciever_account.getCardType());
            System.out.println("Transfer Done you Balance: " + account.getBalance());
            Scanner kb = new Scanner(System.in);
            String input = kb.nextLine();
        }
    }
    public static double CalcAccountTransfer(String account_id, List<Account> own_account){
        double total=0;
        String line ;
        try {
            BufferedReader Reader;
            int id =Account.getAccount(account_id).getUser_id();
            String userRole =User.getUser(String.valueOf(id)).split(",")[6];
            if (userRole.equals("C")){
                Reader = new BufferedReader(new FileReader("Customer-"+account_id+".txt"));
            }else {
                Reader = new BufferedReader(new FileReader("Banker-"+account_id+".txt"));
            }

            while ((line = Reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                LocalDate today = LocalDate.now();
                String[] parts = line.split(",");

                boolean isTransfer = parts[3].equals("Transfer");

                boolean isSameAccount = parts[4].equals(String.valueOf(account_id));
                boolean OwnAccount =false;
                for (Account a :own_account){
                     OwnAccount = parts[5].equals(a.getAccount_number());
                }

                LocalDate transactionDate = LocalDateTime.parse(parts[2]).toLocalDate();
                boolean isToday = transactionDate.equals(today);

                if (isTransfer && isSameAccount && isToday&&!OwnAccount) {
                    total += Double.parseDouble(parts[1]);
                }
            }

            Reader.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return total;
    }
    public static double CalcAccountTransferOwnAccount(String account_id, List<Account> own_account){
        double total=0;
        String line ;

        try {
            BufferedReader Reader;
            int id =Account.getAccount(account_id).getUser_id();
            String userRole =User.getUser(String.valueOf(id)).split(",")[6];
            if (userRole.equals("C")){
                Reader = new BufferedReader(new FileReader("Customer-"+account_id+".txt"));
            }else {
                Reader = new BufferedReader(new FileReader("Banker-"+account_id+".txt"));
            }

            while ((line = Reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String target=null;
               for (Account a :own_account){
                   if (!a.getAccount_number().equals(account_id)){
                       target=a.getAccount_number();
                   }
               }
                String[] parts = line.split(",");

                boolean isTransfer = parts[3].equals("Transfer");
                boolean isSameTarget = parts[5].equals(String.valueOf(target));

                LocalDate transactionDate = LocalDateTime.parse(parts[2]).toLocalDate();
                boolean isToday = transactionDate.equals(LocalDate.now());

                if (isTransfer && isSameTarget && isToday) {
                    total += Double.parseDouble(parts[1]);
                }


            }

            Reader.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return total;
    }


    public String getSender_account_id() {
        return sender_account_id;
    }

    public void setSender_account_id(String sender_account_id) {
        this.sender_account_id = sender_account_id;
    }

    public String getRecciever_id() {
        return recciever_id;
    }

    public void setRecciever_id(String recciever_id) {
        this.recciever_id = recciever_id;
    }
}
