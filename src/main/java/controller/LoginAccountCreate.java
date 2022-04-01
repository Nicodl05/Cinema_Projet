package controller;


import com.example.cinema_projet.*;
import javafx.event.ActionEvent;
import model.User;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

public class LoginAccountCreate {
    ResultSet rs;
    public PreparedStatement stmt;
    SQLTools sqlTools = new SQLTools();
    public User user;

    String inputString() {
        Scanner sc = new Scanner(System.in);
        String input = sc.next();
        return input;
    }

    /**
     * Se connecter avec email et mdp
     *
     * @param email
     * @param password
     */
    public void login(String email, String password) {

        LoginController verif = new LoginController();
        user = new User();
        try {
            String query = "Select * from Person ";
            ResultSet rs = sqlTools.executeQueryWithRs(query);
            while (rs.next()) {
                if (email.equals(rs.getString("email")) && password.equals(rs.getString("pwd"))) {
                    user.id = rs.getInt("person_id");
                    user.firstName = rs.getString("f_name");
                    user.lastName = rs.getString("l_name");
                    user.bday = rs.getDate("bday");
                    user.email = rs.getString("email");
                    user.passwd = rs.getString("pwd");
                    if (rs.getInt("emp") == 1)
                        user.isEmployee = true;
                    else
                        user.isEmployee = false;

                    //verif.Connected();

                }
                if (email != rs.getString("email") && password == rs.getString("pwd"))
                    System.out.println("Mauvais Mail");
                if (email == rs.getString("email") && password != rs.getString("pwd"))
                    System.out.println("Mauvais Mot de passe");
                if (email != rs.getString("email") && password != rs.getString("pwd"))
                    System.out.println("Mauvais Mail et Mot de passe");

            }
        } catch (SQLException e) {
            throw new Error("Problem", e);
        }
    }

    public void Create_Account(String _fname, String _lname, String _email, String _pwd, String _bday) {
        int nb_id = sqlTools.GetNbRow("Person") + 1;
        // Faire les saisies pour affichage
        LocalDate date = LocalDate.parse(_bday, DateTimeFormatter.ISO_DATE);
        Date d = java.sql.Date.valueOf(date);
        try {
            String query = "INSERT INTO Person (person_id, f_name, l_name, bday, email, pwd, emp ) VALUES (?,?,?,?,?,?,+0);";
            PreparedStatement stmt = sqlTools.executeQueryWithPS(query);
            stmt.setInt(1, nb_id);
            stmt.setString(2, _fname);
            stmt.setString(3, _lname);
            stmt.setDate(4, (java.sql.Date) d);
            stmt.setString(5, _email);
            stmt.setString(6, _pwd);
            stmt.execute();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void resetPassword(String email) {
        System.out.println("New Pwd");
        String newPassword = sqlTools.inputString();
        String query = "Update Person Set pwd= '" + newPassword + "'  where email= '" + email + "' ;";
        try {
            stmt = sqlTools.executeQueryWithPS(query);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }

    }
}
