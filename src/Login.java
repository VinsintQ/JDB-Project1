import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Login {

  public static   boolean logged_in = false;
  public static String input ;


  public static boolean LogIn(){
      Scanner kb = new Scanner(System.in);
      boolean found ;
      do {
          System.out.println("Enter User Name");
          String user_name = kb.nextLine();
         found= findUser(user_name);
      }while (!found);

      return true;

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
                 System.out.println("not implemented yet");
             }

         }

        System.out.println("welcome back");
    }
}