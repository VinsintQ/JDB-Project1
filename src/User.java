public class User {

    private String user_Name;
    private String first_Name;
    private String last_Name;
    private String hashedPassword;
    private char user_Role;
    private int user_id ;
    private double balance ;






    public User(String user_Name, String first_Name, String last_Name, String hashedPassword, double balance, int id, char user_Role) {
        this.user_Name = user_Name;
        this.first_Name = first_Name;
        this.last_Name = last_Name;
        this.hashedPassword = hashedPassword;

        this.balance = balance;
        this.user_id = id;
        this.user_Role = user_Role;
    }


    public String getUser_Name() {
        return user_Name;
    }

    public void setUser_Name(String user_Name) {
        this.user_Name = user_Name;
    }

    public String getFirst_Name() {
        return first_Name;
    }

    public void setFirst_Name(String first_Name) {
        this.first_Name = first_Name;
    }

    public String getLast_Name() {
        return last_Name;
    }

    public void setLast_Name(String last_Name) {
        this.last_Name = last_Name;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public char getUser_Role() {
        return user_Role;
    }

    public void setUser_Role(char user_Role) {
        this.user_Role = user_Role;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getId() {
        return user_id;
    }

    public void setId(int id) {
        user_id = id;
    }
}
