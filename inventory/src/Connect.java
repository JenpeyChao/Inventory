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

    public Connect() throws SQLException, ClassNotFoundException {
        url = "jdbc:mysql://localhost:3306/inventory";
        username = "root";
        password = "maplestory";
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url, username, password);
        statement = connection.createStatement();
    }

}
