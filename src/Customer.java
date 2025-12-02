public class Customer extends User{

    private boolean IsActive;

    public Customer(int user_id,String user_Name, String first_Name, String last_Name, String hashedPassword,boolean isActive, double balance,  char user_Role) {
        super(user_Name, first_Name, last_Name, hashedPassword,balance, user_id, user_Role);
        this.IsActive=isActive;
    }

    public boolean isActive() {

        return IsActive;
    }

    public void setActive(boolean active) {

        IsActive = active;
    }
}
