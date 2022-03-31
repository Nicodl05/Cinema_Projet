package controller;

import com.example.cinema_projet.MoviesController;
import model.Movie;
import java.sql.*;
import java.util.ArrayList;



public class DbRepository {

    private final SQLTools sqlTools = new SQLTools();
    public ArrayList<Movie> movieArrayList = new ArrayList<Movie>();
    public DbRepository() {
        MoviesController image = new MoviesController();
        String query = "Select * from Movies";
        sqlTools.setRs(sqlTools.executeQueryWithRs(query));
        try {
            while (sqlTools.getRs().next()) {
                try {
                    Movie tosave = new Movie();
                    tosave.setMovieId(sqlTools.getRs().getInt("movie_id"));
                    tosave.setTitle(sqlTools.getRs().getString("title"));
                    tosave.setGenre(sqlTools.getRs().getString("genre"));
                    tosave.setReleaseDate(sqlTools.getRs().getDate("release_time"));
                    tosave.setDuration(sqlTools.getRs().getTime("r_time"));
                    tosave.setTicketPrice(8);
                    tosave.setRecap(sqlTools.getRs().getString("recap"));
                    if (sqlTools.getRs().getInt("available") == 1)
                        tosave.setAvailable(true);
                    else
                        tosave.setAvailable(false);
                    tosave.setTrailer(sqlTools.getRs().getString("trailer"));
                    tosave.setUrlImage(sqlTools.getRs().getString("cover"));
                    movieArrayList.add(tosave);


                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /**
     * A delete
     *
     * @param movie
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
     * Récupère toutes les infos d'un film à partir de son titre
     *
     * @param _title
     */
    public Movie getInfoMovieBasedTitle(String _title) {
        Movie movie = new Movie();
        try {
            String query = "Select * from Movies where title=" + _title + ";";
            sqlTools.setRs(sqlTools.executeQueryWithRs(query));
            while (sqlTools.getRs().next()) {
                movie.setMovieId(sqlTools.getRs().getInt("movie_id"));
                if (sqlTools.getRs().getInt("available") == 1)
                    movie.setAvailable(true);
                else
                    movie.setAvailable(false);
                movie.setGenre(sqlTools.getRs().getString("genre"));
                movie.setRecap(sqlTools.getRs().getString("recap"));
                movie.setTrailer(sqlTools.getRs().getString("trailer"));
                movie.setUrlImage(sqlTools.getRs().getString("cover"));
                movie.setReleaseDate(sqlTools.getRs().getDate("release_date"));
                movie.setTicketPrice(sqlTools.getRs().getDouble("ticket_price"));
                movie.setDuration(sqlTools.getRs().getTime("r_time"));
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
            sqlTools.setRs( sqlTools.executeQueryWithRs(query));
            while (sqlTools.getRs().next()) {
                movie.setMovieId(sqlTools.getRs().getInt("movie_id"));
                if (sqlTools.getRs().getInt("available") == 1)
                    movie.setAvailable(true);
                else
                    movie.setAvailable(false);
                movie.setTitle(sqlTools.getRs().getString("title"));
                movie.setRecap(sqlTools.getRs().getString("recap"));
                movie.setTrailer(sqlTools.getRs().getString("trailer"));
                movie.setReleaseDate(sqlTools.getRs().getDate("release_date"));
                movie.setTicketPrice(sqlTools.getRs().getDouble("ticket_price"));
                movie.setDuration(sqlTools.getRs().getTime("r_time"));
                movie.setUrlImage(sqlTools.getRs().getString("cover"));

            }
        } catch (SQLException E) {
            System.out.println(E);
        }
        return movie;
    }

    public ArrayList<Movie> getAvailableMovies() {
        String query = "Select * from Movies where available=" + 1;
        ArrayList<Movie> availableMovies = new ArrayList<>();

        try {
            sqlTools.setRs(sqlTools.executeQueryWithRs(query));
            while (sqlTools.getRs().next()) {
                Movie movie = new Movie();
                movie.setMovieId(sqlTools.getRs().getInt("movie_id"));
                movie.setAvailable(true);
                movie.setTitle(sqlTools.getRs().getString("title"));
                movie.setRecap(sqlTools.getRs().getString("recap"));
                movie.setTrailer(sqlTools.getRs().getString("trailer"));
                movie.setReleaseDate(sqlTools.getRs().getDate("release_date"));
                movie.setTicketPrice(sqlTools.getRs().getDouble("ticket_price"));
                movie.setDuration(sqlTools.getRs().getTime("r_time"));
                movie.setUrlImage(sqlTools.getRs().getString("cover"));
                availableMovies.add(movie);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return availableMovies;
    }
}