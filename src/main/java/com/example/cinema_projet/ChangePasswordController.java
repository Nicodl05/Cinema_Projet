package com.example.cinema_projet;

import controller.LoginAccountCreate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChangePasswordController implements Initializable {

    @FXML
    private TextField MailTextField;

    @FXML
    private TextField PwdTextField;

    @FXML
    private Button buttonMDP;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        /**
         Appel de resetPassword avec en arguments les valeurs des TextFields
         */
    }
    @FXML
    void NvMdpOnAction(ActionEvent event) throws IOException {

        LoginAccountCreate login = new LoginAccountCreate();
        login.resetPassword(MailTextField.getText(),PwdTextField.getText());

        Stage stage = (Stage) buttonMDP.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
}
