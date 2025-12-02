import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Login {

  public static   boolean logged_in = false;
  public static String input ;


  public static boolean LogIn(){
      Scanner kb = new Scanner(System.in);
      boolean found ;
      System.out.println("Enter User Name");
      do {

          String user_name = kb.nextLine();
         found= findUser(user_name);
         if (found){
             break;
          }else {
             System.out.println("Enter username again :");
         }
      }while (!found);

      return true;

  }
  public static void Register(){
      Scanner kb = new Scanner(System.in);
      boolean found;
      System.out.println("Enter User Name : ");
      do {

          String user_name = kb.nextLine();
          String password ;
          String first_name;
          String last_name;

          found= findUser(user_name);
         if (!found){
             System.out.println("Enter passwoord");
             password = kb.nextLine();
             System.out.println("Enter first name : ");
              first_name = kb.nextLine();
             System.out.println("Enter last name");
             last_name = kb.nextLine();
             try {
                 FileWriter Writer = new FileWriter("Users.txt",true);
                 Writer.write("\n1002"+user_name+","+first_name+","+last_name+","+password+","+"Pending,"+"0,"+"C");
                 Writer.close();
             } catch (Exception e) {
                 throw new RuntimeException(e);
             }
             System.out.println("your account has been created , and will be reviewed in 24h");

             break;
         }else {
             System.out.println("user name is used , ty another");
             user_name = kb.nextLine();
         }
      }while (found);

  }


  public static boolean findUser(String user_name){
      String line;
      try {
        BufferedReader Reader = new BufferedReader(new FileReader("Users.txt"));

          while ((line = Reader.readLine()) != null) {
              if (line.split(",")[1].equals(user_name)) {
                  return true;
              }
          }

        Reader.close();
      } catch (Exception e) {
          throw new RuntimeException(e);
      }
      return false;
  }



    public static void main(String[] args) {

        Scanner kb = new Scanner(System.in);


         while (!logged_in){
             System.out.println("1-LogIn");
             System.out.println("2-SignUp");
             input = kb.nextLine().toLowerCase();
             if (input.equals("login")){
                 if (LogIn()){
                     logged_in=true;
                 }
             }else if (input.equals("signup")){
                 Register();
             }

         }

        System.out.println("welcome back");
    }
}