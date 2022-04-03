package com.example.cinema_projet;

import controller.DisplayMovie;
import controller.Reservation;
import controller.SQLTools;
import controller.SelectSession;
import javafx.collections.ObservableList;
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
import model.Session;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SessionChoiceController implements Initializable {

    @FXML
    private AnchorPane panee;

    @FXML
    private ComboBox<String> ComboBox;

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

    @FXML
    private Button Retour;

    double price=0;

    public static Session session;

    ArrayList<String > sessionmovie = new ArrayList<>();

    public SQLTools tools = new SQLTools();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        TitreLabel.setText(MoviesController.afficherMovie.getTitle());
        SelectSession session = new SelectSession(LoginController.personne);
        sessionmovie = session.getSessionBasedOn(MoviesController.afficherMovie);
        //DateComboBox.setValue("Sessions");


        for(int i=0;i<sessionmovie.size();i++){
            ComboBox.getItems().addAll(sessionmovie.get(i));
        }


    }
    @FXML
    public void Calcul(ActionEvent event){

        if(LoginController.personne.getFirstName() == null)
        {
            price = Double.parseDouble(NbBilletsTextfield.getText());
            PrixTextfield.setText(String.valueOf(MoviesController.afficherMovie.getTicketPrice()*price));
        }
        else{
            price = Double.parseDouble(NbBilletsTextfield.getText());
            PrixTextfield.setText(String.valueOf(MoviesController.afficherMovie.getTicketPrice()*(price*0.8)));

        }

    }

    @FXML
    public void ReserverOnAction(ActionEvent event) throws IOException {

/*
        int truc = Integer.valueOf(String.valueOf(ComboBox.getItems()));
        session.setSessionTime(tools.translateTime(truc));


 */
        //session=new Session(, )

        DisplayMovie select = new DisplayMovie();

        select.add_to_Historic(LoginController.personne,MoviesController.afficherMovie);

        Parent fxmlLoader = FXMLLoader.load(getClass().getResource("BuyTickets.fxml"));
        Scene scene = new Scene(fxmlLoader,600,400);
        Stage stage = new Stage();
        //stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        stage = (Stage) ReserverButton.getScene().getWindow();
        stage.close();

    }
    @FXML
    void RetourOnAction(ActionEvent event) throws IOException {

        Parent fxmlLoader = FXMLLoader.load(getClass().getResource("DataMovie.fxml"));
        Scene scene = new Scene(fxmlLoader,800,600);
        Stage stage = new Stage();
        //stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        stage = (Stage) Retour.getScene().getWindow();
        stage.close();

    }


}
