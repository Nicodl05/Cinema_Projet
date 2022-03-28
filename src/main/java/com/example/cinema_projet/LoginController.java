package com.example.cinema_projet;
import controller.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.sql.*;


public class LoginController {

    @FXML
    private TextField MyMail;

    @FXML
    private TextField MyPassword;

    @FXML
    private Label MailLabel;

    @FXML
    private Label PasswordLabel;

    public void ConnOnAction() {
        LoginAccountCreate login = new LoginAccountCreate();
        login.login(MyMail.getText(),MyPassword.getText());
    }

    public void Connected() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Movies.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);

        MoviesController  controller = new MoviesController();
        controller =fxmlLoader.getController();

        Stage stage = new Stage();
        stage.setTitle("okrgr");
        stage.setScene(scene);
        stage.show();
        
    }



}