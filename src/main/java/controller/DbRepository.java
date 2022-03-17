package controller;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbSearch;
import info.movito.themoviedbapi.model.Genre;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.Multi;
import info.movito.themoviedbapi.model.people.Person;
import model.Movie;
import model.User;

import java.sql.*;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DbRepository {
    public String url = "jdbc:mysql://fournierfamily.ovh:3306/Nico_database", user_id = "jps", pwd = "poojava";
    public Connection conn;
    public Statement st;
    ArrayList<String> movieTitles;
    public DbRepository(){

    }

    /**
     * Récupère le nb de lignes dans une table SQL
     * @param table
     * @return
     */
    public int GetNbRow(String table) {
        int cpt_col = 0;
        try {  // Code permettant de compter le nb de rows dans une table sql
            conn = DriverManager.getConnection(url, user_id, pwd);
            st = conn.createStatement();
            String query = "Select count(*) from  "+table+";";
            ResultSet rs = st.executeQuery(query);
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
     * @param table
     * @return
     */
    public  int GetNbCol(String table) {
        int cpt_col = 0;
        try {// Code permettant de compter le nb de colonnes d'attributs dans une table sql
            conn = DriverManager.getConnection(url, user_id, pwd);
            st = conn.createStatement();
            String query = "Select * from "+table+";";
            ResultSet rs = st.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            cpt_col = rsmd.getColumnCount();
        } catch (SQLException e) {
            throw new Error("Problem", e);
        }
        return cpt_col;
    }

    /**
     * Retourne un String
     * @return
     */
    String inputString() {
        Scanner sc = new Scanner(System.in);
        String input = sc.next();
        return input;
    }


    // TO FINISH
    /**
     * Charger les infos d'un Film
     */
    public Movie test_api() {
        Movie movie_selected=new Movie();
        MovieDb moviedb = new MovieDb();
        String query = "Star Wars ";
        TmdbApi api = new TmdbApi("810c86d39163e1219bbe9a906af41da0");  // apic créee
        TmdbSearch search = new TmdbSearch(api); // objet recherche
        TmdbSearch.MultiListResultsPage resultsPage = search.searchMulti(query, "fr", 1);
        List<Multi> multiList = resultsPage.getResults();
        ArrayList<String> list_movie = new ArrayList<String>();
        ArrayList<Integer> id_movies = new ArrayList<Integer>();
        ArrayList<Genre> genres = new ArrayList<Genre>();

        for (var elem : multiList) {
            if (elem.getClass().getName().equals("info.movito.themoviedbapi.model.MovieDb")) {
                 moviedb = (MovieDb) elem;
                list_movie.add(moviedb.getTitle());
                // genres.add(movieDb.getGenres());
                // String url = "https://image.tmdb.org/t/p/w600_and_h900_bestv2%22"+movieDb.getPosterPath();
                // TmdbMovies movies = new TmdbApi("810c86d39163e1219bbe9a906af41da0").getMovies();
                //MovieDb movie = movies.getMovie(5353, "fr");
                //System.out.println(movie.getTitle());
                //System.out.println(movie.getGenres());
            }
        }
        for(var title: list_movie)
            System.out.println(title);
        // Ajout du Click sur le film pour récupérer le nom du film;
        String movieSelected="";
        for (var elem : multiList) {
            if (elem.getClass().getName().equals("info.movito.themoviedbapi.model.MovieDb")) {
                if(elem.getClass().getName().equals(movieSelected)) {
                    movie_selected.movieId=GetNbRow("Movie")+1;
                    movie_selected.title=moviedb.getTitle();
                    movie_selected.recap=moviedb.getOverview();
                //    movie_selected.trailer=moviedb.get    Regarder le trailer
                 //   movie_selected.releaseDate=(java.util.Date) moviedb.getReleaseDate().getClass();
                    movie_selected.ticketPrice=8;
                    //movie_selected.duration=(Time) moviedb.getRuntime();



                }
//                    movie = (MovieDb) elem;
//                list_movie.add(movie.getTitle());

                movie_selected.genre=moviedb.getGenres().get(0).getName();
            }
        }

        return movie_selected;
    }

    /**
     * Ajoute un film dans la db
     * @param movie
     */
    public void addMovie(Movie movie) {
        String titleToSearch=inputString();

        movie.movieId = GetNbRow("Movies") + 1;
        // Blob x= new Blob();
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
            if(movie.isAvailable)
                stmt.setInt(8, 1);
            else
                stmt.setInt(8, 0);
            stmt.setString(9, movie.trailer);
            // stmt.setBlob(10,x);
            stmt.execute();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /**
     * MAJ Dispo d'un film
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
     * Stock les titres de films dans un Arraylist
     */
    public void storeMoviesTitles() {
        int nb_row = GetNbRow("Movies");
        movieTitles = new ArrayList<>(nb_row);
        {
            try {
                conn = DriverManager.getConnection(url, user_id, pwd);
                String query = "Select title from Movies";
                st = conn.createStatement();
                ResultSet rs = st.executeQuery(query);
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
     * @param _title
     */
    public void getInfoMovieBasedTitle(String _title) {
        int _movie_id = 0, _available;
        String _genre, _recap, _trailer;
        Date _release_date;//= new Date(2000,12,10);
        double _ticket_price;
        Time _r_time;//= new Time(02,32,00);
        try {
            conn = DriverManager.getConnection(url, user_id, pwd);
            String query = "Select * from Movies where title=" + _title + ";";
            st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                _movie_id = rs.getInt("movie_id");
                _available = rs.getInt("available");
                _genre = rs.getString("genre");
                _recap = rs.getString("recap");
                _trailer = rs.getString("trailer");
                _release_date = rs.getDate("release_date");
                _ticket_price = rs.getDouble("ticket_price");
                _r_time = rs.getTime("r_time");
                //blob=rsgetBlob("cover");
            }
        } catch (SQLException E) {
            System.out.println(E);
        }
    }

    /**
     * Récupère toutes les infos d'un film à partir de son genre
     * @param _genre
     */
    public void getInfoMovieBasedGenre(String _genre) {
        int _movie_id = 0, _available;
        String _title, _recap, _trailer;
        Date _release_date;//= new Date(2000,12,10);
        double _ticket_price;
        Time _r_time;//= new Time(02,32,00);
        try {
            conn = DriverManager.getConnection(url, user_id, pwd);
            String query = "Select * from Movies where genre=" + _genre + ";";
            st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                _movie_id = rs.getInt("movie_id");
                _available = rs.getInt("available");
                _title = rs.getString("title");
                _recap = rs.getString("recap");
                _trailer = rs.getString("trailer");
                _release_date = rs.getDate("release_date");
                _ticket_price = rs.getDouble("ticket_price");
                _r_time = rs.getTime("r_time");
                //blob=rsgetBlob("cover");
            }
        } catch (SQLException E) {
            System.out.println(E);
        }
    }

    /**
     * Ajoute à l'historique d'une personne, le film regardé
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
     * @param user
     */
    public void add_movie_like(User user,Movie movie) {
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
