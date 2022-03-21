package controller;

import java.sql.*;

public class EmpModification {

    public String url = "jdbc:mysql://fournierfamily.ovh:3306/Nico_database", user_id = "jps", pwd = "poojava";
    public Connection conn;
    public Statement st;
    public ResultSet rs;
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
