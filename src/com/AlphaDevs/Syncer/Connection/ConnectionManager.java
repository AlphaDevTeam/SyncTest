
package com.AlphaDevs.Syncer.Connection;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Mihindu Gajaba Karunarathne
 */
public class ConnectionManager 
{
    private static Connection connection=null;

    public static Connection getConnection() 
    {
        if(connection==null)
        {
            System.out.println("Connection not Set");
            return connection;
        }
        else
        {
            return connection;
        }
        
    }

    public static void setConnection(Connection connection) {
        ConnectionManager.connection = connection;
    }
    
    public static void CreateConnection(String Odbc)
    {
        try
        {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            setConnection(DriverManager.getConnection("jdbc:odbc:" + Odbc ,"sa","genuine" ));
            
            System.out.println("Connection Created");
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
    }
    public static void CloseConnection()
    {
        try
        {
            if(!ConnectionManager.connection.isClosed())
            {
                ConnectionManager.connection.close(); 
                System.out.println("Connection Closed");
            }
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
    }

}
