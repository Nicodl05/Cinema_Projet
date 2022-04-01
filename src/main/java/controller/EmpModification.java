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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.time.LocalDate.parse;

public class EmpModification {

    private final SQLTools sqlTools = new SQLTools();
    public ArrayList<User> dataUser;

    public EmpModification() {

    }
    /**
     * MAJ Disponibilité  d'un film
     *
     * @param update   statut du film
     * @param movie_id id du film
     */
    public void update_movie_status(int update, int movie_id) {
        String query = "Update Movies Set available=" + update + " where movie_id=" + movie_id + ";";
        sqlTools.setStmt(sqlTools.executeQueryWithPS(query));
        try {
            sqlTools.getStmt().executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("done");
    }

    /**
     * Maj prix d'un film
     * @param whichMovie titre du film auquel on doit modifier le prix
     */
    public void updateMoviePrice(String whichMovie, int newPrice) {

        String query = "Update Movies Set ticket_price=" + newPrice + " where title=" + whichMovie + ";";
        sqlTools.setStmt(sqlTools.executeQueryWithPS(query));
        try {
            sqlTools.getStmt().executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("done");
    }

    /**
     * Récupère le lien du trailer via l'api
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
     * Utilise l'api The Movie DataBase, permet de charger un film via la database tmdb et l'enregistrer dans la notre par la suite
     *
     * @return un film enregistré
     */
    public Movie addMovieDataAutomatic() {
        Movie movie_selected = new Movie();
        MovieDb moviedb = new MovieDb();
        System.out.println("title of the movie");
        String query = "Les Cinq Légendes";
        TmdbApi api = new TmdbApi("810c86d39163e1219bbe9a906af41da0");  // apic créee
        TmdbSearch search = new TmdbSearch(api); // objet recherche
        TmdbSearch.MultiListResultsPage resultsPage = search.searchMulti(query, "fr", 1);
        List<Multi> multiList = resultsPage.getResults();
        ArrayList<MovieDb> list_movies = new ArrayList<MovieDb>();
        for (var elem : multiList) {
            if (elem.getClass().getName().equals("info.movito.themoviedbapi.model.MovieDb"))
                list_movies.add((MovieDb) elem); // On récupère tous les films et le user choisit lequel rajouter
        }
        for (var title : list_movies) {
            System.out.println(title.getTitle());
        }
        // Ajout du Click sur le film pour récupérer le nom du film;
        String chosenMovie = list_movies.get(0).getTitle();
        for (var element : list_movies) {
            if (element.getClass().getName().equals("info.movito.themoviedbapi.model.MovieDb")) {
                MovieDb mdv = api.getMovies().getMovie(element.getId(), "fr");
                if (element.getTitle().equals(chosenMovie)) {
                    movie_selected.setMovieId(sqlTools.GetNbRow("Movies") + 1);
                    movie_selected.setTitle(mdv.getTitle());
                    movie_selected.setRecap(mdv.getOverview());
                    movie_selected.setGenre(mdv.getGenres().get(0).getName());
                    movie_selected.setDuration(sqlTools.translateTime(mdv.getRuntime()));
                    movie_selected.setUrlImage("https://image.tmdb.org/t/p/w600_and_h900_bestv2/" + mdv.getPosterPath());
                    //movie_selected.actorIds = loadactorIds(movie_selected);
                    movie_selected.setReleaseDate(java.sql.Date.valueOf(parse(mdv.getReleaseDate(), DateTimeFormatter.ISO_DATE)));
                    movie_selected.setTicketPrice(8);
                    movie_selected.setTrailer(getTrailer(mdv.getId()));
                }
            }
        }
        return movie_selected;
    }

    /**
     * Permet à l'utilisateur de rentrer toutes les infos d'un film à ajouter dans notre bdd
     *
     * @return le film chargé par l'utilisateur
     */
    public Movie addMovieDataManual(int movieId, boolean isAvailable, String title, String genre, String recap,Date releaseDate, double ticketPrice, Time duration) {
        Movie movie = new Movie(movieId,isAvailable,title,genre,recap,releaseDate,ticketPrice,duration);
        return movie;
    }

    /**
     * Ajoute un film dans la db qui a été chargé manuellement ou automatiquement
     */
    public void addMovie() {
        System.out.println("1.Manual\2.Automatic");
        // int choice = sqlTools.input1Or2();
        int choice = sqlTools.input1Or2();
        Movie movie = new Movie();
        switch (choice) {
            case 1:
                //movie = addMovieDataManual();
                break;
            case 2:
                movie = addMovieDataAutomatic();
                break;
        }
        try {
            String query = "INSERT INTO Movies (movie_id, title, genre, release_time, r_time, ticket_price, recap, available, trailer,cover) VALUES (?,?,?,?,?,?,?,?,?,?);";
            sqlTools.setStmt(sqlTools.executeQueryWithPS(query));
            sqlTools.getStmt().setInt(1, movie.getMovieId());
            sqlTools.getStmt().setString(2, movie.getTitle());
            sqlTools.getStmt().setString(3, movie.getGenre());
            sqlTools.getStmt().setDate(4, (Date) movie.getReleaseDate());
            sqlTools.getStmt().setTime(5, movie.getDuration());
            sqlTools.getStmt().setDouble(6, movie.getTicketPrice());
            sqlTools.getStmt().setString(7, movie.getRecap());
            if (movie.isAvailable())
                sqlTools.getStmt().setInt(8, 1);
            else
                sqlTools.getStmt().setInt(8, 0);
            sqlTools.getStmt().setString(9, movie.getTrailer());   // A dev le trailer avec Api
            sqlTools.getStmt().setString(10, movie.getUrlImage());
            sqlTools.getStmt().execute();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /**
     * Permet de charger dans un array toutes les informations de toutes les personnes enregistrées dans notre db
     */
    public void loadUsersData() {
        String query = "Select * from Person";
        try {
            sqlTools.setRs(sqlTools.executeQueryWithRs(query));
            while (sqlTools.getRs().next()) {
                User toSave = new User();
                toSave.setId(sqlTools.getRs().getInt("person_id"));
                toSave.setFirstName(sqlTools.getRs().getString("f_name"));
                toSave.setLastName(sqlTools.getRs().getString("l_name"));
                toSave.setEmail(sqlTools.getRs().getString("email"));
                toSave.setPasswd(sqlTools.getRs().getString("pwd"));
                if (sqlTools.getRs().getInt("emp") == 1)
                    toSave.setEmployee(true);
                else
                    toSave.setEmployee(false);

                toSave.setBday(sqlTools.getRs().getDate("bday"));
                dataUser.add(toSave);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /**
     * Permet de retourner un user à partir de son nom de famille
     * @param lName nom de famille
     * @return un user
     */
    public User getUserBasedOnLName(String lName) {
        User toget = new User();
        for (var element : dataUser) {
            if (element.getLastName().equals(lName))
                toget = element;
        }
        return toget;
    }
}
