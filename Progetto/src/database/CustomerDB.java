package database;

import javafx.scene.paint.Color;
import services.TextualPrintServices;

import java.sql.*;

public class CustomerDB {

    public static boolean putCustomer(String nome, String password, String mailAddress){
        try {
            Database.insertStatement("insert into sql7293749.Users values ('" + nome + "', '" + password +  "', '" + mailAddress + "', '', '', '') ");	// inserisce account nel DB
            return true;
        } catch (NumberFormatException nfe) {
            String err = "\nErrore nell'inserimento dei dati utente. Riprovare:";
            System.out.println(TextualPrintServices.colorSystemOut(err, Color.RED, false, false));
            return false;
        }
    }

    public static boolean getCustomer( String username, String password)throws SQLException{
        ResultSet rs = Database.getStatement("select * from sql7293749.Users where User = '" + username + "' && Pass = '" + password + "' ");
        boolean hasRows = false;
        while (rs.next()) {
            hasRows = true;
        }
        return hasRows;
    }

    public static String  getCustomerFromUsername(String username, int columnIndex){
        ResultSet rs;
        String info = null;
        try {
            rs = Database.getStatement("select * from sql7293749.Users where User = '" + username + "' ");
            while (rs.next()) {
                info = rs.getString(columnIndex);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return info;
    }

    public static String getInfoCustomerFromMailAddress(String mail, int columnIndex){
        ResultSet rs;
        String user = null;
        try {
            rs = Database.getStatement("select * from sql7293749.Users where MailAddress = '" + mail + "' ");
            while (rs.next()) {
                user = rs.getString(columnIndex);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static boolean addInfoCustomer( String username, String name, String surname, String address) {
            Database.insertStatement("update sql7293749.Users set Name = '" + name + "', Surname = '" + surname +  "', Address = '" + address + "' where User = '" + username + "' ");	// aggiorna account nel DB
            return true;

    }
}
