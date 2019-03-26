import java.sql.*;

public class Database {
    public Database(){}

    public void connectdatabase(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/singleschool","root","ilrichi10");
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select * from dipart");
            while (rs.next()) {
                //
            }
            statement.close();
            con.close();
        }catch (Exception e){
            System.out.println(e);
        }

    }
}
