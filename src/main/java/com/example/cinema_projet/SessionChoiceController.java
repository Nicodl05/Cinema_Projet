package com.example.cinema_projet;

import controller.DisplayMovie;
import controller.SelectSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SessionChoiceController implements Initializable {

    @FXML
    private AnchorPane panee;

    @FXML
    private ComboBox<String> DateComboBox;

    @FXML
    private TextField NbBilletsTextfield;

    @FXML
    private Button ReserverButton;

    @FXML
    private ComboBox<String> SessionComboBox;

    @FXML
    private Label TitreLabel;

    @FXML
    private TextField PrixTextfield;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        TitreLabel.setText(MoviesController.afficherMovie.getTitle());
        DateComboBox.setValue("Sessions");
        DateComboBox.getItems().addAll("Test", "Test2", "Test3");
        //int price = (int)MoviesController.afficherMovie.getTicketPrice()*(int)NbBilletsTextfield.getText();
        //PrixTextfield.setText(price);


    }

    @FXML
    public void ReserverOnAction(ActionEvent event) throws IOException {

        DisplayMovie select = new DisplayMovie();

        select.add_to_Historic(LoginController.personne,MoviesController.afficherMovie);

        /*

        Parent fxmlLoader = FXMLLoader.load(getClass().getResource("BuyTickets.fxml"));
        Scene scene = new Scene(fxmlLoader,800,600);
        Stage stage = new Stage();
        //stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

         */

    }


}
