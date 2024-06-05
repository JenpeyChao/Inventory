import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class App {

    static Scanner myObj = new Scanner(System.in);
    static Connect connect;
    static Users user;

    public static void getAccess() throws SQLException {
        //checks if the user is in the database
        do {
            String username, password;
            System.out.print("Username: ");
            username = myObj.nextLine();
            System.out.print("Password: ");
            password = myObj.nextLine();
            user = connect.setUser(username, password,user);
            if(user.getRole().isEmpty()){
                System.out.println("Please Try again");
            }
        }while(user.getRole().isEmpty());
        menu(user.getRole());


    }
    public static void getSales() throws SQLException {
        ResultSet results = connect.getUserSales(user.getID(), user.getRole());
        //gets the results of the sql database depending on the id and user role
        if(!results.isBeforeFirst()){ // if theres no items in the results
            System.out.println("Theres no sold items!");
        }else {//if theres results from the database
            System.out.println("Sold Items:");
            while (results.next()){
                System.out.print("{");
                //if the user is an admin then it will add the user id for each sale
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
        //gets the results of the products depending on the id and the user role
        if(!results.isBeforeFirst()){ // if theres no result
            System.out.println("Theres no products!");
        }else {
            System.out.println("Products:");
            while (results.next()){
                System.out.print("{");
                //if the user is an admin then it will add the userID for every product
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
        //the choices if the user is an admin
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
            //choices if the user isnt an admin
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
        //gettting the product to add to the database
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

    private static void updateProducts() throws SQLException {
        int id,price;
        String name,quantity;
        //asks which product do you want to update
        System.out.print("Which userID's product did you want to update? ");
        id = myObj.nextInt();
        myObj.nextLine();
        System.out.print("Name of the item you want to update? ");
        name = myObj.nextLine();

        System.out.print("What is the price of the product? (leave 0 if you dont want to change)");
        price = myObj.nextInt();
        myObj.nextLine();

        System.out.print("How many? (leave blank if you dont want to change)");
        quantity = myObj.nextLine();
        connect.updateProduct(id,name,quantity,price);

    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        //start of the program
        connect = new Connect();
        getAccess();

    }
}
