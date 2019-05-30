package pizzeria;
import java.sql.*;
import java.util.HashMap;

public class PizzaDB extends Pizza {
    public PizzaDB(String name, HashMap<String, Toppings> ingred, double price) {
        super(name, ingred, price);
    }

    public static PreparedStatement putPizza(Connection con, String nome, String ingred, int prezzo){
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = con.prepareStatement("insert into sql7293749.Pizze values ('" + nome + "', '" + ingred + "', '" + prezzo + "');");

        } catch(SQLException ignored){ }
        return preparedStatement;
    }

    public static ResultSet getPizzaByName(Connection con, String name){
        ResultSet rs = null;
        try {
            Statement statement=con.createStatement();
            rs = statement.executeQuery("select Pizze from sql7293749.Pizze where nome = '" + name + "'");
            rs.next();
        }catch (SQLException sqle){
            System.out.println(sqle.getMessage());
        }
        return rs;
    }
}
