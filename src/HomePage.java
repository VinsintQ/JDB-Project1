import com.sun.jdi.Value;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;


public class HomePage {

  public static   boolean logged_in = false;
  public static String input ;
  public static Customer customer;

  public static boolean LogIn(){
      Scanner kb = new Scanner(System.in);
      String found ;

      System.out.println("Enter User Name");
      String user_name = kb.nextLine();

      do {

          found= findUser(user_name);


         if (found!=null){

             System.out.println("Enter Password");
             String password = kb.nextLine();
             while (!checkPass(found.split(",")[4],password)){
                 System.out.println("Incorrect Password ,try Again");
                 password = kb.nextLine();
             }
             System.out.println("Welcome "+found.split(",")[1]);
              customer =  getCustomerInformation(found);

             return true;

         }else {
             System.out.println("Enter username again :");
             user_name = kb.nextLine();
         }
      }while (found==null);

      return false;

  }

  public static boolean checkPass(String password,String userInput){
           if (password.equals(userInput)){
               return true;
           }
           return false;


  }
  public static void Register(){
      Scanner kb = new Scanner(System.in);
      String found;
      String password ;
      String first_name;
      String last_name;
      System.out.println("Enter User Name : ");
      String user_name = kb.nextLine();
      do {



          found= findUser(user_name);
         if (found==null){
             System.out.println("Enter passwoord");
             password = kb.nextLine();
             System.out.println("Enter first name : ");
              first_name = kb.nextLine();
             System.out.println("Enter last name");
             last_name = kb.nextLine();
             try {
                 FileWriter Writer = new FileWriter("Users.txt",true);
                 Writer.write("\n1002"+user_name+","+first_name+","+last_name+","+password+","+"false,"+"0,"+"C");
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
      }while (found!=null);

  }


  public static String findUser(String user_name){
      String line;
      try {
        BufferedReader Reader = new BufferedReader(new FileReader("Users.txt"));

          while ((line = Reader.readLine()) != null) {
              if(line.split(",")[1].equals(user_name)) {
                  return line;
              }
          }

        Reader.close();
      } catch (Exception e) {
          throw new RuntimeException(e);
      }
      return null;
  }

   public static Customer getCustomerInformation(String userInfo){

        String userData[]=userInfo.split(",");
        Customer c1 = new Customer(Integer.parseInt(userData[0]),userData[1],userData[2],userData[3],userData[4],Boolean.parseBoolean(userData[5]),Double.parseDouble(userData[6]),userData[7].charAt(0));
    return c1;

   }


   public static void homePage(){
       Scanner kb = new Scanner(System.in);
       while (!logged_in){
           System.out.println("1-LogIn");
           System.out.println("2-SignUp");
           input = kb.nextLine().toLowerCase();
           if (input.equals("login")){
             if (LogIn()){
                 logged_in =true;
                 LandingPage l1 =  new LandingPage(customer);
                 l1.viewSeting();

             }



           }else if (input.equals("signup")){
               Register();
           }

       }


   }
    public static void main(String[] args) {

      homePage();
    }
}