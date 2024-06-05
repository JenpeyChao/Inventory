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
    public static void getSales() throws SQLException {
        ResultSet results = connect.getUserSales(user.getID(), user.getRole());
        if(!results.isBeforeFirst()){
            System.out.println("Theres no sold items!");
        }else {
            System.out.println("Sold Items:");
            while (results.next()){
                System.out.print("{");
                if (user.getRole().equals("admin")) {
                    System.out.print("UserID: " + results.getString("ID") + " ");
                }
                System.out.print("Name: " + results.getString("name") + ",");
                System.out.print(" Number Of Sales: " + results.getString("quantity") + ",");
                System.out.print(" Price: " + (double) (results.getInt("price") / 100) + ",");
                System.out.print(" Time of Sale: " + results.getString("time") + "}");
                System.out.println();
            }
        }
    }

    public static void getProducts() throws SQLException {
        ResultSet results = connect.getUserProducts(user.getID(), user.getRole());
        if(!results.isBeforeFirst()){
            System.out.println("Theres no products!");
        }else {
            System.out.println("Products:");
            while (results.next()){
                System.out.print("{");
                if (user.getRole().equals("admin")) {
                    System.out.print("UserID: " + results.getString("ID") + " ");
                }
                System.out.print("Name: " + results.getString("name") + ",");
                System.out.print(" Quantity: " + results.getString("quantity") + ",");
                System.out.print(" Price: " + (double) (results.getInt("price") / 100) + "}");
                System.out.println();
            }
        }
    }

    public static void menu(String role) throws SQLException {
        int choice;
        if(role.equals("admin")){
            do{
                System.out.println("Admin Menu:");
                System.out.println("1) View products");
                System.out.println("2) View Sales");
                System.out.println("3) Update products");
                System.out.println("4) Add products");
                System.out.println("5) delete products");
                System.out.println("6) exit");
                choice = myObj.nextInt();
                myObj.nextLine();
                switch(choice){
                    case 1 -> getProducts();
                    case 2 -> getSales();
                    case 3 -> updateProducts();
                    case 4 -> addProducts();
                    case 5 -> deleteProducts();
                    
                }
            }while(choice!= 6);

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

    private static void deleteProducts() {

    }

    private static void addProducts() {
        int id,price;
        String name,quantity;
        System.out.print("Which user id? ");
        id = myObj.nextInt();
        myObj.nextLine();

        System.out.print("Name of the item? ");
        name = myObj.nextLine();

        System.out.print("How much is the product? ");
        price = myObj.nextInt();
        myObj.nextLine();

        System.out.print("How many? ");
        quantity = myObj.nextLine();
        connect.insertProduct(id,name,quantity,price);
    }

    private static void updateProducts() {
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        connect = new Connect();
        getAccess();

    }
}
