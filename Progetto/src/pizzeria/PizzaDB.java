package pizzeria;
import java.sql.*;

public class PizzaDB {

    public static PreparedStatement putPizza(Connection con, String nome, String ingred, double prezzo){
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = con.prepareStatement("insert into sql7293749.Pizze values ('" + nome + "', '" + ingred + "', '" + prezzo + "');");

        } catch(SQLException ignored){ }
        return preparedStatement;
    }

    public static PreparedStatement removePizza(Connection con, String nome){
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = con.prepareStatement("delete from sql7293749.Pizze where nome =  '"+nome+"';");

        } catch(SQLException ignored){
            System.out.println("Problema SQL rimozione pizza");
        }
        return preparedStatement;
    }

    public static ResultSet getPizzaByName(Connection con){
        ResultSet rs = null;
        try {
            Statement statement=con.createStatement();
            rs = statement.executeQuery("select * from sql7293749.Pizze");
        }catch (SQLException sqle){
            System.out.println(sqle.getMessage());
        }
        return rs;
    }
}
