package controller;

import model.User;

import java.sql.*;

public class Profile {


    public String url = "jdbc:mysql://fournierfamily.ovh:3306/Nico_database", user_id = "jps", pwd = "poojava";
    public Connection conn;
    public Statement st;
    public ResultSet rs;
    Profile(){

    }
    void become_Emp(User user){
        try {
            conn = DriverManager.getConnection(url, user_id, pwd);
            String query = "Update Person Set emp=" + 1 + " where person_id=" + user.id + ";";
            st = conn.createStatement();
            st.execute(query);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    void delete_account(User user){
        try {
            conn = DriverManager.getConnection(url, user_id, pwd);
            String query = "DELETE from Person where person_id=" + user.id + ";";
            st = conn.createStatement();
            st.execute(query);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
