package com.example.cinema_projet;

import controller.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Movie;
import model.User;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @author Nicolas Dreyfus--Laquièze
 * @author Adrien Leboeuf
 * @author John Samaha
 * @author Groupe 10 TD 9
 */
public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        scene.getStylesheets().add(getClass().getResource("Affichage.css").toExternalForm());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args)  {
        /*
        DbRepository dbRepository= new DbRepository();

        Movie d = dbRepository.getInfoMovieBasedTitle("Le Monde de Narnia : Le Prince caspian");
        System.out.println(d.getRecap());

         */

      // launch(args);
      //  EmpModification empModification = new EmpModification();
        //empModification.addMovie();
        SendMail s = new SendMail();
        s.Emailsent();
    }
}