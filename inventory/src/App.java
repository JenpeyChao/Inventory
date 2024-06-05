import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class App {

    static Scanner myObj = new Scanner(System.in);
    static Connect connect;
    static Users user;

    public static void getAccess() throws SQLException {
        do {
            String username, password;
            System.out.print("Username: ");
            username = myObj.nextLine();
            System.out.print("Password: ");
            password = myObj.nextLine();
            user = connect.setUser(username, password,user);

        }while(user.getRole().isEmpty());
        menu(user.getRole());


    }
    public static void getSales() {
    }

    public static void getProducts() throws SQLException {
        ResultSet results = connect.getUserProducts(user.getID(), user.getRole());

        System.out.println("Products:");
        do{
            System.out.print("{");
            if(user.getRole().equals("admin")) {
                System.out.print("UserID: " + results.getString("ID") + " ");
            }
            System.out.print("Name: "+results.getString("name") + ",");
            System.out.print(" Quantity: "+results.getString("quantity")+ ",");
            System.out.print(" Price: "+(double)(results.getInt("price")/100) + "}");
            System.out.println();
        }while(results.next());




    }
    public static void menu(String role) throws SQLException {
        int choice;
        if(role.equals("admin")){
            do{
                System.out.println("Admin Menu:");
                System.out.println("1) View products");
                System.out.println("2) View Sales");
                choice = myObj.nextInt();
                myObj.nextLine();
                switch(choice){
                    case 1 -> getProducts();
                    case 2 -> getSales();
                }
            }while(choice!= 5);

        }else{
            do{
                System.out.println("User Menu:");
                System.out.println("1) View products");
                System.out.println("2) View Sales");
                System.out.println("3) exit");

                choice = myObj.nextInt();
                myObj.nextLine();
                switch(choice){
                    case 1 -> getProducts();
                    case 2 -> getSales();
                }
            }while(choice!= 3);
        }
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        connect = new Connect();
        getAccess();

    }
}
