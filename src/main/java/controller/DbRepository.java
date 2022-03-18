package controller;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.TmdbSearch;
import info.movito.themoviedbapi.model.Genre;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.Multi;


import info.movito.themoviedbapi.model.Video;
import model.Movie;
import model.User;

import java.sql.*;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class DbRepository {
    /**
     * To DO s'occuper des BLOB
     */


    public String url = "jdbc:mysql://fournierfamily.ovh:3306/Nico_database", user_id = "jps", pwd = "poojava";
    public Connection conn;
    public Statement st;
    public ResultSet rs;
    public ArrayList<String> movieTitles;


    public DbRepository() {

    }


    public ResultSet executeQueryWithRs(String query) {
        try {
            conn = DriverManager.getConnection(url, user_id, pwd);
            st = conn.createStatement();
            rs = st.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    /**
     * Récupère le nb de lignes dans une table SQL
     *
     * @param table
     * @return
     */
    public int GetNbRow(String table) {
        int cpt_col = 0;
        try {  // Code permettant de compter le nb de rows dans une table sql

            String query = "Select count(*) from  " + table + ";";
            rs = executeQueryWithRs(query);


            while (rs.next()) {
                cpt_col = rs.getInt(1);   // fonction a retenir
            }
        } catch (SQLException e) {
            throw new Error("Problem", e);
        }
        return cpt_col;
    }

    /**
     * Récupère le nb de colonnes dans une table SQL
     *
     * @param table
     * @return
     */
    public int GetNbCol(String table) {
        int cpt_col = 0;
        try {// Code permettant de compter le nb de colonnes d'attributs dans une table sql

            String query = "Select * from " + table + ";";
            rs = executeQueryWithRs(query);

            ResultSetMetaData rsmd = rs.getMetaData();
            cpt_col = rsmd.getColumnCount();
        } catch (SQLException e) {
            throw new Error("Problem", e);
        }
        return cpt_col;
    }

    /**
     * Retourne un String
     *
     * @return
     */
    public String inputString() {
        Scanner sc = new Scanner(System.in);
        String input = sc.next();
        return input;
    }


    /**
     * Saisie sécurisée 1 ou 2 INT
     *
     * @return
     */
    Integer input1Or2() {
        int choice = 0;
        Scanner sc = new Scanner(System.in);
        boolean test = false;
        do {
            choice = sc.nextInt();
            if (choice == 1 || choice == 2)
                test = true;
        } while (!test);
        return choice;
    }


    // TO FINISH

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

    /**

     * Récupère le lien du trailer
     *
     * @param id
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
            rs = executeQueryWithRs(query);
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
    public Movie loadMovieDataAutomatic() {
        /*
         * Load Movie info with the public database
         * @return
         */


        Movie movie_selected = new Movie();
        MovieDb moviedb = new MovieDb();
        String query = "Star Wars ";
        TmdbApi api = new TmdbApi("810c86d39163e1219bbe9a906af41da0");  // apic créee
        TmdbSearch search = new TmdbSearch(api); // objet recherche
        TmdbSearch.MultiListResultsPage resultsPage = search.searchMulti(query, "fr", 1);
        List<Multi> multiList = resultsPage.getResults();
        ArrayList<MovieDb> list_movies = new ArrayList<MovieDb>();

        for (var elem : multiList) {
            if (elem.getClass().getName().equals("info.movito.themoviedbapi.model.MovieDb"))
                list_movies.add((MovieDb) elem); // On récupère tous les films et le user choisit lequel rajouter
        }
        for (var title : list_movies)
            System.out.println(title.getTitle());

        ArrayList<Integer> id_movies = new ArrayList<Integer>();
        ArrayList<Genre> genres = new ArrayList<Genre>();

        for (var elem : multiList) {
            if (elem.getClass().getName().equals("info.movito.themoviedbapi.model.MovieDb")) {
                // moviedb = (MovieDb) elem;
                list_movies.add((MovieDb) elem); // On récupère tous les films et le user choisit lequel rajouter

            }
        }
        for (var title : list_movies) {
            System.out.println(title.getTitle());
        }

        // Ajout du Click sur le film pour récupérer le nom du film;
        String chosenMovie = "";
        for (var elem : multiList) {
            if (elem.getClass().getName().equals("info.movito.themoviedbapi.model.MovieDb")) {
                if (elem.getClass().getName().equals(chosenMovie)) {
                    movie_selected.movieId = GetNbRow("Movie") + 1;
                    movie_selected.title = moviedb.getTitle();
                    movie_selected.recap = moviedb.getOverview();
                    movie_selected.genre = moviedb.getGenres().get(0).getName();
                    movie_selected.releaseDate = java.sql.Date.valueOf(LocalDate.parse(moviedb.getReleaseDate(), DateTimeFormatter.ISO_DATE));
                    movie_selected.ticketPrice = 8;
                    movie_selected.duration = translateTime(moviedb.getRuntime());
                    movie_selected.urlImage = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/" + moviedb.getPosterPath();
                    movie_selected.actorIds = loadactorIds(movie_selected);
                }

            }//

        }

        return movie_selected;
}

    /**
     * Load Movie info with the public database
     *
     * @return
     */
    public Movie loadMovieDataManual() {
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

        //  String titleToSearch = inputString();
        System.out.println("1.Manual\2.Automatic");
        int choice = input1Or2();
        Movie movie = new Movie();
        switch (choice) {
            case 1:
                movie = loadMovieDataManual();
                break;
            case 2:
                movie = loadMovieDataAutomatic();
                break;
        }
        movie.movieId = GetNbRow("Movies") + 1;



        //Blob x= new Blob();

        try {
            conn = DriverManager.getConnection(url, user_id, pwd);
            String query = "INSERT INTO Movies (movie_id, title, genre, release_time, r_time, ticket_price, recap, available, trailer,cover) VALUES (?,?,?,?,?,?,?,?,?,?);";
            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
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

            stmt.setString(10, movie.urlImage);
            // stmt.setBlob(10,x);

            stmt.execute();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }


    /**
     * Stock les titres de films dans un Arraylist
     */
    public void storeMoviesTitles() {
        int nb_row = GetNbRow("Movies");
        movieTitles = new ArrayList<>(nb_row);
        {
            try {
                String query = "Select title from Movies";
                rs = executeQueryWithRs(query);
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
            conn = DriverManager.getConnection(url, user_id, pwd);
            String query = "Select * from Movies where title=" + _title + ";";
            st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
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
            rs = executeQueryWithRs(query);
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
        // Blob x= new Blob();
        LocalDate now = LocalDate.now();
        Date d = java.sql.Date.valueOf(now);
        try {
            conn = DriverManager.getConnection(url, user_id, pwd);
            String query = "INSERT INTO Historic (id_user,movie_id,last_viewed) VALUES (?,?,?);";
            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
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
            conn = DriverManager.getConnection(url, user_id, pwd);
            String query = "INSERT INTO Movies_liked (movie_id,user_id) VALUES (?,?);";
            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, user.id);
            stmt.setInt(2, movie.movieId);
            stmt.execute();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }


}
