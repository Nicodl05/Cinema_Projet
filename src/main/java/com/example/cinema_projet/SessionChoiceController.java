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
import model.Movie;
import model.MovieSession;
import model.Session;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Objects;
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

    public static int variable=0;

    public static MovieSession movieSession;

    public static ArrayList<Session> sessionmovie = new ArrayList<>();

    public SQLTools tools = new SQLTools();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        TitreLabel.setText(MoviesController.afficherMovie.getTitle());
        SelectSession selectSession = new SelectSession(LoginController.personne);
        sessionmovie = selectSession.getSessionDb(MoviesController.afficherMovie);
        //System.out.println(sessionmovie.get(0).getSessionId());



        for(int i=0;i<sessionmovie.size();i++){
            ComboBox.getItems().addAll(String.valueOf(sessionmovie.get(i).getSessionTime()));
        }

    }
    /**
        On calcul ici le prix que le client va payer en fonction du prix du billet et de son statut sur le site
     */
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
    /**
        La session est initialisé avec l'heure choisit dans la comboBox, tout comme le film et on ajoute le film à l'historique
     */
    @FXML
    public void ReserverOnAction(ActionEvent event) throws IOException {


        for(int i=0;i<sessionmovie.size();i++){
            String passage = String.valueOf(sessionmovie.get(i).getSessionTime());

            if(Objects.equals("["+passage+"]",String.valueOf(ComboBox.getItems()))){
                System.out.println("dedans");
                session = new Session(sessionmovie.get(i).getSessionId(),sessionmovie.get(i).getMovieId(),sessionmovie.get(i).getReservId(),sessionmovie.get(i).getSessionTime());
                System.out.println(sessionmovie.get(i).getSessionId());
                System.out.println(session.getSessionId());
                break;
            }
        }

        variable = Integer.valueOf(String.valueOf(NbBilletsTextfield.getText()));

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
