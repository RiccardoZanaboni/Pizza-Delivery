import org.omg.CORBA.CODESET_INCOMPATIBLE;
import pizzeria.PizzaDB;

import java.sql.*;
import java.util.Scanner;

public class Database {
    private Connection con;
    private Scanner scan = new Scanner(System.in);

    public Database(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
             con = DriverManager.getConnection("jdbc:mysql://localhost:3306/PIZZERIA?autoReconnect=true&useSSL=false", "root", "ilrichi10");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public  void PutPizza(){
        System.out.print("Inserisci nome della pizza da inserire:");
        String name =scan.nextLine();
        System.out.print("Inserisci ingrediente della pizza da inserire:");
        String ingred=scan.nextLine();
        System.out.print("Inserisci prezzo della pizza da inserire:");
        int prezzo=Integer.parseInt(scan.nextLine());
        try{
            PizzaDB.putPizza(con,name,ingred,prezzo).execute();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        Database d=new Database();
        d.PutPizza();
    }

}
