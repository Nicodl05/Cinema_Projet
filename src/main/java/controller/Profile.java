package controller;

import model.Movie;
import model.User;

import java.sql.*;

public class Profile {


    public SQLTools sql = new SQLTools();
    public ResultSet rs;
<<<<<<< Updated upstream
User user;
    public Profile(User user1) {
user=user1;
=======
    User user;
    public Profile(User user1) {
user = user1;
>>>>>>> Stashed changes
    }

    void become_Emp(User user) {
        String query = "Update Person Set emp=" + 1 + " where person_id=" + user.id + ";";
        rs = sql.executeQueryWithRs(query);
    }

    void delete_account(User user) {
        String query = "DELETE from Person where person_id=" + user.id + ";";
        rs = sql.executeQueryWithRs(query);
    }
    public void modifyInfo(){
        System.out.println("Info a modif");
        String attribute="f_name";    // for example
        String newinfo="Nicolas";
        String query ="Update Person Set"+attribute+"="+newinfo+" where person_id="+user.id;
        rs= sql.executeQueryWithRs(query);
    }
<<<<<<< Updated upstream
    public void addToMovieLiked(){
        // on suppose qu'on display tt l'historique
        Movie movie=new Movie();     // pour des test
        String query = "Insert into Movies_liked (movie_id, id_user) Values (?,?);";
        try{
            PreparedStatement statement= sql.executeQueryWithPS(query);
            statement.setInt(1,movie.movieId);
            statement.setInt(2,user.id);
        }
        catch (SQLException E){
            System.out.println(E);
        }
    }
=======
   public void addToMovieLiked(){
        // on suppose qu'on display tt l'historique
       Movie movie=new Movie();     // pour des test
       String query = "Insert into Movies_liked (movie_id, id_user) Values (?,?);";
       try{
           PreparedStatement statement= sql.executeQueryWithPS(query);
           statement.setInt(1,movie.movieId);
           statement.setInt(2,user.id);
       }
       catch (SQLException E){
           System.out.println(E);
       }
   }

>>>>>>> Stashed changes


}
