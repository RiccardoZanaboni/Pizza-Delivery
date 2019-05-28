package pizzeria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

public class PizzaDB extends Pizza {
    public PizzaDB(String name, HashMap<String, Toppings> ingred, double price) {
        super(name, ingred, price);
    }

    public static PreparedStatement putPizza(Connection con, String nome, String ingred, int prezzo){
        PreparedStatement preparedStatement=null;
        try{
            preparedStatement =con.prepareStatement("insert into PIZZERIA.Pizze values('"+nome+"','"+ingred+"','"+prezzo+"');");

        }catch(Exception e) {
            ;
        }
        return preparedStatement;
    }

    public static ResultSet getPizzaByName(Connection con, String name){
        ResultSet rs=null;
        try {
            Statement statement=con.createStatement();
            rs=statement.executeQuery("select Pizze from PIZZERIA.Pizze where nome='"+name+"'");
            rs.next();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return rs;
    }
}
