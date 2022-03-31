package controller;

import com.example.cinema_projet.MoviesController;
import model.Movie;
import java.sql.*;
import java.util.ArrayList;



public class DbRepository {

    private final SQLTools sqlTools = new SQLTools();
    public ArrayList<Movie> movieArrayList = new ArrayList<Movie>();

    /**
     * Constructeur
     * On charge dans notre ArrayList: movieArrayList tous les films de notre base de données avec ses informations
     */
    public DbRepository() {
        //MoviesController image = new MoviesController();
        String query = "Select * from Movies";
        sqlTools.setRs(sqlTools.executeQueryWithRs(query));
        try {
            while (sqlTools.getRs().next()) {
                try {
                    Movie tosave = new Movie();
                   // System.out.println(sqlTools.getRs().getString("title"));
                    tosave.setMovieId(sqlTools.getRs().getInt("movie_id"));
                    tosave.setTitle(sqlTools.getRs().getString("title"));
                    tosave.setGenre(sqlTools.getRs().getString("genre"));
                    tosave.setReleaseDate(sqlTools.getRs().getDate("release_date"));
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
     * Retourne un film de type movie à partir de son titre
     * Nous permet d'avoir accès à toutes les infos du film
     * @param _title le titre du film
     */
    public Movie getInfoMovieBasedTitle(String _title) {
        Movie movie = new Movie();
        try {
            String query = "Select * from Movies where title='" + _title + "';";
            sqlTools.setRs(sqlTools.executeQueryWithRs(query));
            while (sqlTools.getRs().next()) {
                //System.out.println(sqlTools.getRs().getInt("movie_id"));
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
     * Retourne un film de type movie à partir de son titre
     * * Nous permet d'avoir accès à toutes les infos du film     *
     * @param _genre le genre du film
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

    /**
     * Retourne un ArrayList contenant tous les films étant disponible au visionnage
     * ( emp = 1)
     * @return notre liste de tableau
     */
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