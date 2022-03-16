package com.example.cinema_projet;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.TmdbSearch;
import info.movito.themoviedbapi.model.Genre;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.Multi;


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    static void test_api() {
        String query = "Star Wars ";
        TmdbApi api = new TmdbApi("810c86d39163e1219bbe9a906af41da0");  // apic créee
        TmdbSearch search = new TmdbSearch(api); // objet recherche
        TmdbSearch.MultiListResultsPage resultsPage = search.searchMulti(query, "fr", 1);
        List<Multi> multiList = resultsPage.getResults();
        ArrayList<String> list_movie = new ArrayList<String>();
        ArrayList<Integer> id_movies = new ArrayList<Integer>();
        ArrayList<Genre> genres = new ArrayList<Genre>();
        String title, genre, release_time, r_time, recap;
        for (var elem : multiList) {
            if (elem.getClass().getName().equals("info.movito.themoviedbapi.model.MovieDb")) {

                MovieDb movie = (MovieDb) elem;
                title = movie.getTitle();

                list_movie.add(movie.getTitle());
                id_movies.add(movie.getId());
                // genres.add(movieDb.getGenres());
                // String url = "https://image.tmdb.org/t/p/w600_and_h900_bestv2%22"+movieDb.getPosterPath();
                // TmdbMovies movies = new TmdbApi("810c86d39163e1219bbe9a906af41da0").getMovies();
                //MovieDb movie = movies.getMovie(5353, "fr");
                //System.out.println(movie.getTitle());
                //System.out.println(movie.getGenres());
            }
        }
        for (var elem : list_movie)
            System.out.println(elem);
    }

    public static void main(String[] args) {
        test_api();
    }
}