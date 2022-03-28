package com.example.cinema_projet;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Employe extends Application
{
    Stage window;
    BorderPane layout;
    Button butUpdtMovies,butMiseAjourPrix,butPageDacc,butPageDacc2,butPageDacc3,butPageDacc4,butPageDacc5,butDossClients,butPasserEnRevue;
    Scene scene1,scene2,scene3,scene4,scene5;
    Label label1,label2,label3,label4,label5;
    public static void main(String args[])
    {
        launch(args);//We can also write Application.launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        //Create a window
        window=primaryStage;
        window.setTitle("Employés");

        //Buttons
        butPageDacc=new Button("Retour à la page Employés");
        butPageDacc2=new Button("Retour à la page Employés");
        butPageDacc3=new Button("Retour à la page Employés");
        butPageDacc4=new Button("Retour à la page Employés");
        butPageDacc5=new Button("Retour à la page Employés");

        //File Menu
        Menu fileMenu=new Menu("Options");

        //Menu Items
        MenuItem prevueFilms=new MenuItem("Prévue des films populaires");
        MenuItem miseAjourFilm=new MenuItem("Mise à jours des Films");
        MenuItem miseAjourPrix=new MenuItem("Mise à jours des Prix");
        MenuItem accesDossiers=new MenuItem("Accès Dossiers Clients");
        MenuItem ajouterunFilm=new MenuItem("Ajouter un Film");


        prevueFilms.setOnAction(onClick-> window.setScene(scene1));
        miseAjourFilm.setOnAction(onClick->window.setScene(scene2));
        miseAjourPrix.setOnAction(onClick->window.setScene(scene3));
        accesDossiers.setOnAction(onClick->window.setScene(scene4));
        ajouterunFilm.setOnAction(onClick->window.setScene(scene5));

        fileMenu.getItems().add(prevueFilms);
        fileMenu.getItems().add(new SeparatorMenuItem());
        fileMenu.getItems().add(miseAjourFilm);
        fileMenu.getItems().add(miseAjourPrix);
        fileMenu.getItems().add(new SeparatorMenuItem());
        fileMenu.getItems().add(accesDossiers);
        fileMenu.getItems().add(new SeparatorMenuItem());
        fileMenu.getItems().add(ajouterunFilm);


        //Main Menu Bar
        MenuBar menuBar=new MenuBar();
        menuBar.getMenus().addAll(fileMenu);

        //Scene 1 Films Populaires
        VBox layout1 = new VBox(10);
        label1 = new Label("Films Populaires");
        layout1.setAlignment(Pos.BOTTOM_CENTER);
        layout1.setBackground(new Background(new BackgroundFill(Color.CYAN,CornerRadii.EMPTY,Insets.EMPTY)));//To set Colors
        layout1.getChildren().addAll(butPageDacc,label1);
        scene1 = new Scene(layout1,800,600);

        //Scene 2 Mise a jour des films
        VBox layout2=new VBox(10);
        label2 = new Label("Mise à jour des films");
        layout2.setAlignment(Pos.BOTTOM_CENTER);
        layout2.setBackground(new Background(new BackgroundFill(Color.FUCHSIA,CornerRadii.EMPTY,Insets.EMPTY)));
        layout2.getChildren().addAll(label2,butPageDacc2);
        scene2 = new Scene(layout2,800,600);

        //Scene 3 Mise a jour des prix
        VBox layout3 = new VBox(10);
        label3 = new Label("Mise à jour des prix");
        layout3.setAlignment(Pos.BOTTOM_CENTER);
        layout3.setBackground(new Background(new BackgroundFill(Color.FUCHSIA,CornerRadii.EMPTY,Insets.EMPTY)));
        layout3.getChildren().addAll(label3,butPageDacc3);
        scene3 = new Scene(layout3,800,600);

        //Scene 4 Dossier Clients
        VBox layout4 = new VBox(10);
        label4 = new Label("Dossier Clients");
        layout4.setAlignment(Pos.BOTTOM_CENTER);
        layout4.setBackground(new Background(new BackgroundFill(Color.FUCHSIA,CornerRadii.EMPTY,Insets.EMPTY)));
        layout4.getChildren().addAll(label4,butPageDacc4);
        scene4 = new Scene(layout4,800,600);

        //Scene 5 Ajouter un film
        VBox layout5 = new VBox(10);
        label5 = new Label("Ajouter un Film");
        layout5.setAlignment(Pos.BOTTOM_CENTER);
        layout5.setBackground(new Background(new BackgroundFill(Color.FUCHSIA,CornerRadii.EMPTY,Insets.EMPTY)));
        layout5.getChildren().addAll(label5,butPageDacc5);
        scene5= new Scene(layout5,800,600);

        layout=new BorderPane();
        layout.setTop(menuBar);
        Scene scene=new Scene(layout,800,600);
        window.setScene(scene);
        //Return to main Page button
        butPageDacc.setOnAction(onClick->window.setScene(scene));
        butPageDacc2.setOnAction(onClick->window.setScene(scene));
        butPageDacc3.setOnAction(onClick->window.setScene(scene));
        butPageDacc4.setOnAction(onClick->window.setScene(scene));
        butPageDacc5.setOnAction(onClick->window.setScene(scene));
        window.show();
    }
}