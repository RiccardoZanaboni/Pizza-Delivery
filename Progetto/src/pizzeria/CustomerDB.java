package pizzeria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class CustomerDB {

    public static PreparedStatement putCostumer(Connection con, String nome, String password){
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = con.prepareStatement("insert into sql7293749.Users values ('" + nome + "', '" + password +"');");

        } catch(Exception ignored){ }
        return preparedStatement;
    }

    public static ResultSet getCustomers(Connection con,String username,String password){
        ResultSet rs = null;
        try {
            Statement statement = con.createStatement();

            rs = statement.executeQuery("select * from sql7293749.Users where User = '"+ username + "' and Pass= '"+ password +"'"  );
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return rs;
    }
}
