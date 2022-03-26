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
                    movie_selected.actorIds = loadactorIds(movie_selected);
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
        movie.actorIds = loadactorIds(movie);
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
                movie.actorIds = loadactorIds(movie);
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
            PreparedStatement stmt = sqlTools.executeQueryWithPS(query);
            stmt.setInt(1, user.id);
            stmt.setInt(2, movie.movieId);
            stmt.setDate(3, d);
            stmt.execute();
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
            PreparedStatement stmt = sqlTools.executeQueryWithPS(query);
            stmt.setInt(1, user.id);
            stmt.setInt(2, movie.movieId);
            stmt.execute();
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

            ResultSet rs = sqlTools.executeQueryWithRs(query);
            while (rs.next()) {
                likedMovieIds.add(rs.getString("title"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}