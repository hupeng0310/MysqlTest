import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MYSQLConnection {
    private static Connection connection;
    //MYSQL版本指示器，为true时MYSQL版本大于8.0,为false时MYSQL版本小于8.0
    private static boolean version = true;
    //jdbc包名
    private static final String classNameForMysql = "com.mysql.jdbc.Driver";
    private static final String classNameForMysql8plus = "com.mysql.cj.jdbc.Driver";
    private static String urlHead = "jdbc:mysql://localhost:3306/";
    //mysql8.0plus的url尾部
    private static final String urlBack = "?&serverTimezone=Asia/Shanghai";
    private static String user;
    private static String password;
    private static String database;
    //获取数据库连接
    public static Connection getConnection() {
        try {
            if(connection == null || connection.isClosed()){
                //验证数据库版本
                try {
                    Class.forName(classNameForMysql8plus);
                }catch (ClassNotFoundException e){
                    try {
                        version = false;
                        Class.forName(classNameForMysql);
                    } catch (ClassNotFoundException ex) {
                        System.out.println("错误，找不到jdbc驱动");
                    }
                }
                try{
                    if(version){
                        connection = DriverManager.getConnection(urlHead+database+urlBack,user,password);
                    }
                    else{
                        connection = DriverManager.getConnection(urlHead+database,user,password);
                    }
                }catch (SQLException e){
                    if(user == null){
                        new SQLException("mysql用户名为空").printStackTrace();
                    }
                    else if(password == null){
                        new SQLException("mysql密码为空").printStackTrace();
                    }
                    else if(database == null){
                        new SQLException("数据库未设置").printStackTrace();
                    }
                    else {
                        e.printStackTrace();
                    }
                }
            }
        } catch (SQLException e) {
            connection = null;
            getConnection();
        }
        return connection;
    }
    public static void clearConnection(){
        connection=null;
    }
    public static void setLoginInfo(String user,String password) {
        MYSQLConnection.user = user;
        MYSQLConnection.password = password;
        clearConnection();
    }
    public static void setConnection(String user,String password,String database) {
        MYSQLConnection.user = user;
        MYSQLConnection.password = password;
        MYSQLConnection.database = database;
        clearConnection();
    }
    public static void setHost(String host,String port){
        MYSQLConnection.urlHead = MYSQLConnection.urlHead.substring(0,13)+host+":"+port+"/";
        clearConnection();
    }
    private MYSQLConnection(){
    }
}
