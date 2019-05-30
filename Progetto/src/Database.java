import org.omg.CORBA.CODESET_INCOMPATIBLE;
import pizzeria.*;

import java.awt.*;
import java.lang.invoke.SerializedLambda;
import java.sql.*;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Database {
    private static Connection con;
    private static Scanner scan = new Scanner(System.in);

    public Database(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
             con = DriverManager.getConnection("jdbc:mysql://sql7.freesqldatabase.com:3306/sql7293749?autoReconnect=true&useSSL=false", "sql7293749", "geZxKTlyi1");
        }catch (SQLException | ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    /** Consente alla pizzeria di aggiungere una pizza al Menu
     * che è salvato sul Database. */
    public static void putPizza(Pizzeria pizzeria){
        try {
            System.out.print("Inserisci nome della pizza da inserire: (usa \"_\" al posto di \" \"):\t");
            String name = scan.nextLine();
            String ingred="";
            do {
                String adding = Services.colorSystemOut("Inserisci gli ingredienti da AGGIUNGERE, separati da virgola, poi invio:",Color.YELLOW,false,false);
                System.out.println(adding);
                System.out.println(TextInterface.possibleAddictions(pizzeria));
                ingred=scan.nextLine();
            }while(ingred.toUpperCase().equals("OK"));
            System.out.print("Inserisci prezzo della pizza da inserire (usa il punto per i decimali):\t");
            double prezzo = Double.parseDouble(scan.nextLine());
            PizzaDB.putPizza(con, name, ingred, prezzo).execute();
        } catch (NumberFormatException nfe){
            String err = "Errore nell'inserimento dei dati della pizza. Riprovare:";
            System.out.println(Services.colorSystemOut(err,Color.RED,false,false));
        } catch (SQLException sqle){
            String err = "Errore nell'inserimento della pizza nel database. Riprovare:";
            System.out.println(Services.colorSystemOut(err,Color.RED,false,false));
        }
    }

    public static void GetPizze(ArrayList<Pizza> menu) throws SQLException{
        ResultSet rs=PizzaDB.getPizzaByName(con);
        while(rs.next()){
            HashMap<String, Toppings> ingr = new HashMap<>();
            String nomePizza=rs.getString(1);
            String ingrediente=rs.getString(2);
            StringTokenizer stAgg = new StringTokenizer(ingrediente);
            while (stAgg.hasMoreTokens()) {
                try {
                    String ingredienteAggiuntoString = Services.arrangeIngredientString(stAgg);
                    Toppings toppings = Toppings.valueOf(ingredienteAggiuntoString);
                    ingr.put(toppings.name(),toppings);
                } catch (Exception ignored) { }
            }
            double prezzo=rs.getDouble(3);
            Pizza p=new Pizza(nomePizza,ingr,prezzo);
            menu.add(p);
        }
    }

    public static void main(String[] args) {
        Database d = new Database();
        Pizzeria wolf=new Pizzeria("wolf","via bolzano 10", LocalTime.MIN.plus(Services.getMinutes(18,30), ChronoUnit.MINUTES),
                LocalTime.MIN.plus(Services.getMinutes(0,0), ChronoUnit.MINUTES),
                LocalTime.MIN.plus(Services.getMinutes(18,30), ChronoUnit.MINUTES),
                LocalTime.MIN.plus(Services.getMinutes(18,30), ChronoUnit.MINUTES),
                LocalTime.MIN.plus(Services.getMinutes(18,30), ChronoUnit.MINUTES),
                LocalTime.MIN.plus(Services.getMinutes(18,30), ChronoUnit.MINUTES),
                LocalTime.MIN.plus(Services.getMinutes(18,30), ChronoUnit.MINUTES),
                // orari di chiusura, da domenica a sabato
                LocalTime.MIN.plus(Services.getMinutes(23,30), ChronoUnit.MINUTES),
                LocalTime.MIN.plus(Services.getMinutes(0,0), ChronoUnit.MINUTES),
                LocalTime.MIN.plus(Services.getMinutes(23,30), ChronoUnit.MINUTES),
                LocalTime.MIN.plus(Services.getMinutes(23,59), ChronoUnit.MINUTES),
                LocalTime.MIN.plus(Services.getMinutes(23,30), ChronoUnit.MINUTES),
                LocalTime.MIN.plus(Services.getMinutes(23,30), ChronoUnit.MINUTES),
                LocalTime.MIN.plus(Services.getMinutes(23,30), ChronoUnit.MINUTES)
        );
        putPizza(wolf);
        try {
            ArrayList<Pizza> pizzas=new ArrayList<>();
            GetPizze(pizzas);
            System.out.println("Ciao");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
