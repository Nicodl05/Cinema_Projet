package controller;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmpModification {

    SQLTools sqlTools = new SQLTools();
    //public ResultSet rs;
    public PreparedStatement stmt;

   public  EmpModification() {

    }

    /**
     * MAJ Disponibilité  d'un film
     *
     * @param update   statut du film
     * @param movie_id id du film
     */
    public void update_movie_status(int update, int movie_id) {
        String query = "Update Movies Set available=" + update + " where movie_id=" + movie_id + ";";
       stmt= sqlTools.executeQueryWithPS(query);
        try {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("done");
    }
}
