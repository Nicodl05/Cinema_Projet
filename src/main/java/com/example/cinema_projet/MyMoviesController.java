package com.example.cinema_projet;

import controller.DisplayMovie;
import controller.Profile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Movie;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MyMoviesController implements Initializable {
    @FXML
    AnchorPane pane;

    @FXML
    private Button retour;

    ArrayList<String> likedMovies = new ArrayList<String>();
    ArrayList<Movie> loadHistoric = new ArrayList<Movie>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        DisplayMovie Dmovie = new DisplayMovie();
        Profile profile = new Profile(LoginController.personne);
        likedMovies=Dmovie.load_movies_liked(LoginController.personne);
        loadHistoric = profile.loadHistoric();

        MenuBar menuBar=new MenuBar();
        Menu fileMenu=new Menu("Mes Films");

        MenuItem MyHisto = new MenuItem("Mon historique");
        fileMenu.getItems().add(MyHisto);
        fileMenu.getItems().add(new SeparatorMenuItem());
        MenuItem Myliked = new MenuItem("Mes Films Préférés");
        fileMenu.getItems().add(Myliked);

        menuBar.getMenus().addAll(fileMenu);
        pane.getChildren().add(menuBar);


        MyHisto.setOnAction(ActionEvent -> {
            int a=10;
            int o=50;

            for(int i = 0; i< loadHistoric.size();i++){

                o+=30;
                Label label1 = new Label(loadHistoric.get(i).getTitle());
                label1.setLayoutX(a);
                label1.setLayoutY(o);
                pane.getChildren().addAll(label1);
                ImageView coeur = new ImageView("C:\\Users\\adrie\\Documents\\GitHub\\Cinema_Projet\\src\\main\\resources\\com\\example\\cinema_projet\\Coeur.png");
                coeur.setFitHeight(40);
                coeur.setFitWidth(40);
                Button button = new Button("",coeur);
                button.setPrefSize(30, 30);
                button.setOnAction(ActionEvent2 -> {

                    profile.addToMovieLiked(MoviesController.afficherMovie,LoginController.personne);

                });
                pane.getChildren().addAll(button);
            }
        });
        Myliked.setOnAction(ActionEvent -> {
            int a=10;
            int o=50;

            for(int i = 0; i< likedMovies.size();i++){

                o+=30;
                Label label1 = new Label(likedMovies.get(i));
                label1.setLayoutX(a);
                label1.setLayoutY(o);


            }

        });
    }
    @FXML
    void retournOnAction(ActionEvent event) throws IOException {

        Parent fxmlLoader = FXMLLoader.load(getClass().getResource("Movies.fxml"));
        Scene scene = new Scene(fxmlLoader,800,600);
        Stage stage = new Stage();
        //stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        stage = (Stage) retour.getScene().getWindow();
        stage.close();

    }

}
