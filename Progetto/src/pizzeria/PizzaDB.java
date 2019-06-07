package pizzeria;
import javafx.scene.paint.Color;

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
            Statement statement = con.createStatement();
            rs = statement.executeQuery("select * from sql7293749.Pizze");
        } catch (SQLException sqle){
            //TODO: fare in modo che stampi questo e chiuda il programma, se non c'è connessione.
            System.out.println(Services.colorSystemOut("\nSpiacenti: impossibile connettersi al momento.\nControllare connessione di rete.\n", Color.RED,true,false));
            System.out.println(sqle.getMessage());
        }
        return rs;
    }
}
