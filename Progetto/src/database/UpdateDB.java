package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdateDB {

    /** @return la data di ultimo aggiornamento del DB. */
    public static Date getLastUpdate() {
        Date last = null;
        String requestSql = "select * from sql2298759.LastUpdate";
        try {
            ResultSet rs = Database.getStatement(requestSql);
            rs.next();
            last = rs.getDate(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return last;
    }

    /** Aggiorna la data di ultimo aggiornamento del DB, inserendo la data odierna. */
    public static void setLastUpdate(Date oldDate) {
        DateFormat dateFormatYMD = new SimpleDateFormat("yyyy-MM-dd");
        String newDateString = dateFormatYMD.format(new Date());
        String oldDateString = dateFormatYMD.format(oldDate);
        java.sql.Date newSQLData = java.sql.Date.valueOf(newDateString);
        java.sql.Date oldSQLData = java.sql.Date.valueOf(oldDateString);
        String requestSql = "update sql2298759.LastUpdate set Date = '" + newSQLData + "' where Date = '" + oldSQLData + "' ";
        Database.insertStatement(requestSql);
    }
}
