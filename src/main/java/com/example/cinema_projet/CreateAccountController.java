package com.example.cinema_projet;

import controller.LoginAccountCreate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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

    public void CreateButtononAction(){
        LoginAccountCreate login = new LoginAccountCreate();
        login.Create_Account(FirstName.getText(),Name.getText(),Mail.getText(),Password.getText(),BirthDate.getText());
    }
}
