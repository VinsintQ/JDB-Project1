import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
            int id = HomePage.generateId("Customer-" + account.getAccount_number()+ ".txt");
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
                BufferedWriter Writer = new BufferedWriter(new FileWriter("Customer-" + account.getAccount_number() + ".txt", true));
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
            int id_receiver = HomePage.generateId("Customer-" + recciever_account.getAccount_number() + ".txt");
            Transfer Receiver = new Transfer(id_receiver, amount, LocalDateTime.now(), "Transfer", recciever_account.getBalance(), account.getAccount_number(), recciever_id);
            try {
                BufferedWriter Writer = new BufferedWriter(new FileWriter("Customer-" + recciever_account.getAccount_number() + ".txt", true));
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
    public static double CalcAccountTransfer(String account_id){
        double total=0;
        String line ;
        try {
            BufferedReader Reader = new BufferedReader(new FileReader("Customer-"+account_id+".txt"));
            while ((line = Reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                if(line.split(",")[3].equals("Transfer")&&line.split(",")[4].equals(account_id)){
                    total+=Double.parseDouble(line.split(",")[1]);
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
