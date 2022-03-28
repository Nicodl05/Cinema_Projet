package controller;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.TmdbSearch;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.Multi;
import info.movito.themoviedbapi.model.Video;
import model.Movie;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.time.LocalDate.parse;

public class EmpModification {

    SQLTools sqlTools = new SQLTools();
    //public ResultSet rs;
    public PreparedStatement stmt;

   public  EmpModification() {

    }

    /**
     * MAJ Disponibilité  d'un film
     *
     * @param update   statut du film
     * @param movie_id id du film
     */
    public void update_movie_status(int update, int movie_id) {
        String query = "Update Movies Set available=" + update + " where movie_id=" + movie_id + ";";
       stmt= sqlTools.executeQueryWithPS(query);
        try {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("done");
    }

    /**
     * Maj prix d'un film
     */
    public void updateMoviePrice(){
        String whichMovie="";
        int newprice=sqlTools.input1OrX(20);
        String query ="Update Movies Set ticket_price=" + newprice + " where title=" + whichMovie + ";";
        stmt= sqlTools.executeQueryWithPS(query);
        try {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("done");
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
     * Load Movie info with the public database
     *
     * @return
     */
    public Movie addMovieDataAutomatic() {
        Movie movie_selected = new Movie();
        MovieDb moviedb = new MovieDb();
        System.out.println("title of the movie");
        String query = "Narnia";
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
                    movie_selected.movieId = sqlTools.GetNbRow("Movies") + 1;
                    movie_selected.title = mdv.getTitle();
                    movie_selected.recap = mdv.getOverview();
                    movie_selected.genre = mdv.getGenres().get(0).getName();
                    movie_selected.duration = sqlTools.translateTime(mdv.getRuntime());
                    movie_selected.urlImage = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/" + mdv.getPosterPath();
                    //movie_selected.actorIds = loadactorIds(movie_selected);
                    movie_selected.releaseDate = java.sql.Date.valueOf(parse(mdv.getReleaseDate(), DateTimeFormatter.ISO_DATE));
                    movie_selected.ticketPrice = 8;
                    movie_selected.trailer = getTrailer(mdv.getId());
                }
            }
        }
        return movie_selected;
    }

    /**
     * Load Movie info with the public database
     *
     * @return le film chargé
     */
    public Movie addMovieDataManual() {
        Movie movie = new Movie();
        Scanner sc = new Scanner(System.in);
        movie.movieId = sqlTools.GetNbRow("Movies") + 1;
        System.out.println("title");
        movie.title = sc.next();
        System.out.println("genre");
        movie.genre = sc.next();
        System.out.println("recap");
        movie.recap = sc.next();
        System.out.println("trailer");
        movie.trailer = sc.next();
        System.out.println("urlimage");
        movie.urlImage = sc.next();
        System.out.println("r date");
        String date = sc.next();
        try {
            movie.releaseDate = new SimpleDateFormat("yyyy-mm-dd").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("prix");
        movie.ticketPrice = sc.nextInt();
        System.out.println("durée");
        String duration = sc.next();
        movie.duration = Time.valueOf(duration);
        //movie.actorIds = loadactorIds(movie);
        return movie;
    }

    /**
     * Ajoute un film dans la db
     *
     * @param
     */
    public void addMovie() {
        System.out.println("1.Manual\2.Automatic");
        // int choice = sqlTools.input1Or2();
        int choice = sqlTools.input1Or2();
        Movie movie = new Movie();
        switch (choice) {
            case 1:
                movie = addMovieDataManual();
                break;
            case 2:
                movie = addMovieDataAutomatic();
                break;
        }
        try {
            String query = "INSERT INTO Movies (movie_id, title, genre, release_time, r_time, ticket_price, recap, available, trailer,cover) VALUES (?,?,?,?,?,?,?,?,?,?);";
            PreparedStatement stmt = sqlTools.executeQueryWithPS(query);
            stmt.setInt(1, movie.movieId);
            stmt.setString(2, movie.title);
            stmt.setString(3, movie.genre);
            stmt.setDate(4, (Date) movie.releaseDate);
            stmt.setTime(5, movie.duration);
            stmt.setDouble(6, movie.ticketPrice);
            stmt.setString(7, movie.recap);
            if (movie.isAvailable)
                stmt.setInt(8, 1);
            else
                stmt.setInt(8, 0);
            stmt.setString(9, movie.trailer);   // A dev le trailer avec Api
            stmt.setString(10, movie.urlImage);
            stmt.execute();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
