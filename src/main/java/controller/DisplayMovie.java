package controller;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.Video;
import model.Movie;
import model.User;

import java.sql.*;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDate.now;

public class DisplayMovie {
    private final SQLTools sqlTools = new SQLTools();
    DisplayMovie() {

    }

    /**
     * Récupère le lien du trailer
     *
     * @param id id du film
     * @return
     */
    public String getTrailer(int id) {
        TmdbMovies movies = new TmdbApi("810c86d39163e1219bbe9a906af41da0").getMovies();
        List<Video> path = movies.getVideos(id, "fr");
        String ytlink = "https://youtu.be/" + path.get(0).getKey();
        return ytlink;
    }

    /**
     * Charge les actor id en fct du movie
     *
     * @param movie film selectionne
     * @return
     */
    public ArrayList<Integer> loadactorIds(Movie movie) {
        ArrayList<Integer> actorsID = new ArrayList<>();
        String query = "Select ac_id from Movies_Actors where movie_id=" + movie.getMovieId();
        try {
            sqlTools.setRs(sqlTools.executeQueryWithRs(query));
            while (sqlTools.getRs().next()) {
                actorsID.add(sqlTools.getRs().getInt("ac_id"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return actorsID;
    }

    /**
     * Ajoute à l'historique d'une personne, le film regardé
     *
     * @param user
     */
    public void add_to_Historic(User user, Movie movie) {
        LocalDate now = now();
        Date d = java.sql.Date.valueOf(now);
        try {
            String query = "INSERT INTO Historic (id_user,movie_id,last_viewed) VALUES (?,?,?);";
            sqlTools.setStmt(sqlTools.executeQueryWithPS(query));
            sqlTools.getStmt().setInt(1, user.getId());
            sqlTools.getStmt().setInt(2, movie.getMovieId());
            sqlTools.getStmt().setDate(3, d);
            sqlTools.getStmt().execute();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /**
     * Ajouter un film à la liste des films aimés
     *
     * @param user
     */
    public void add_movie_like(User user, Movie movie) {
        try {

            String query = "INSERT INTO Movies_liked (movie_id,user_id) VALUES (?,?);";
            sqlTools.setStmt(sqlTools.executeQueryWithPS(query));
            sqlTools.getStmt().setInt(1, user.getId());
            sqlTools.getStmt().setInt(2, movie.getMovieId());
            sqlTools.getStmt().execute();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /**
     * Load Movies liked by the user
     */
    void load_movies_liked() {
        ArrayList<String> likedMovieIds = new ArrayList<String>();
        try {
            String query = "";//= //"Select m.title from Movies as m, Movies_liked as ml where m.movie_id=ml.movie_id and ml.user_id="+ id;

            sqlTools.setRs(sqlTools.executeQueryWithRs(query));
            while (sqlTools.getRs().next()) {
                likedMovieIds.add(sqlTools.getRs().getString("title"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }


}
