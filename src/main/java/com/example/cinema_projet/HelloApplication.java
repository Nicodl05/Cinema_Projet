package com.example.cinema_projet;

import controller.DbRepository;
import controller.EmpModification;
import controller.LoginAccountCreate;
import controller.SQLTools;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args)  {
//        LoginAccountCreate cr = new LoginAccountCreate();
//        cr.login("nicolas.dreyfus@outlook.fr","cecile05!");

        //launch(args);
//        DbRepository dbRepository = new DbRepository();
//       dbRepository.addMovie();
//            LoginAccountCreate loginAccountCreate = new LoginAccountCreate();
//            loginAccountCreate.resetPassword("arthur.fournier@gmail.com");
        EmpModification empModification = new EmpModification();
        empModification.update_movie_status(0,1);
    }


}