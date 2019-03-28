import java.sql.*;

public class Database {

    public static void main(String[] args) {

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/PROVA", "root", "ilrichi10");
                PreparedStatement preparedStmt = con.prepareStatement("INSERT INTO Utenti_Pizzeria.Utenti VALUES ('MUSI',10)");
                preparedStmt.execute();
                Statement statement = con.createStatement();
                ResultSet rs = statement.executeQuery("select * from Utenti_Pizzeria.Utenti");
                while (rs.next()) {
                    System.out.println(rs.getString(1) + " " + rs.getInt(2));
                }
                statement.close();
                con.close();
            } catch (Exception e) {
                System.out.println(e);
            }

        }
    }