package com.example.cinema_projet;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import model.Movie;


import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class DataController implements Initializable{

    @FXML
    private TextField DateTextField;

    @FXML
    private ImageView FilmImageView;

    @FXML
    private TextField GenreTextField;

    @FXML
    private TextField PrixTextField;

    @FXML
    private Button ReserveButton;

    @FXML
    private TextArea ResumeTextArea;

    @FXML
    private TextField TitleTextField;

    @FXML
    private Button TrailerButton;

    @FXML
    private AnchorPane pane;


    Stage stage2;
    AnchorPane plan;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        TitleTextField.setText(MoviesController.afficherMovie.getTitle());
        ResumeTextArea.setText(MoviesController.afficherMovie.getRecap());
        PrixTextField.setText(String.valueOf(MoviesController.afficherMovie.getTicketPrice()));
        DateTextField.setText(String.valueOf(MoviesController.afficherMovie.getReleaseDate()));
        String urlImage = MoviesController.afficherMovie.getUrlImage();
        ImageView view = new ImageView(urlImage);
        view.setFitHeight(260);
        view.setFitWidth(260);
        view.setX(50);
        view.setY(100);
        pane.getChildren().addAll(view);
        //FilmImageView = new ImageView(MoviesController.afficherMovie.urlImage);

        //FilmImageView.setImage(Image.fromPlatformImage(MoviesController.afficherMovie.urlImage));
        GenreTextField.setText(MoviesController.afficherMovie.getGenre());


    }
    @FXML
    public void TrailerOnAction(ActionEvent event){
/*
        WebView webView = new WebView();

        webView.getEngine().load(MoviesController.afficherMovie.trailer);
        Vbox vbox = Vbox(webView);
        //Scene scene = new Scene(vbox,800,600);



 */
    }
    @FXML
    public void ReserveButtonOnAction(ActionEvent event) throws IOException {

        Parent fxmlLoader = FXMLLoader.load(getClass().getResource("SessionChoice.fxml"));
        Scene scene = new Scene(fxmlLoader,800,600);
        Stage stage = new Stage();
        //stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

    }


}
