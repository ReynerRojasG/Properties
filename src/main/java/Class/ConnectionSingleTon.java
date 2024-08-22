package Class;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Reyner
 */
public class ConnectionSingleTon {
   
    private static ConnectionSingleTon singleInstance = null;
    public Connection conn;
    
    public ConnectionSingleTon(){
    String mySql_URL = "jdbc:mysql://localhost:3306/properties";
    String mySql_USER = "root";
    String mySql_PASSWORD = "1234";
    
        try{
            conn = DriverManager.getConnection(mySql_URL, mySql_USER,mySql_PASSWORD);
        }catch(SQLException e){
            e.printStackTrace();
        } 
    }
    
    public static synchronized ConnectionSingleTon getInstance(){
        if(singleInstance == null){
            singleInstance = new ConnectionSingleTon();
        }
        return singleInstance;   
    }
}
