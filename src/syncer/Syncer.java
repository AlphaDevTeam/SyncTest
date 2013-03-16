/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package syncer;

import com.AlphaDevs.Syncer.Connection.ConnectionManager;
import com.AlphaDevs.Syncer.Utils.ColumnGrabber;
import com.AlphaDevs.Syncer.XMLSync.Reader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mihindu Gajaba Karunarathne
 */
public class Syncer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        
        Syncer sync = new Syncer();
        sync.AddCustomer();
        
    }

    public void AddCustomer()
    {
        try {
            
            //ConnectionManager.CreateConnection("Scotts_Pos");
            
            ConnectionManager.CreateConnection("Scotts_Pos");
           
            //ResultSet res = ColumnGrabber.getColumns("Customer");
            
            ResultSet res = ColumnGrabber.getCustomerChanges(19,"New");
            
            System.out.println("Here");
            //ColumnGrabber.writeMetaData(res);
            boolean isFirst = false;
            String ColName="";
            
            String Url = "";
            int a = 0;
            while(res.next())
            {
                a++;
                //System.out.println("A : " + a);
                isFirst=true;
                Url = "";
                for  (int i = 1; i<= res.getMetaData().getColumnCount(); i++)
                {
                  //System.out.println(res.getMetaData().getColumnName(i) + " : " + res.getString(res.getMetaData().getColumnName(i)) + " Type : " + res.getMetaData().getColumnTypeName(i) + " - "   + res.getMetaData().getColumnType(i) );
                  ColName = res.getMetaData().getColumnName(i);
                  
                  //System.out.println("<" + ColName + ">");
                 
                  if(isFirst)
                  {
                      Url = Url + ColName;
                      isFirst=false;
                  }
                  else
                  {
                      Url = Url + "&" + ColName;                      
                  }
                  
                  
                  switch (res.getMetaData().getColumnType(i)) 
                  {
                       
                       case 4:  
                       {
                           //System.out.println("case : " + res.getMetaData().getColumnType(i));
                           Url = Url + "=" + res.getInt(ColName)  ;
                           //System.out.println(Url);
                           break;
                       }
                       case 12:
                       {
                           //System.out.println(res.getInt(ColName));
                           Url = Url + "=" + res.getInt(ColName)  ;
                           break;    
                       }
                       case -9:
                       {
                           //System.out.println(res.getString(ColName));
                           Url = Url + "=" + res.getString(ColName) ;
                           break;
                       }
                       case 3:
                       {
                           //System.out.println(res.getDouble(ColName));
                           Url = Url + "=" + res.getDouble(ColName)  ;
                           break;
                       }   
                       case 93:
                       {
                           //System.out.println(res.getDate(ColName));
                           Url = Url + "=" + res.getDate(ColName) ;
                           break;
                       }
                       case 1:
                       {
                           Url = Url + "=" + res.getString(ColName).trim()  ;
                           //System.out.println(res.getString(ColName));
                           break;
                       }
                       case -5:
                       {
                           //System.out.println(res.getDouble(ColName));
                            Url = Url + "=" + res.getDouble(ColName)  ;
                           break;
                       }
                       case 6:
                       {
                           //System.out.println(res.getFloat(ColName));
                           Url = Url + "=" + res.getFloat(ColName)  ;
                           break;
                       }
                       default: 
                       {
                           //System.out.println("KELA - " + res.getMetaData().getColumnType(i));
                           //System.out.println( res.getMetaData().getColumnTypeName(i));
                           break;
                       }
                        
                  }
                  
                  //System.out.println("</" + ColName + ">");
                  //System.out.println("Url : " + Url);
                  
                 /* 
                  if(res.getMetaData().getColumnTypeName(i).equalsIgnoreCase("nvarchar"))
                  {
                      System.out.println("------- String ---------");
                  }
                  else if(res.getMetaData().getColumnTypeName(i).equalsIgnoreCase("int"))
                  {
                      System.out.println("-------- INT --------");
                  }
                  else if(res.getMetaData().getColumnTypeName(i).equalsIgnoreCase("int identity"))
                  {
                      System.out.println("-------- INT --------");
                  }
                  else if(res.getMetaData().getColumnTypeName(i).equalsIgnoreCase("bigint"))
                  {
                      System.out.println("-------- INT --------");
                  }
                  else if(res.getMetaData().getColumnTypeName(i).equalsIgnoreCase("money"))
                  {
                      System.out.println("-------- MONEY --------");
                  }
                  else if(res.getMetaData().getColumnTypeName(i).equalsIgnoreCase("float"))
                  {
                      System.out.println("-------- FLOAT --------");
                  }
                  else if(res.getMetaData().getColumnTypeName(i).equalsIgnoreCase("datetime"))
                  {
                      System.out.println("-------- DATE --------");
                  }
                  else if(res.getMetaData().getColumnTypeName(i).equalsIgnoreCase("char"))
                  {
                      System.out.println("-------- CHAR --------");
                     
                  }
                  
                  */
                  
                }
                ColumnGrabber.UploadCustomer(Url);
                
            }
            
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(Syncer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
