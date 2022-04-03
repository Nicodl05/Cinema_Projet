package controller;


import com.example.cinema_projet.*;
import model.User;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class LoginAccountCreate {
    private final SQLTools sqlTools = new SQLTools();
    public User user;

    // A delete, servait pour des test
    String inputString() {
        Scanner sc = new Scanner(System.in);
        return sc.next();
    }

    /**
     * Se connecter avec email et mdp
     *
     * @param email
     * @param password
     */
    public User login(String email, String password) {

        LoginController verif = new LoginController();
        ArrayList<String> emails =new ArrayList<>(), mdp=new ArrayList<>();
        user = new User();
        String finalemail = "";
        String finalPassword ="";
        boolean test=false;
        try {
            String query = "Select * from Person ";
            sqlTools.setRs(sqlTools.executeQueryWithRs(query));
            while (sqlTools.getRs().next()) {
                emails.add(sqlTools.getRs().getString(("email")));
                mdp.add(sqlTools.getRs().getString(("pwd")));

                if (email.equals(sqlTools.getRs().getString("email")) && password.equals(sqlTools.getRs().getString("pwd"))) {
                    System.out.println("dedans");
                    verif.Connected();
                    user.setId(sqlTools.getRs().getInt("person_id"));
                    user.setFirstName( sqlTools.getRs().getString("f_name"));
                    user.setLastName(sqlTools.getRs().getString("l_name"));
                    user.setBday( sqlTools.getRs().getDate("bday"));
                    user.setEmail(sqlTools.getRs().getString("email"));
                    user.setPasswd(sqlTools.getRs().getString("pwd"));
                    if (sqlTools.getRs().getInt("emp") == 1)
                        user.setEmployee( true);
                    else
                        user.setEmployee( false);
                    test=true;

                }
            }
        } catch (SQLException  e)  {
            throw new Error("Problem", e);
        }
        catch (IOException i){
            throw new Error("Problem2",i);
        }
        if(!test){
            for(var elem: emails){
                System.out.println(elem);
                if(elem.equals(email))
                    user.setEmail(email);
            }
            for (var pws: mdp){
                System.out.println(pws);
                if(Objects.equals(pws, password))
                    user.setPasswd(password);
            }
        }
        if(user.getEmail()==null){
            user.setLastName("wrong email");
        }
        if(user.getPasswd()==null ){
            user.setLastName("wrong password");
        }
        if (user.getPasswd()=="" && user.getEmail()==""){
            user.setLastName("wrong password and email");
        }
        return user;
    }

    /**
     * Permet de créer un compte et de le save dans la db
     * @param _fname prénom
     * @param _lname nom de famille
     * @param _email mail du user
     * @param _pwd  mdp
     * @param _bday date de naissance
     */
    public void Create_Account(String _fname, String _lname, String _email, String _pwd, String _bday) {
        int nb_id = sqlTools.GetNbRow("Person") + 1;
        // Faire les saisies pour affichage
        LocalDate date = LocalDate.parse(_bday, DateTimeFormatter.ISO_DATE);
        Date d = java.sql.Date.valueOf(date);
        try {
            String query = "INSERT INTO Person (person_id, f_name, l_name, bday, email, pwd, emp ) VALUES (?,?,?,?,?,?,+0);";
            sqlTools.setStmt(sqlTools.executeQueryWithPS(query));
            sqlTools.getStmt().setInt(1, nb_id);
            sqlTools.getStmt().setString(2, _fname);
            sqlTools.getStmt().setString(3, _lname);
            sqlTools.getStmt().setDate(4, (java.sql.Date) d);
            sqlTools.getStmt().setString(5, _email);
            sqlTools.getStmt().setString(6, _pwd);
            sqlTools.getStmt().execute();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /**
     * Permet de faire un mot de passe oublié à partir du mail du user
     * @param email le mail
     */
    public void resetPassword(String email,String newPassword) {

        String query = "Update Person Set pwd= '" + newPassword + "'  where email= '" + email + "' ;";
        try {
            sqlTools.setStmt(sqlTools.executeQueryWithPS(query));
            sqlTools.getStmt().executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }

    }
}
