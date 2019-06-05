package pizzeria;

import Interfaces.TextInterface;
import javafx.scene.paint.Color;

import java.sql.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Database {
    private static Connection con;
    private static Scanner scan = new Scanner(System.in);

    public static void openDatabase(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://sql7.freesqldatabase.com:3306/sql7293749?autoReconnect=true&useSSL=false", "sql7293749", "geZxKTlyi1");
        }catch (SQLException | ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    /** Consente alla pizzeria di aggiungere una pizza al Menu
     * che è salvato sul pizzeria.Database. */
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
            PizzaDB.putPizza(con, name.toUpperCase(), ingred.toUpperCase(), prezzo).execute();
        } catch (NumberFormatException nfe){
            String err = "Errore nell'inserimento dei dati della pizza. Riprovare:";
            System.out.println(Services.colorSystemOut(err,Color.RED,false,false));
        } catch (SQLException sqle){
            String err = "Errore nell'inserimento della pizza nel database. Riprovare:";
            System.out.println(Services.colorSystemOut(err,Color.RED,false,false));
        }
    }

    public static HashMap<String,Pizza> getPizze(HashMap<String,Pizza> menu) throws SQLException{  //TODO BY @ZANA DA INSERIRE NELLE DUE INTERFACCE PASSANDOCI IL MENU
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
            menu.put(nomePizza,p);
        }
        return menu;
    }

    public static boolean putCustomer(String username,String password){
        try {
            CustomerDB.putCostumer(con,username,password).execute();
            return true;
        } catch (NumberFormatException nfe){
            String err = "\nErrore nell'inserimento dei dati utente. Riprovare:";
            System.out.println(Services.colorSystemOut(err,Color.RED,false,false));
            return false;
        } catch (SQLException sqle){
            //String err = "\nErrore nell'inserimento dell'utente nel database. Riprovare:";
            //System.out.println(Services.colorSystemOut(err,Color.RED,false,false));
            return false;
        }
    }

    public static boolean getCustomers(String username,String password) throws SQLException{    //TODO BY @ZANA DA INSERIRE NELLE DUE INTERFACCE PASSANDOCI ARRAYLIST DI CUSTOMER
        ResultSet rs = CustomerDB.getCustomers(con,username,password);
        boolean rows = false;
        while(rs.next()) {
            rows = true;
        }
        return rows;
    }

    public static void main(String[] args) {
        openDatabase();
        Pizzeria wolf = new Pizzeria("wolf","via bolzano 10", LocalTime.MIN.plus(Services.getMinutes(18,30), ChronoUnit.MINUTES),
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
        try {
            putPizza(wolf);
            HashMap<String,Pizza> menu=new HashMap<>();
            getPizze(menu);
            getPizze(menu);
            System.out.println("ciao");
        } catch (SQLException e) {
            e.printStackTrace();
        }/*
        String sDate1="21:15:00";
        Calendar calendar = new GregorianCalendar();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        sDate1 = day + "/" + month + "/" + year + " " + sDate1;
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        java.util.Date data=null;
        Date dati=null;
        try {
            data=formato.parse(sDate1);
             dati=new java.sql.Date(data.getTime());
            System.out.println(dati.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            OrderDB.putOrder(con,"ORD-01","ILRICHI","CIAO",dati).execute(); //todo con data
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
    }

}
