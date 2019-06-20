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
            rs = statement.executeQuery("select * from sql7293749.Users where User = '" + username + "' and Pass = '" + password + "' ");
        }catch (SQLException sqle){
            Database.missingConnection();
        }
        return rs;
    }

    public static PreparedStatement getMailAddress(Connection con, String username){
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = con.prepareStatement("select * from sql7293749.Users where User = '" + username + "' && MailAddress is not null");
        } catch(SQLException sqle){
            Database.missingConnection();
        }
        return preparedStatement;
    }
}
