package controller;

import model.Movie;
import model.User;

import java.sql.*;
import java.util.ArrayList;

public class Profile {


    public SQLTools sql = new SQLTools();
    public ResultSet rs;

    User user;

    public Profile(User user1) {
        user = user1;
    }


    void become_Emp() {
        String query = "Update Person Set emp=" + 1 + " where person_id=" + user.id + ";";
        rs = sql.executeQueryWithRs(query);
    }

    void delete_account() {
        String query = "DELETE from Person where person_id=" + user.id + ";";
        rs = sql.executeQueryWithRs(query);
    }

    public void modifyInfo() {
        System.out.println("Info a modif");
        String attribute = "f_name";    // for example
        String newinfo = "Nicolas";
        String query = "Update Person Set" + attribute + "=" + newinfo + " where person_id=" + user.id;
        rs = sql.executeQueryWithRs(query);
    }

    public void addToMovieLiked() {
        // on suppose qu'on display tt l'historique
        Movie movie = new Movie();     // pour des test
        String query = "Insert into Movies_liked (movie_id, id_user) Values (?,?);";
        try {
            PreparedStatement statement = sql.executeQueryWithPS(query);
            statement.setInt(1, movie.movieId);
            statement.setInt(2, user.id);
        } catch (SQLException E) {
            System.out.println(E);
        }
    }
    public ArrayList<Movie> loadHistoric(){
        ArrayList<Movie> movieArrayList = new ArrayList<>();
        String query = "Select * from Movies as m, Historic as h where h.id_user="+user.id+" and m.movie_id=h.movie_id;";
        try{
            rs= sql.executeQueryWithRs(query);
            while (rs.next()){
               Movie m= new Movie();
               m.movieId=rs.getInt("movie_id");
               m.title=rs.getString("title");
               m.genre=rs.getString("genre");
               m.releaseDate=rs.getDate("release_time");
               m.duration=rs.getTime("r_time");
               m.ticketPrice=rs.getInt("ticket_price");
               m.recap=rs.getString("recap");
               if(rs.getInt("available")==1)
                   m.isAvailable=true;
               else
                   m.isAvailable=false;
               m.trailer=rs.getString("trailer");
               m.urlImage=rs.getString("cover");
               movieArrayList.add(m);
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }
        return movieArrayList;
    }
}
