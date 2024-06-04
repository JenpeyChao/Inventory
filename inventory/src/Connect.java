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
    public String getRole(String user, String pass) throws SQLException {
        String query = "select role from users where users.username ='"+user+"' and users.password ='"+pass+"'";
        insert = connection.prepareStatement(query);
        result = statement.executeQuery(query);
        result.next();
        return result.getString("role");

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
