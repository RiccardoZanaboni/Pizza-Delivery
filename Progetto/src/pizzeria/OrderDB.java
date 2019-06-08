package pizzeria;

import java.sql.*;
import java.util.ArrayList;

public class OrderDB {
    public static PreparedStatement putOrder(Connection con,Order order, Timestamp date){
        PreparedStatement preparedStatement = null;
        try {
            String requestSql="insert into sql7293749.Orders" + "(orderID, username, address, date) VALUES" + "(?,?,?,?)";
            preparedStatement = con.prepareStatement(requestSql);
            preparedStatement.setString(1, order.getOrderCode());
            preparedStatement.setString(2,order.getName() );
            preparedStatement.setString(3,order.getAddress() );
            preparedStatement.setTimestamp(4,date);

        } catch(SQLException ignored){ }
        return preparedStatement;
    }

    public static void putOrderedPizzas(Connection con,Order order){
        PreparedStatement preparedStatement = null;
        try {
            for(Pizza p:order.getOrderedPizze()) {
                preparedStatement = con.prepareStatement("insert into sql7293749.OrderedPizza values ('" + order.getOrderCode() + "', '" + p.getName() + "', '" + p.getDescription()+ "', '" + p.getPrice() + "');");
                preparedStatement.execute();
            }
        } catch(SQLException sqle){
            System.out.println(sqle.getMessage());
        }
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

    public static ResultSet getOrderedPizzasById(Connection con,String orderID){
        ResultSet rs = null;
        try {
            Statement statement=con.createStatement();
            rs = statement.executeQuery("select nome,ingrediente,prezzo from sql7293749.Orders natural join sql7293749.OrderedPizza where orderID="+"\""+orderID+"\"");
        }catch (SQLException sqle){
            System.out.println(sqle.getMessage());
        }
        return rs;
    }
}
