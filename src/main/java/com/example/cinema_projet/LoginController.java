package com.example.cinema_projet;
import controller.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
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

    public static User personne= new User();

    public void ConnOnAction() {
        LoginAccountCreate login = new LoginAccountCreate();
        login.login(MyMail.getText(),MyPassword.getText());
        try {
            Connected();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Connected() throws IOException {


            Parent fxmlLoader = FXMLLoader.load(getClass().getResource("Movies.fxml"));
            Scene scene = new Scene(fxmlLoader,800,600);
            Stage stage = new Stage();
            //stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();

        }

    }

