import org.omg.CORBA.CODESET_INCOMPATIBLE;
import pizzeria.PizzaDB;
import pizzeria.Pizzeria;
import pizzeria.Services;

import java.awt.*;
import java.lang.invoke.SerializedLambda;
import java.sql.*;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class Database {
    private Connection con;
    private Scanner scan = new Scanner(System.in);

    public Database(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
             con = DriverManager.getConnection("jdbc:mysql://sql7.freesqldatabase.com:3306/sql7293749?autoReconnect=true&useSSL=false", "sql7293749", "geZxKTlyi1");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /** Consente alla pizzeria di aggiungere una pizza al Menu
     * che è salvato sul Database. */
    public void putPizza(Pizzeria pizzeria){
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
            // TODO: il prezzo va reso double
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
        d.putPizza(wolf);
    }

}
