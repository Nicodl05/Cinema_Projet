package com.example.cinema_projet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class WebSiteController {

    @FXML
    private Button CreateAccountButton;

    @FXML
    private Button InviterButton;

    @FXML
    private Button LoginButton;

    @FXML
    void InviterOnAction(ActionEvent event) throws IOException {

        Parent fxmlLoader = FXMLLoader.load(getClass().getResource("Movies.fxml"));
        Scene scene = new Scene(fxmlLoader,800,600);
        Stage stage = new Stage();
        //stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        stage = (Stage) InviterButton.getScene().getWindow();
        stage.close();


    }
    @FXML
    void LoginOnAction(ActionEvent event) throws IOException {

        Parent fxmlLoader = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader,800,600);
        Stage stage = new Stage();
        //stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        stage = (Stage) LoginButton.getScene().getWindow();
        stage.close();

    }

    @FXML
    void CreateAccountButtonOnAction(ActionEvent event) throws IOException {

        Parent fxmlLoader = FXMLLoader.load(getClass().getResource("CreateAccount.fxml"));
        Scene scene = new Scene(fxmlLoader,800,600);
        Stage stage = new Stage();
        //stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        stage = (Stage) CreateAccountButton.getScene().getWindow();
        stage.close();

    }
}
