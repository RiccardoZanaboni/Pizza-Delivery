package pizzeria;

import java.sql.*;
import java.util.Date;

public class OrderDB {
    public static PreparedStatement putOrder(Connection con, String ordercode, String username, String address, Date date){
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = con.prepareStatement("insert into sql7293749.Orders values ('" + ordercode + "', '" + username + "', '" + address + "', "+date+");");

        } catch(SQLException ignored){ }
        return preparedStatement;
    }

    public static ResultSet getOrders(Connection con){
        ResultSet rs = null;
        try {
            Statement statement=con.createStatement();
            rs = statement.executeQuery("select * from sql7293749.Orders");
        }catch (SQLException sqle){
            System.out.println(sqle.getMessage());
        }
        return rs;
    }
}
