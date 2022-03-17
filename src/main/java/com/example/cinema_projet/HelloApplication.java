package com.example.cinema_projet;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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



    public static void main(String[] args) {
      //  test_api();
        String query="Titanic";
        TmdbApi api = new TmdbApi("810c86d39163e1219bbe9a906af41da0");  // apic créee
        TmdbSearch search =new TmdbSearch(api); // objet recherche
        TmdbSearch.MultiListResultsPage resultsPage= search.searchMulti(query,"fr",1);
        List<Multi> multiList =resultsPage.getResults();
        ArrayList<String> list_movie=new ArrayList<String>();
        ArrayList<Integer>id_movies=new ArrayList<Integer>();
        ArrayList<List<Genre>> genres= new ArrayList<List<Genre>>();
        int id=0;
        for(var elem: multiList){
            if(elem.getClass().getName().equals("info.movito.themoviedbapi.model.MovieDb")){

                /*System.out.println(movie.getTitle());*/
                // System.out.println(movie.getGenres());
                MovieDb movie=(MovieDb)  elem;
                id=movie.getId();


                // Récupérer la date de release
                String date=(movie.getReleaseDate());
                Date d =java.sql.Date.valueOf(LocalDate.parse(date, DateTimeFormatter.ISO_DATE));
                //System.out.println(d);

            String truc = movie.getBackdropPath();
                System.out.println(truc);





                id_movies.add(movie.getId());
                //System.out.println(movie.getVideos().get(0).getName());
                //String machin=movie.getVideos().get(0).getName();
                // System.out.println(machin);
                // genres.add(movieDb.getGenres());
                // String url = "https://image.tmdb.org/t/p/w600_and_h900_bestv2%22"+movieDb.getPosterPath();*/


            }
        }
    }
}