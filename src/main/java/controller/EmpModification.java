package controller;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbSearch;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.Multi;
import model.Movie;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmpModification {

    public String url = "jdbc:mysql://fournierfamily.ovh:3306/Nico_database", user_id = "jps", pwd = "poojava";
    public Connection conn;
    public Statement st;
    public ResultSet rs;
    EmpModification(){

    }
    /**
     * Load an array with actors
     * @param movie
     * @return
     */
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
     * Retourne un String
     *
     * @return
     */
    public String inputString() {
        Scanner sc = new Scanner(System.in);
        String input="";
        do{
            input=sc.next();
        }while(input.length()==0);
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
    Integer inputclean() {
        int choice = 0;
        Scanner sc = new Scanner(System.in);
        boolean test = false;
        do {
            choice = sc.nextInt();
            if (choice>0)
                test = true;
        } while (!test);
        return choice;
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
     * MAJ Dispo d'un film
     *
     * @param update
     * @param movie_id
     */
    public void update_movie_status(int update, int movie_id) {
        try {
            conn = DriverManager.getConnection(url, user_id, pwd);
            String query = "Update Movies Set available=" + update + " where movie_id=" + movie_id + ";";
            st = conn.createStatement();
            st.execute(query);
        } catch (SQLException e) {
            System.out.println(e);
        }
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
        String query = "Star Wars ";    // à modif ici faire l'input clean du film souhaité
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
            System.out.println(title.getTitle());   // test à enlever
        // ArrayList<Integer> id_movies = new ArrayList<Integer>();
        //ArrayList<Genre> genres = new ArrayList<Genre>();

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
                    break;
                }
            }
        }
        return movie_selected;
    }

    /**
     * Load Movie info with manual input
     *
     * @return
     */
    public Movie loadMovieDataManual() {
        Movie movie = new Movie();
        System.out.println("title");
        movie.title = inputString();
        System.out.println("genre");
        movie.genre = inputString();
        System.out.println("recap");
        movie.recap = inputString();
        System.out.println("trailer");
        movie.trailer = inputString();
        System.out.println("urlimage");
        movie.urlImage = inputString();
        System.out.println("r date");
        String date = inputString();
        try {
            movie.releaseDate = new SimpleDateFormat("yyyy-mm-dd").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("prix");
        movie.ticketPrice = inputclean();
        System.out.println("durée");
        String duration = inputString();
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
}
