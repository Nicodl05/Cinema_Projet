package controller;

import java.sql.DriverManager;
import java.sql.SQLException;

public class EmpModification {

    EmpModification(){

    }

    /**
     * MAJ Dispo d'un film
     *
     * @param update
     * @param movie_id
     */
    public void update_movie_status(int update, int movie_id) {
        try {
            conn = DriverManager.getConnection(url, user_id, pwd);
            String query = "Update Movies Set available=" + update + " where movie_id=" + movie_id + ";";
            st = conn.createStatement();
            st.execute(query);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
