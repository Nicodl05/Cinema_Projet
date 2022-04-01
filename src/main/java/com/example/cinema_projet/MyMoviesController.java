package com.example.cinema_projet;

import controller.DisplayMovie;
import controller.Profile;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.User;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MyMoviesController implements Initializable {
    @FXML
    AnchorPane pane;

    ArrayList<String> likedMovies = new ArrayList<String>();
    ArrayList<String> loadHistoric = new ArrayList<String>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        DisplayMovie Dmovie = new DisplayMovie();
        Profile profile = new Profile(LoginController.personne);
        likedMovies=Dmovie.load_movies_liked(LoginController.personne);
        loadHistoric=Dmovie.loadHistoric(LoginController.personne);

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
                Label label1 = new Label(loadHistoric.get(i));
                label1.setLayoutX(a);
                label1.setLayoutY(o);
                pane.getChildren().addAll(label1);
            }
        });
        Myliked.setOnAction(ActionEvent -> {
            int a=10;
            int o=50;

            for(int i = 0; i< likedMovies.size();i++){
                ImageView coeur = new ImageView("Coeur.PNG");
                Button button = new Button("",coeur);
                o+=30;
                Label label1 = new Label(likedMovies.get(i));
                label1.setLayoutX(a);
                label1.setLayoutY(o);
                button.setOnAction(ActionEvent2 -> {

                    profile.addToMovieLiked(MoviesController.afficherMovie,LoginController.personne);

                });
                pane.getChildren().addAll(button);
            }

        });

    }
}
