import org.omg.CORBA.CODESET_INCOMPATIBLE;
import pizzeria.PizzaDB;
import pizzeria.Services;

import java.awt.*;
import java.lang.invoke.SerializedLambda;
import java.sql.*;
import java.util.Scanner;

public class Database {
    private Connection con;
    private Scanner scan = new Scanner(System.in);

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
    public void putPizza(){
        try {
            System.out.print("Inserisci nome della pizza da inserire: (usa \"_\" al posto di \" \"):\t");
            String name = scan.nextLine();
            System.out.print("Inserisci ingrediente della pizza da inserire:\t");
            String ingred = scan.nextLine();
            // TODO: il prezzo va reso double
            System.out.print("Inserisci prezzo della pizza da inserire (usa il punto per i decimali):\t");
            int prezzo = Integer.parseInt(scan.nextLine());
            PizzaDB.putPizza(con, name, ingred, prezzo).execute();
        } catch (NumberFormatException nfe){
            String err = "Errore nell'inserimento dei dati della pizza. Riprovare:";
            System.out.println(Services.colorSystemOut(err,Color.RED,false,false));
            putPizza();
        } catch (SQLException sqle){
            String err = "Errore nell'inserimento della pizza nel database. Riprovare:";
            System.out.println(Services.colorSystemOut(err,Color.RED,false,false));
            putPizza();
        }
    }

    public static void main(String[] args) {
        Database d = new Database();
        d.putPizza();
    }

}
