package com.example.cinema_projet;

import controller.SelectSession;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.fxml.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.MovieSession;
import model.Session;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BuyTicketsController implements Initializable {

    @FXML
    private TextField CVVTextField;

    @FXML
    private TextField DateTextField;

    @FXML
    private Label InvalideLabel;

    @FXML
    private TextField NumTextField;

    @FXML
    private Label AurevoirLabel;

    @FXML
    private Button Retour;

    @FXML
    private Button PayerButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
        Fonction pour vérifier la validité de la carte bancaire
     */

    public boolean verifCB(String card, String date, String lasnb ){
        boolean test=false;
        if(card.length()==16 && date.length()==5 && lasnb.length()==3)
            test=true;
        return test;
    }
    /**
        Appel de verifCB et validation de la réservation avec changement de la DB
     */
    @FXML
    void PayerOnAction(ActionEvent event) throws IOException {
        boolean bool = false;
        bool = verifCB(NumTextField.getText(),DateTextField.getText(),CVVTextField.getText());
        if(bool == true)
        {

            SelectSession movieSession = new SelectSession(LoginController.personne);
            movieSession.userSelectedSession(SessionChoiceController.session,MoviesController.afficherMovie.getMovieId(),SessionChoiceController.variable);

            AurevoirLabel.setText("Votre Réservation à été prise en compte, A bientot !");
            PauseTransition pause = new PauseTransition(Duration.seconds(2000));

            Parent fxmlLoader = FXMLLoader.load(getClass().getResource("Movies.fxml"));
            Scene scene = new Scene(fxmlLoader,800,600);
            Stage stage = new Stage();
            //stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();

            stage = (Stage) PayerButton.getScene().getWindow();
            stage.close();

        }
        else if(bool == false){
            InvalideLabel.setText("Saisie Invalide");
        }

    }
    @FXML
    void RetourOnAction(ActionEvent event) throws IOException {

        Parent fxmlLoader = FXMLLoader.load(getClass().getResource("SessionChoice.fxml"));
        Scene scene = new Scene(fxmlLoader,800,600);
        Stage stage = new Stage();
        //stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        stage = (Stage) Retour.getScene().getWindow();
        stage.close();

    }

}
