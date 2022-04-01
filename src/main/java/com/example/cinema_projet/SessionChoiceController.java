package com.example.cinema_projet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class SessionChoiceController implements Initializable {

    @FXML
    private AnchorPane pane;

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        TitreLabel.setText(MoviesController.afficherMovie.getTitle());
        DateComboBox.setValue("Test");
        DateComboBox.getItems().addAll("Test", "Test2", "Test3");

    }

    @FXML
    void ReserverOnAction(ActionEvent event) {

    }
}
