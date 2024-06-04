import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

public class App {
    static int score;
    static int point;
    static String name;
    static String quizDifficulty;
    static String quizAnswer;
    static String url;
    static String username;
    static String password;
    static Connection connection;
    static Statement statement;
    static String query;
    static ResultSet result;

    // Input stream


    static void Connect() throws ClassNotFoundException, SQLException {
        // Set up connect to local database
        url = "jdbc:mysql://localhost:3306/StudentRegistration";
        username = "root";
        password = "maplestory";
        Class.forName("com.mysql.cj.jdbc.Driver");

        connection = DriverManager.getConnection(url,username,password);
        statement = connection.createStatement();

        // Query to get all the questions, answers, difficulties
        query = "SELECT * FROM records";
        result = null;
    }



    static void UpdateScore(){
        // Check if data back can retrieve the name from the database
        String readQuery = "";
        try {

            // Only print out top 10 scorer
            readQuery = "SELECT * from records";
            result = statement.executeQuery(readQuery);
            // Print out leader board
            System.out.println("\n--== Leader Board ==--");
            while(result.next()){
                System.out.println(result.getInt("ID") + " : " + result.getInt("studentName"));
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws Exception {
        Connect();
        UpdateScore();
    }
}
