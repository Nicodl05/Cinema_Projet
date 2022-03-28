package controller;

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
        String query = "Select * from Movies";
        rs = sqlTools.executeQueryWithRs(query);
        try {
            while (rs.next()) {
                try {
                    Movie tosave = new Movie();
                    tosave.movieId = rs.getInt("movie_id");
                    tosave.title = rs.getString("title");
                    tosave.genre = rs.getString("genre");
                    tosave.releaseDate = rs.getDate("release_time");
                    tosave.duration = rs.getTime("r_time");
                    tosave.ticketPrice = 8;
                    tosave.recap = rs.getString("recap");
                    if (rs.getInt("available") == 1)
                        tosave.isAvailable = true;
                    else
                        tosave.isAvailable = false;
                    tosave.trailer = rs.getString("trailer");
                    tosave.urlImage = rs.getString("cover");
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
        String query = "Select ac_id from Movies_Actors where movie_id=" + movie.movieId;
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
                movie.movieId = rs.getInt("movie_id");
                if (rs.getInt("available") == 1)
                    movie.isAvailable = true;
                else
                    movie.isAvailable = false;
                movie.genre = rs.getString("genre");
                movie.recap = rs.getString("recap");
                movie.trailer = rs.getString("trailer");
                movie.urlImage = rs.getString("cover");
                movie.releaseDate = rs.getDate("release_date");
                movie.ticketPrice = rs.getDouble("ticket_price");
                movie.duration = rs.getTime("r_time");
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
                movie.movieId = rs.getInt("movie_id");
                if (rs.getInt("available") == 1)
                    movie.isAvailable = true;
                else
                    movie.isAvailable = false;
                movie.title = rs.getString("title");
                movie.recap = rs.getString("recap");
                movie.trailer = rs.getString("trailer");
                movie.releaseDate = rs.getDate("release_date");
                movie.ticketPrice = rs.getDouble("ticket_price");
                movie.duration = rs.getTime("r_time");
                movie.urlImage = rs.getString("cover");

            }
        } catch (SQLException E) {
            System.out.println(E);
        }
        return movie;
    }


}