package database;

import java.sql.*;

public class CustomerDB {

    /** Aggiunge un utente al database, riempiendo i tre campi obbligatori (user, password, mail). */
    public static void putCustomer(String nome, String password, String mailAddress){
        try {
            Database.insertStatement("insert into sql2298759.Users values ('" + nome + "', '" + password +  "', '" + mailAddress + "', '', '', '') ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** @return true se l'utente esiste, cioè se il ReturnSet ha una tupla. */
    public static boolean getCustomer( String username, String password) throws SQLException{
        ResultSet rs = Database.getStatement("select * from sql2298759.Users where User = '" + username + "' && Password = '" + password + "' ");
        boolean hasRows = false;
        while (rs.next()) {
            hasRows = true;
        }
        return hasRows;
    }

    /** Recupera i dati dell'utente, identificato dall'username, tramite DB.
     * @param columnIndex è l'indice della colonna richiesta, fa riferimento alla struttura del DB:
     * 1: User, 2: Password, 3: MailAddress, 4: Name, 5: Surname, 6: Address. */
    public static String getCustomerFromUsername(String username, int columnIndex){
        ResultSet rs;
        String info = null;
        try {
            rs = Database.getStatement("select * from sql2298759.Users where User = '" + username + "' ");
            while (rs.next()) {
                info = rs.getString(columnIndex);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return info;
    }

    /** Recupera i dati dell'utente, identificato dall'indirizzo e-mail, tramite DB.
     * @param columnIndex è l'indice della colonna richiesta, fa riferimento alla struttura del DB:
     * 1: User, 2: Password, 3: MailAddress, 4: Name, 5: Surname, 6: Address. */
    public static String getInfoCustomerFromMailAddress(String mail, int columnIndex){
        ResultSet rs;
        String user = null;
        try {
            rs = Database.getStatement("select * from sql2298759.Users where MailAddress = '" + mail + "' ");
            while (rs.next()) {
                user = rs.getString(columnIndex);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    /** Aggiorna i dati dell'utente contenuti nel DB. */
    public static boolean addInfoCustomer( String username, String name, String surname, String address, String mail) {
        return Database.insertStatement("update sql2298759.Users set Name = '" + name + "', MailAddress = '" + mail +"', Surname = '" + surname + "', Address = '" + address + "' where User = '" + username + "' ");
    }

	/** @return true se l'indirizzo e-mail inserito risulta effettivamente presente nella tabella degli utenti. */
	public static boolean checkMail(String mail){
		return (getInfoCustomerFromMailAddress(mail,1) != null);
	}
}