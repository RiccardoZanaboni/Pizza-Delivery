import java.sql.*;

public class Database {

    public static void main(String[] args) {

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/PROVA", "root", "ilrichi10");
                Statement statement = con.createStatement();
                ResultSet rs = statement.executeQuery("select * from DIPENDENTI");
                while (rs.next()) {
                    System.out.println(rs.getInt(1) + " " + rs.getString(2)+ " " + rs.getString(3));
                }
                statement.close();
                con.close();
            } catch (Exception e) {
                System.out.println(e);
            }

        }
    }
