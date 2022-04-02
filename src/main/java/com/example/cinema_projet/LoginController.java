package com.example.cinema_projet;
import controller.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;


public class LoginController implements Initializable {

    @FXML
    private TextField MyMail;

    @FXML
    private TextField MyPassword;

    @FXML
    public Label MailLabel;

    @FXML
    public Label PasswordLabel;

    public static User personne= new User();

    public LoginController(){

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
    public void ConnOnAction() {
        LoginAccountCreate login = new LoginAccountCreate();
        personne = login.login(MyMail.getText(), MyPassword.getText());

        if(Objects.equals(personne.getLastName(), "wrong email")) {
            MailLabel.setText("Mauvais mail");
            PasswordLabel.setText("");

        }
        if(Objects.equals(personne.getLastName(), "wrong password")) {
            PasswordLabel.setText("Mauvais Password");
            MailLabel.setText("");
        }
        if(Objects.equals(personne.getLastName(), "wrong password and email")) {
            MailLabel.setText("Mauvais mail");
            PasswordLabel.setText("Mauvais Password");
        }

    }

    public void Connected() throws IOException {


        Parent fxmlLoader = FXMLLoader.load(getClass().getResource("Movies.fxml"));
        Scene scene = new Scene(fxmlLoader, 800, 600);
        Stage stage = new Stage();
        //stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

    }

    public void ForgetPassword(ActionEvent event) throws IOException {

        Parent fxmlLoader = FXMLLoader.load(getClass().getResource("ChangePassword.fxml"));
        Scene scene = new Scene(fxmlLoader, 800, 600);
        Stage stage = new Stage();
        //stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

    }


}

