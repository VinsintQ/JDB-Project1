public class Customer extends User{

    private boolean IsActive;

    public Customer(String user_Name, String first_Name, String last_Name, String hashedPassword, double balance, int user_id, char user_Role) {
        super(user_Name, first_Name, last_Name, hashedPassword,balance, user_id, user_Role);
    }

    public boolean isActive() {

        return IsActive;
    }

    public void setActive(boolean active) {

        IsActive = active;
    }
}
