package controller;

import model.Movie;
import model.User;
import java.sql.*;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class DbRepository {


    SQLTools sqlTools = new SQLTools();
    public ResultSet rs;
    public ArrayList<String> movieTitles;


    public DbRepository() {

    }

    /**
     * Charger les infos d'un Film
     */
    public Time translateTime(int retrievedTime) {
        Time t;
        int cpt_hour = 0;
        while (retrievedTime >= 60) {
            cpt_hour++;
            retrievedTime -= 60;
        }
        t = new Time(cpt_hour, retrievedTime, 0);

        return t;
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
     * @return le film chargé
     */
    public Movie addMovieDataManual() {
        Movie movie = new Movie();
        Scanner sc = new Scanner(System.in);
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
        int choice = sqlTools.input1Or2();
        Movie movie = new Movie();
        switch (choice) {
            case 1:
                movie = addMovieDataManual();
                break;
            case 2:
                // movie = loadMovieDataAutomatic();
                break;
        }
        movie.movieId = sqlTools.GetNbRow("Movies") + 1;
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
     * Stock les titres de films dans un Arraylist
     */
    public void storeMoviesTitles() {
        int nb_row = sqlTools.GetNbRow("Movies");
        movieTitles = new ArrayList<>(nb_row);
        {
            try {
                String query = "Select title from Movies";
                rs = sqlTools.executeQueryWithRs(query);
                while (rs.next()) {
                    movieTitles.add(rs.getString("title"));
                }
            } catch (SQLException E) {
                System.out.println(E);
            }
        }
        for (var movie : movieTitles) {
            System.out.println(movie);
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
        LocalDate now = LocalDate.now();
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