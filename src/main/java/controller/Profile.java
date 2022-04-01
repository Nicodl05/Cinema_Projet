package controller;

import model.Movie;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class Profile {


    private final SQLTools sql = new SQLTools();
    User user;
    public Profile(User user1) {
        user = user1;
    }

    /**
     * Permet de mettre un user en tant qu'employé
     */
    void become_Emp() {
        String query = "Update Person Set emp=" + 1 + " where person_id=" + user.getId() + ";";
        sql.setRs(sql.executeQueryWithRs(query));
    }

    /**
     * Permet de supprimer le compte d'une personne
     */
    void delete_account() {
        String query = "DELETE from Person where person_id=" + user.getId() + ";";
        sql.setRs(sql.executeQueryWithRs(query));
    }

    /**
     * Permet de modifier un attribut d'une personne avec sélection de l'attribut
     */
    public void modifyInfo() {
        System.out.println("Info a modif");
        String attribute = "f_name";    // for example
        String newinfo = "Nicolas";
        String query = "Update Person Set" + attribute + "=" + newinfo + " where person_id=" + user.getId();
        sql.setRs( sql.executeQueryWithRs(query));
    }

    /**
     * Permet à l'utilsateur de save un movie dans les films liked à partir d'un film de son historique
     */
    public void addToMovieLiked(Movie movie,User user2) {
        // on suppose qu'on display tt l'historique
            // pour des test
        String query = "Insert into Movies_liked (movie_id, id_user) Values (?,?);";
        try {
            sql.setStmt(sql.executeQueryWithPS(query));
            sql.getStmt().setInt(1, movie.getMovieId());
            sql.getStmt().setInt(2, user2.getId());
        } catch (SQLException E) {
            System.out.println(E);
        }
    }

    /**
     * Permet de charger entièrement l'historique d'un utilisateur
     * @return
     */
    public ArrayList<Movie> loadHistoric(){
        ArrayList<Movie> movieArrayList = new ArrayList<>();
        String query = "Select * from Movies as m, Historic as h where h.id_user="+user.getId()+" and m.movie_id=h.movie_id;";
        try{
            sql.setRs(sql.executeQueryWithRs(query));
            while (sql.getRs().next()){
               Movie m= new Movie();
               m.setMovieId(sql.getRs().getInt("movie_id"));
               m.setTitle(sql.getRs().getString("title"));
               m.setGenre(sql.getRs().getString("genre"));
               m.setReleaseDate(sql.getRs().getDate("release_time"));
               m.setDuration(sql.getRs().getTime("r_time"));
               m.setTicketPrice(sql.getRs().getInt("ticket_price"));
               m.setRecap(sql.getRs().getString("recap"));
               if(sql.getRs().getInt("available")==1)
                   m.setAvailable(true);
               else
                   m.setAvailable(false);
               m.setTrailer(sql.getRs().getString("trailer"));
               m.setUrlImage(sql.getRs().getString("cover"));
               movieArrayList.add(m);
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }
        return movieArrayList;
    }

    /**
     * Permet de récupérer la dernière date de visionnage d'un film vu par un user
     * @param m correspond au film regardé par le user
     * @return une date de visionnage
     */
    public  Date getLastViewed(Movie m){
        Date d= new Date();
        String query = "Select last_viewed from Historic where movie.id="+m.getMovieId() +" and user_id="+user.getId();
        try{
            sql.setRs(sql.executeQueryWithRs(query));
            while(sql.getRs().next()){
                d=sql.getRs().getDate("last_viewed");
            }
        }
        catch (SQLException E){
            System.out.println(E);
        }
        return d;
    }
}
