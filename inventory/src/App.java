import java.sql.SQLException;
import java.util.Scanner;

public class App {

    static Scanner myObj = new Scanner(System.in);
    static Connect connect;
    public static void getAccess() throws SQLException {
        String username,password;
        System.out.print("Username: ");
        username = myObj.nextLine();
        System.out.print("Password: ");
        password = myObj.nextLine();
        menu(connect.getRole(username,password));


    }
    public static void menu(String role){
        if(role.equals("admin")){
            int choice;
            do{

            }while(choice!= 5);

        }else{

        }
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        connect = new Connect();
        getAccess();

    }
}
