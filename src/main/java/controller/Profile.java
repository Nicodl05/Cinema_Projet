package controller;

import model.User;

import java.sql.*;

public class Profile {


    public SQLTools sql = new SQLTools();
    public ResultSet rs;

    Profile() {

    }

    void become_Emp(User user) {
        String query = "Update Person Set emp=" + 1 + " where person_id=" + user.id + ";";
        rs = sql.executeQueryWithRs(query);
    }

    void delete_account(User user) {
        String query = "DELETE from Person where person_id=" + user.id + ";";
        rs = sql.executeQueryWithRs(query);
    }

}
