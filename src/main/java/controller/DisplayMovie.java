package controller;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.TmdbSearch;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.Multi;
import info.movito.themoviedbapi.model.Video;
import model.Movie;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DisplayMovie {
    SQLTools sqlTools = new SQLTools();
    public ResultSet rs;
    DisplayMovie(){

    }


    /**Récupère le lien du trailer

     *
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
     * Charge les actor id en fct du movie
     * @param movie film selectionne
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
     * Load Movie info with the public database
     *
     * @return
     */
    public Movie loadMovieDataAutomatic() {
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
                list_movies.add((MovieDb) elem); // On récupère tous les films et le user choisit lequel rajouter        }
            for (var title : list_movies)
                System.out.println(title.getTitle());
            for (var thing : multiList) {
                if (thing.getClass().getName().equals("info.movito.themoviedbapi.model.MovieDb")) {
                    list_movies.add((MovieDb) thing); // On récupère tous les films et le user choisit lequel rajouter

                }
            }
            for (var title : list_movies) {
                System.out.println(title.getTitle());
            }
            // Ajout du Click sur le film pour récupérer le nom du film;
            String chosenMovie = "";
            for (var element : multiList) {
                if (element.getClass().getName().equals("info.movito.themoviedbapi.model.MovieDb")) {
                    if (element.getClass().getName().equals(chosenMovie)) {
                        movie_selected.movieId = sqlTools.GetNbRow("Movie") + 1;
                        movie_selected.title = moviedb.getTitle();
                        movie_selected.recap = moviedb.getOverview();
                        movie_selected.genre = moviedb.getGenres().get(0).getName();
                        movie_selected.releaseDate = java.sql.Date.valueOf(LocalDate.parse(moviedb.getReleaseDate(), DateTimeFormatter.ISO_DATE));
                        movie_selected.ticketPrice = 8;
                        movie_selected.duration = sqlTools.translateTime(moviedb.getRuntime());
                        movie_selected.urlImage = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/" + moviedb.getPosterPath();
                        movie_selected.actorIds = loadactorIds(movie_selected);
                    }
                }
            }
        }
        return movie_selected;
    }
}
