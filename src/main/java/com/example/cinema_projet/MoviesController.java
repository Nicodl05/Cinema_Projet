package com.example.cinema_projet;

import controller.DbRepository;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MoviesController implements Initializable {

    @FXML
    private AnchorPane pane;

    private ArrayList<Button> ButtonList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        DbRepository info = new DbRepository();

        Stage stage = new Stage();

        int a = 100;
        int b = 100;
    for(int i=0;i<info.movieArrayList.size();i++) {

        String urlImage = info.movieArrayList.get(i).urlImage;
        ImageView view = new ImageView(urlImage);

        Button button1 = new Button("", view);
        button1.setMaxHeight(50);

            a += 200;
            b += 100;
            if (i % 3 == 0) {
                a = 100;
            }

        button1.setLayoutX(a);
        button1.setLayoutY(b);
        //pane =new AnchorPane();
        pane.getChildren().add(button1);
    }

        System.out.println("affichage");


        Scene scene = new Scene(pane, 600, 600);
        stage.setScene(scene);
        stage.show();

    }


/*
        FXMLLoader fxmlLoader = new FXMLLoader(MoviesController.class.getResource("Movies.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 595, 170);
        Stage stage = new Stage();

        stage.setTitle("Button Graphics");
        stage.setScene(scene);
        stage.show();

        //CreateAllButtonToDisplay();
        //call new method to get all Image url
        //GetAllImageUrl();

 */
public void Change() {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
            "Movies.fxml"));
    fxmlLoader.setRoot(this);
    fxmlLoader.setController(this);

    try {
        fxmlLoader.load();
    } catch (IOException exception) {
        throw new RuntimeException(exception);
    }
}


    public void CreateAllButtonToDisplay() {

        int a = 100;
        int b = 100;
        for (int i = 0; i < ButtonList.size(); i++) {

            a += 100;
            b += 100;
            if (i % 3 == 0) {
                a = 100;
            }
            ButtonList.get(i).setLayoutX(a);
            ButtonList.get(i).setLayoutY(b);


        }

        //  ButtonList.get(1).setOnAction();


    }

    public void CreateAButton(String ImageUrl) {

        ImageView view = new ImageView(ImageUrl);
        Button button1 = new Button("", view);

        ButtonList.add(button1);
    }

    public void FindAMovie(String textfield) {


    }
}
