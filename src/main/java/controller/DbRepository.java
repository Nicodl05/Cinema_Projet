package controller;

import com.example.cinema_projet.MoviesController;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.TmdbSearch;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.Multi;
import info.movito.themoviedbapi.model.Video;
import model.Movie;
import model.User;

import java.sql.*;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.time.LocalDate.*;

public class DbRepository {

    SQLTools sqlTools = new SQLTools();
    public ResultSet rs;
    public ArrayList<Movie> movieArrayList = new ArrayList<Movie>();


    public DbRepository() {
        MoviesController image = new MoviesController();
        String query = "Select * from Movies";
        rs = sqlTools.executeQueryWithRs(query);
        try {
            while (rs.next()) {
                try {
                    Movie tosave = new Movie();
                    tosave.setMovieId(rs.getInt("movie_id"));
                    tosave.setTitle(rs.getString("title"));
                    tosave.setGenre(rs.getString("genre"));
                    tosave.setReleaseDate( rs.getDate("release_time"));
                    tosave.setDuration( rs.getTime("r_time"));
                    tosave.setTicketPrice( 8);
                    tosave.setRecap(rs.getString("recap"));
                    if (rs.getInt("available") == 1)
                        tosave.setAvailable(true);
                    else
                        tosave.setAvailable(false);
                    tosave.setTrailer(rs.getString("trailer"));
                    tosave.setUrlImage( rs.getString("cover"));
                    movieArrayList.add(tosave);


                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }


    }

    /**A delete
     *
     * @param movie
     * @return
     */
    public ArrayList<Integer> loadactorIds(Movie movie) {
        ArrayList<Integer> actorsID = new ArrayList<>();
        String query = "Select ac_id from Movies_Actors where movie_id=" + movie.getMovieId();
        try {
            rs = sqlTools.executeQueryWithRs(query);
            while (rs.next()) {
                actorsID.add(rs.getInt("ac_id"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return actorsID;
    }


    /**
     * Récupère toutes les infos d'un film à partir de son titre
     *
     * @param _title
     */
    public Movie getInfoMovieBasedTitle(String _title) {
        Movie movie = new Movie();
        try {
            String query = "Select * from Movies where title=" + _title + ";";
            ResultSet rs = sqlTools.executeQueryWithRs(query);
            while (rs.next()) {
                movie.setMovieId( rs.getInt("movie_id"));
                if (rs.getInt("available") == 1)
                    movie.setAvailable(true);
                else
                    movie.setAvailable( false);
                movie.setGenre(rs.getString("genre"));
                movie.setRecap( rs.getString("recap"));
                movie.setTrailer( rs.getString("trailer"));
                movie.setUrlImage(rs.getString("cover"));
                movie.setReleaseDate( rs.getDate("release_date"));
                movie.setTicketPrice(rs.getDouble("ticket_price"));
                movie.setDuration( rs.getTime("r_time"));
                //movie.actorIds = loadactorIds(movie);
            }
        } catch (SQLException E) {
            System.out.println(E);
        }
        return movie;
    }

    /**
     * Récupère toutes les infos d'un film à partir de son genre
     *
     * @param _genre
     */
    public Movie getInfoMovieBasedGenre(String _genre) {
        Movie movie = new Movie();
        try {
            String query = "Select * from Movies where genre=" + _genre + ";";
            rs = sqlTools.executeQueryWithRs(query);
            while (rs.next()) {
                movie.setMovieId(rs.getInt("movie_id"));
                if (rs.getInt("available") == 1)
                    movie.setAvailable(true);
                else
                    movie.setAvailable(false);
                movie.setTitle(rs.getString("title"));
                movie.setRecap(rs.getString("recap"));
                movie.setTrailer(rs.getString("trailer"));
                movie.setReleaseDate(rs.getDate("release_date"));
                movie.setTicketPrice( rs.getDouble("ticket_price"));
                movie.setDuration( rs.getTime("r_time"));
                movie.setUrlImage( rs.getString("cover"));

            }
        } catch (SQLException E) {
            System.out.println(E);
        }
        return movie;
    }


}