package pizzeria;

import java.sql.*;

public class CustomerDB {

    public static PreparedStatement putCostumer(Connection con, String nome, String password, String mailAddress){
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = con.prepareStatement("insert into sql7293749.Users values ('" + nome + "', '" + password +  "', '" + mailAddress + "');");
        } catch(SQLException sqle){
            Database.missingConnection();
        }
        return preparedStatement;
    }

    public static ResultSet getCustomers(Connection con, String username, String password){
        ResultSet rs = null;
        try {
            Statement statement = con.createStatement();
            rs = statement.executeQuery("select * from sql7293749.Users where User = '" + username + "' && Pass = '" + password + "' ");
        }catch (SQLException sqle){
            Database.missingConnection();
        }
        return rs;
    }

    public static PreparedStatement getInfoCustomer(Connection con, String username){
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = con.prepareStatement("select * from sql7293749.Users where User = '" + username + "' ");
        } catch(SQLException sqle){
            Database.missingConnection();
        }
        return preparedStatement;
    }

    public static PreparedStatement getUserCustomer(Connection con, String mail){
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = con.prepareStatement("select * from sql7293749.Users where MailAddress = '" + mail + "' ");
        } catch(SQLException sqle){
            Database.missingConnection();
        }
        return preparedStatement;
    }
}
