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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Movie;
import model.User;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MyMoviesController implements Initializable {
    @FXML
    AnchorPane pane;

    @FXML
    private AnchorPane pane2;

    @FXML
    private Button retour;

    @FXML
    private Label infoLabel;

    ArrayList<String> likedMovies = new ArrayList<String>();
    ArrayList<Movie> loadHistoric = new ArrayList<Movie>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        DisplayMovie Dmovie = new DisplayMovie();
        Profile profile = new Profile(LoginController.personne);

        loadHistoric = profile.loadHistoric();

        MenuBar menuBar=new MenuBar();
        Menu fileMenu=new Menu("Mes Films");

        MenuItem MyHisto = new MenuItem("Mon historique");
        fileMenu.getItems().add(MyHisto);
        fileMenu.getItems().add(new SeparatorMenuItem());
        MenuItem Myliked = new MenuItem("Mes Films Préférés");
        fileMenu.getItems().add(Myliked);
        fileMenu.getItems().add(new SeparatorMenuItem());
        MenuItem informations= new MenuItem("Mes données");
        fileMenu.getItems().add(informations);


        menuBar.getMenus().addAll(fileMenu);
        pane.getChildren().add(menuBar);
        /**
         Lors du clique sur historique on affiche l'historique du client en mettant le titre des films dans des labels
         avec la possibilité de liker le film déjà vu.
         */

        MyHisto.setOnAction(ActionEvent -> {

            int a=10;
            int o=50;
            pane2.getChildren().clear();
            infoLabel.setText("Mon Historique");
            pane2.getChildren().addAll(infoLabel);
            for(int i = 0; i< loadHistoric.size();i++){

                o+=30;
                Label label1 = new Label(loadHistoric.get(i).getTitle());
                label1.setLayoutX(a);
                label1.setLayoutY(o+10);
                pane2.getChildren().addAll(label1);

                ImageView coeur;
                //= new ImageView("C:\\Users\\adrie\\Documents\\GitHub\\Cinema_Projet\\src\\main\\resources\\com\\example\\cinema_projet\\Coeur.png");
                File file = new File("Coeur.png");
                Image image = new Image(file.toURI().toString());
                coeur= new ImageView(image);
                coeur.setFitHeight(20);
                coeur.setFitWidth(20);
                Button button = new Button("",coeur);
                button.setPrefSize(10, 10);
                button.setLayoutX(150);
                button.setLayoutY(o);
                int k=i;
                button.setOnAction(ActionEvent2 -> {

                    profile.addToMovieLiked(loadHistoric.get(k),LoginController.personne);

                });
                pane2.getChildren().addAll(button);
            }
        });
        /**
         Lors du clique sur mes films pref on affiche les films pref du client en mettant le titre des films dans des labels
         */
        Myliked.setOnAction(ActionEvent -> {
            pane2.getChildren().clear();
            pane2.getChildren().addAll(infoLabel);
            infoLabel.setText("Mes Films Préférés");
            int a=10;
            int o=50;
            likedMovies=Dmovie.load_movies_liked(LoginController.personne);
            for(int i = 0; i< likedMovies.size();i++){

                System.out.println(likedMovies.get(i));
                o+=30;
                Label label1 = new Label(likedMovies.get(i));
                label1.setLayoutX(a);
                label1.setLayoutY(o);
                pane2.getChildren().addAll(label1);
            }

        });
        /**
         Lors du clique on informations on permet à l'utilisateur de modifier ses informations, comme son nom
         ou encore l'adresse mail
         */
        informations.setOnAction(ActionEvent -> {
            pane2.getChildren().clear();
            pane2.getChildren().addAll(infoLabel);
            infoLabel.setText("Mes données");

            Label labelattribute = new Label("Choisissez un attribut");
            Label labelnewinfo= new Label("Choisissez une nouvelle valeur");

            labelattribute.setLayoutX(10);
            labelattribute.setLayoutY(100);
            labelnewinfo.setLayoutX(10);
            labelnewinfo.setLayoutY(130);

            TextField attribute = new TextField("");
            TextField newinfo = new TextField("");

            attribute.setLayoutX(190);
            attribute.setLayoutY(95);

            newinfo.setLayoutX(190);
            newinfo.setLayoutY(125);

            Button button = new Button("Modifier");

            button.setLayoutX(190);
            button.setLayoutY(170);

            pane2.getChildren().addAll(labelattribute,labelnewinfo,attribute,newinfo,button);

            button.setOnAction(ActionEvent2 -> {
                profile.modifyInfo(attribute.getText(),newinfo.getText());
            });

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
