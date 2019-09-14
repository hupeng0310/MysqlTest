import java.sql.*;

public class Test {
    public static void main(String args[]) {
        MYSQLConnection.setHost("47.102.159.90","3306");
        MYSQLConnection.setConnection("root","root","mysql");
        Connection connection = MYSQLConnection.getConnection();
        try {
            Statement statement = connection.createStatement();
            statement.execute("select * from user");
            ResultSet resultSet =statement.getResultSet();
            while(resultSet.next()){
                System.out.println(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
}
