
package com.AlphaDevs.Syncer.Utils;

import com.AlphaDevs.Syncer.Connection.ConnectionManager;
import com.AlphaDevs.Syncer.XMLSync.Reader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Mihindu Gajaba Karunarathne
 */
public class ColumnGrabber 
{
    private static Statement sqlStatement;
    private static Reader xmlReader = new Reader();
    public static ResultSet getColumns(String TableName)
    {
        ResultSet result;
        try
        {
            sqlStatement = ConnectionManager.getConnection().createStatement();
            result = sqlStatement.executeQuery("Select * from " + TableName);
            
            /*result = sqlStatement.executeQuery("SELECT sysobjects.name AS TableName, syscolumns.name AS ColumnName "
                + "FROM sysobjects INNER JOIN syscolumns ON sysobjects.id = syscolumns.id "
                + "WHERE (sysobjects.xtype = 'U') AND (sysobjects.name = N'" + TableName +"')");*/
            return result;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
        
    }
    
    public static void UploadCustomer(String Data)
    {
        try
        {                
            String urlParameters = "param1=a&param2=b&param3=c";
            urlParameters = Data;
            String request = "http://www.alphadevs.com/php/SyncCustomer.php?";
            URL url = new URL(request); 
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();           
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setInstanceFollowRedirects(false); 
            connection.setRequestMethod("POST"); 
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
            connection.setRequestProperty("charset", "utf-8");
            connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
            connection.setUseCaches (false);

            DataOutputStream wr = new DataOutputStream(connection.getOutputStream ());
            wr.writeBytes(urlParameters);
            System.out.println("Writing : " + urlParameters + " With Size of : " + urlParameters.getBytes().length );
            wr.flush();
            wr.close();
            
            DataInputStream in = new DataInputStream(connection.getInputStream());
            
            if(xmlReader.isSuccessful(in.readUTF()))
            {
                System.out.println("Added");
            }
            else
            {
                System.out.println("Error");
            }
                    
            in.close();
            connection.disconnect();
            
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    
    public static ResultSet getCustomerChanges(int LocationID, String TrnType)
    {
        ResultSet result;
        try
        {
            sqlStatement = ConnectionManager.getConnection().createStatement();
            
            String strSql ="";
            
            strSql = " SELECT  ItemChanges.ID as ChangeID , Customer.* FROM   ItemChanges INNER JOIN "
                    + " Customer ON ItemChanges.ItemID = Customer.CustomerID "
                    + " WHERE   (NOT (ItemChanges.ID IN "
                    + " (SELECT ChangeID From ItemSent "
                    + " WHERE   LocationID = " + LocationID + "))) AND (ItemChanges.TrnType ='" + TrnType + "') AND ( ItemChanges.TableID = '7' ) ";
            
            result = sqlStatement.executeQuery(strSql);
            
            /*result = sqlStatement.executeQuery("SELECT sysobjects.name AS TableName, syscolumns.name AS ColumnName "
                + "FROM sysobjects INNER JOIN syscolumns ON sysobjects.id = syscolumns.id "
                + "WHERE (sysobjects.xtype = 'U') AND (sysobjects.name = N'" + TableName +"')");*/
            return result;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
        
        
    }
    
    public static void writeMetaData(ResultSet resultSet) throws SQLException {
    //   Now get some metadata from the database
    // Result set get the result of the SQL query
    
    System.out.println("The columns in the table are: ");
    
    System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
    for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
      System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i) + " - " + resultSet.getMetaData().getColumnType(i)+ " - " + resultSet.getMetaData().getColumnTypeName(i));
    }
  }
    
}
