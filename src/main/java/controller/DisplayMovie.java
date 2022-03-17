package controller;

import java.sql.*;
import java.util.Scanner;

public class DisplayMovie {
    public String url = "jdbc:mysql://fournierfamily.ovh:3306/Nico_database", user_id = "jps", pwd = "poojava";
    public Connection conn;
    public Statement st;

    public int Get_nb_row(String table) {
        int cpt_col = 0;
        try {  // Code permettant de compter le nb de rows dans une table sql
            conn = DriverManager.getConnection(url, user_id, pwd);
            st = conn.createStatement();
            String query = "Select count(*) from  "+table+";";
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                cpt_col = rs.getInt(1);   // fonction a retenir
            }

        } catch (SQLException e) {
            throw new Error("Problem", e);
        }
        return cpt_col;
    }
    public  int Get_nb_Col(String table) {
        int cpt_col = 0;
        try {// Code permettant de compter le nb de colonnes d'attributs dans une table sql
            conn = DriverManager.getConnection(url, user_id, pwd);
            st = conn.createStatement();
            String query = "Select * from "+table+";";
            ResultSet rs = st.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            cpt_col = rsmd.getColumnCount();
        } catch (SQLException e) {
            throw new Error("Problem", e);
        }
        return cpt_col;
    }

    String inputString() {
        Scanner sc = new Scanner(System.in);
        String input = sc.next();
        return input;
    }

    //Display movies with an actor
   /* public void loadMoviesActor() {
        try {
            conn = DriverManager.getConnection(url, user_id, pwd);
            String query = "Select m.title from Actors as a,Movies as m, Movies_Actors as ma  where ma.ac_id=" + ac_id + " and m.movie_id=ma.movie_id;";
            st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
               // movies.add(rs.getString("title"));
                //blob=rsgetBlob("cover");
            }
        } catch (SQLException E) {
            System.out.println(E);
        }
    }*/
}
