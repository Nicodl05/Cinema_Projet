package com.example.cinema_projet;

import controller.LoginAccountCreate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateAccountController {

    @FXML
    private TextField BirthDate;

    @FXML
    private Button CreateButton;

    @FXML
    private TextField FirstName;

    @FXML
    private TextField Mail;

    @FXML
    private TextField Name;

    @FXML
    private TextField Password;

    /**
     Appel de Create_Account avec en arguments les valeurs des TextFields
     */
    public void CreateButtononAction() throws IOException {
        LoginAccountCreate login = new LoginAccountCreate();
        login.Create_Account(FirstName.getText(),Name.getText(),Mail.getText(),Password.getText(),BirthDate.getText());

        Parent fxmlLoader = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader,800,600);
        Stage stage = new Stage();
        //stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        stage = (Stage) CreateButton.getScene().getWindow();
        stage.close();
    }
}
