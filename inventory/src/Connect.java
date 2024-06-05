import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.*;

public class Connect {
    private String url;
    private String username;
    private String password;
    private Connection connection;
    private Statement statement;
    private ResultSet result;
    private PreparedStatement insert;

    //sets the information thats needed from the user
    //and returns the user information
    public Users setUser(String username, String pass,Users user) throws SQLException {
        String query = "select role,ID from users where users.username ='"+username+"' and users.password ='"+pass+"'";
        insert = connection.prepareStatement(query);
        result = statement.executeQuery(query);
        if (result.next()){
            user = new Users(result.getString("role"),result.getInt("ID"));

            return user;
        }
        return new Users();

    }

    public ResultSet getUserProducts(int ID,String role) throws SQLException {
        String query;
        //checks if the user is an admin
        //if its not that admin then it will send the reports of its products
        //if it is then it will send every product
        if(role.equals("admin")){
            query = "select * from products";
        }else{
            query = "select * from products where products.ID ='"+ID+"'";
        }

        insert = connection.prepareStatement(query);
        result = statement.executeQuery(query);
        if (result.isBeforeFirst()){

            return result;
        }
        return null;
    }
    public ResultSet getUserSales(int ID, String role) throws SQLException {
        String query;
        //checks if the user is an admin
        //if its not that admin then it will send the reports of its sales
        //if it is then it will send every sale made
        if(role.equals("admin")){
            query = "select * from sales";
        }else{
            query = "select * from sales where sales.ID ='"+ID+"'";
        }

        insert = connection.prepareStatement(query);
        result = statement.executeQuery(query);
        if (result.isBeforeFirst()){

            return result;
        }
        return null;
    }
    public void insertProduct(int ID, String name, String quantity, int price){
        try{
            //inserts the new product given by the parameters
            String insertSQL = "INSERT INTO products (ID, name, quantity, price) VALUES (?, ?, ?, ?)";
            insert = connection.prepareStatement(insertSQL);
            connection.setAutoCommit(false);

            insert.setInt(1,ID);
            insert.setString(2,name);
            insert.setString(3,quantity);
            insert.setInt(4, price);
            insert.addBatch();

            int[] addedRecords = insert.executeBatch();
            connection.commit();
            System.out.println("Successfully added "+addedRecords.length+ " products!");
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateProduct(int id, String name,String quantity, int price) throws SQLException {
        String query = "select * from products where products.id = '"+id+"' and products.name = '"+name+"'";
        //gets the result from the database to see if we have it in the sytem
        //if not then we say we dont have that item
        result = statement.executeQuery(query);
        if(result.isBeforeFirst()){
            query = "";
            //if they wanted to change both
            if (!quantity.isEmpty() && price !=0){
                query = "update products set quantity='"+quantity+"', price = '"+price+"' where products.id = '"+id+"' and products.name = '"+name+"'";
            //if they wanted to change the price
            }else if (quantity.isEmpty() && price !=0){
                query = "update products set price = '"+price+"' where products.id = '"+id+"' and products.name = '"+name+"'";
            //change the quantity
            } else if (price == 0 && !quantity.isEmpty()) {
                query = "update products set quantity='"+quantity+"' where products.id = '"+id+"' and products.name = '"+name+"'";;
            }else {
                //and nothing to change
                System.out.println("You changed nothing ");
                return;
            }


            insert = connection.prepareStatement(query);
            insert.execute();
            System.out.println("Product has been updated");

        }else{
            System.out.println("Theres no product for that user");
        }
    }

        public void removeProduct(int id, String name) throws SQLException {
            String query = "select * from products where products.id = '"+id+"' and products.name = '"+name+"'";
            //gets the result from the database to see if we have it in the system
            //if not then we say we dont have that item
            result = statement.executeQuery(query);
            if(result.isBeforeFirst()){
                //deletes what the user chose
                query = "delete from products where products.id = '"+id+"' and products.name = '"+name+"'";
                insert = connection.prepareStatement(query);
                insert.execute();
                System.out.println(name +" from Userid " + id +" has been deleted");
            }else{
                System.out.println("Theres no product to delete");
            }
        }

    public Connect() throws SQLException, ClassNotFoundException {
        //connects to the database
        url = "jdbc:mysql://localhost:3306/inventory";
        username = "root";
        password = "maplestory";
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url, username, password);
        statement = connection.createStatement();
    }

}
