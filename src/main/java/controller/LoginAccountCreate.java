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

public class LoginAccountCreate  {
    public String url = "jdbc:mysql://fournierfamily.ovh:3306/Nico_database", user_id = "jps", pwd = "poojava";
    public Connection conn;
    public Statement st;
    ResultSet rs;
    String inputString() {
        Scanner sc = new Scanner(System.in);
        String input = sc.next();
        return input;
    }
    public ResultSet executeQueryWithRs(String query) {
        try {
            conn = DriverManager.getConnection(url, user_id, pwd);
            st = conn.createStatement();
            rs = st.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
    /**
     * Récupère le nb de lignes dans une table SQL
     *
     * @param table
     * @return
     */
    public int GetNbRow(String table) {
        int cpt_col = 0;
        try {  // Code permettant de compter le nb de rows dans une table sql
            String query = "Select count(*) from  " + table + ";";
            rs = executeQueryWithRs(query);
            while (rs.next()) {
                cpt_col = rs.getInt(1);   // fonction a retenir
            }
        } catch (SQLException e) {
            throw new Error("Problem", e);
        }
        return cpt_col;
    }

    /**
     * Récupère le nb de colonnes dans une table SQL
     *
     * @param table
     * @return
     */
    public int GetNbCol(String table) {
        int cpt_col = 0;
        try {// Code permettant de compter le nb de colonnes d'attributs dans une table sql
            String query = "Select * from " + table + ";";
            rs = executeQueryWithRs(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            cpt_col = rsmd.getColumnCount();
        } catch (SQLException e) {
            throw new Error("Problem", e);
        }
        return cpt_col;
    }

    /**
     * Se connecter avec email et mdp
     * @param email
     * @param password
     */
    public void login(String email, String password){

        LoginController verif = new LoginController();

        User user = new User();
        try {
            conn = DriverManager.getConnection(url, user_id, pwd);
            st = conn.createStatement();
            String query = "Select * from Person ";
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                if (email.equals(rs.getString("email")) && password.equals(rs.getString("pwd"))) {
                    user.id = rs.getInt("person_id");
                    user.firstName = rs.getString("f_name");
                    user.lastName = rs.getString("l_name");
                    user.bday = rs.getDate("bday");
                    user.email = rs.getString("email");
                    user.passwd = rs.getString("pwd");
                    if(rs.getInt("emp")==1)
                        user.isEmployee=true;
                    else
                        user.isEmployee=false;
                    verif.Connected();

                }
                if (email != rs.getString("email") && password == rs.getString("pwd"))
                    System.out.println("Mauvais Mail");
                if (email == rs.getString("email") && password != rs.getString("pwd"))
                    System.out.println("Mauvais Mot de passe");
                if (email != rs.getString("email") && password != rs.getString("pwd"))
                    System.out.println("Mauvais Mot de passe et mail");
            }
        } catch (SQLException e) {
            throw new Error("Problem", e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void Create_Account(String _fname,String _lname,String _email,String _pwd,String _bday) {
        int nb_id = GetNbRow("Person") + 1;
        /*
        System.out.println("First name: ");
        String _fname = inputString();
        System.out.println("\nLast name: ");
        String _lname = inputString();
        System.out.println("\nEmail : ");
        String _email = inputString();
        System.out.println("\nPassword: ");
        String _pwd = inputString();
        System.out.println("\nBirthday (Format 1900-01-01) : ");
        String _bday = inputString();

         */
        LocalDate date=LocalDate.parse(_bday, DateTimeFormatter.ISO_DATE);
        Date d=java.sql.Date.valueOf(date);
        try {
            conn = DriverManager.getConnection(url, user_id, pwd);
            String query = "INSERT INTO Person (person_id, f_name, l_name, bday, email, pwd, emp ) VALUES (?,?,?,?,?,?,+0);";
            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1,nb_id);
            stmt.setString( 2, _fname);
            stmt.setString( 3, _lname);
            stmt.setDate(4, (java.sql.Date) d);
            stmt.setString(5, _email);
            stmt.setString( 6, _pwd);
            stmt.execute();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
