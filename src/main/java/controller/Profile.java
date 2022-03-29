package controller;

import model.Movie;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class Profile {


    public SQLTools sql = new SQLTools();
    public ResultSet rs;

    User user;

    public Profile(User user1) {
        user = user1;
    }


    void become_Emp() {
        String query = "Update Person Set emp=" + 1 + " where person_id=" + user.getId() + ";";
        rs = sql.executeQueryWithRs(query);
    }

    void delete_account() {
        String query = "DELETE from Person where person_id=" + user.getId() + ";";
        rs = sql.executeQueryWithRs(query);
    }

    public void modifyInfo() {
        System.out.println("Info a modif");
        String attribute = "f_name";    // for example
        String newinfo = "Nicolas";
        String query = "Update Person Set" + attribute + "=" + newinfo + " where person_id=" + user.getId();
        rs = sql.executeQueryWithRs(query);
    }

    public void addToMovieLiked() {
        // on suppose qu'on display tt l'historique
        Movie movie = new Movie();     // pour des test
        String query = "Insert into Movies_liked (movie_id, id_user) Values (?,?);";
        try {
            PreparedStatement statement = sql.executeQueryWithPS(query);
            statement.setInt(1, movie.getMovieId());
            statement.setInt(2, user.getId());
        } catch (SQLException E) {
            System.out.println(E);
        }
    }
    public ArrayList<Movie> loadHistoric(){
        ArrayList<Movie> movieArrayList = new ArrayList<>();
        String query = "Select * from Movies as m, Historic as h where h.id_user="+user.getId()+" and m.movie_id=h.movie_id;";
        try{
            rs= sql.executeQueryWithRs(query);
            while (rs.next()){
               Movie m= new Movie();
               m.setMovieId(rs.getInt("movie_id"));
               m.setTitle(rs.getString("title"));
               m.setGenre(rs.getString("genre"));
               m.setReleaseDate(rs.getDate("release_time"));
               m.setDuration(rs.getTime("r_time"));
               m.setTicketPrice(rs.getInt("ticket_price"));
               m.setRecap(rs.getString("recap"));
               if(rs.getInt("available")==1)
                   m.setAvailable(true);
               else
                   m.setAvailable(false);
               m.setTrailer(rs.getString("trailer"));
               m.setUrlImage(rs.getString("cover"));
               movieArrayList.add(m);
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }
        return movieArrayList;
    }
    public  Date getLastViewed(Movie m){
        Date d= new Date();
        String query = "Selet last_viewed from Historic where movie.id="+m.getMovieId() +" and user_id="+user.getId();
        try{
            rs=sql.executeQueryWithRs(query);
            while(rs.next()){
                d=rs.getDate("last_viewed");
            }
        }
        catch (SQLException E){
            System.out.println(E);
        }
        return d;
    }
}
