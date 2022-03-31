package com.example.cinema_projet;

import controller.DbRepository;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.text.Text;

public class Employe extends Application
{
    DbRepository recupFilms;//Create an object of the Repository class
    Stage window;//Create a stage and call it window
    BorderPane layout;//Create the window layout
    Button butPageDacc,butPageDacc2,butPageDacc3,butPageDacc4,butPageDacc5,buttAjouterUnFilm,buttManuel,buttAuto;//Create buttons
    ListView nomsFilms;//Create a list view that will contain the array list of the movies in the Repository class
    ImageView ImageUser;//Image to display the Movie's Image
    Image userImage;
    Scene scene1,scene2,scene3,scene4,scene5,scene6;//Create different scene
    Label label1,label2,label3,label4;//Create label in case needed
    Text textScene6;

    public static void main(String args[])
    {
        launch(args);//We can also write Application.launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        //Create a window
        window=primaryStage;
        window.setTitle("Employés");//Set the Window title

        //Image of the main menu
        userImage=new Image("C:\\Users\\johns\\Documents\\GitHub\\Cinema_Projet\\src\\main\\java\\com\\example\\cinema_projet\\user.png");
        ImageUser=new ImageView();
        ImageUser.setImage(userImage);

        //Get movies Title
        recupFilms = new DbRepository();
        nomsFilms=new ListView<>();
        for(int i=0;i<recupFilms.movieArrayList.size();i++)
        {
            nomsFilms.getItems().add(i+1+"- "+recupFilms.movieArrayList.get(i).getTitle());//Fill the list view with the arraylist
        }

        //Get the movies image
        /*imageDisp = new ImageView();
        String movie1;
        movie1=recupFilms.movieArrayList.get(0).getUrlImage();
        movieImage=new Image(movie1);
        imageDisp.setImage(movieImage);
        imageDisp.setX(800);
        imageDisp.setY(100);
        imageDisp.setFitHeight(200);
        imageDisp.setFitWidth(300);
        imageDisp.setPreserveRatio(true);*/

        //Buttons
        butPageDacc=new Button("Retour à la page Employés");//Return to the main page
        butPageDacc2=new Button("Retour à la page Employés");
        butPageDacc3=new Button("Retour à la page Employés");
        butPageDacc4=new Button("Retour à la page Employés");
        butPageDacc5=new Button("Retour à la page Employés");
        buttAjouterUnFilm=new Button("Ajouter un Film");
        buttAuto=new Button("Automatiquement");//Add automatically a movie
        buttManuel=new Button("Manuellement");//Add manually a movie
        buttManuel.setLayoutX(300);
        buttAuto.setLayoutX(400);
        buttManuel.setLayoutY(400);
        buttAuto.setLayoutY(400);
        nomsFilms.setLayoutX(0);
        nomsFilms.setLayoutY(300);
        nomsFilms.setPrefHeight(600);
        nomsFilms.setPrefWidth(800);
        buttAjouterUnFilm.setLayoutX(320);
        butPageDacc5.setLayoutX(290);
        butPageDacc5.setLayoutY(35);

        //File Menu
        Menu fileMenu=new Menu("Options");

        //Menu Items
        MenuItem prevueFilms=new MenuItem("Prévue des films populaires");
        MenuItem miseAjourFilm=new MenuItem("Mise à jours des Films");
        MenuItem miseAjourPrix=new MenuItem("Mise à jours des Prix");
        MenuItem accesDossiers=new MenuItem("Accès Dossiers Clients");
        MenuItem ajouterunFilm=new MenuItem("Ajouter un Film");
        //MenuItem ajouterunFilm2=new MenuItem("Ajouter un Film");

        //What happens the button is pressed
        prevueFilms.setOnAction(onClick-> window.setScene(scene1));
        miseAjourFilm.setOnAction(onClick->window.setScene(scene2));
        miseAjourPrix.setOnAction(onClick->window.setScene(scene3));
        accesDossiers.setOnAction(onClick->window.setScene(scene4));
        ajouterunFilm.setOnAction(onClick->window.setScene(scene5));
        buttAjouterUnFilm.setOnAction(onClick->window.setScene(scene6));
        buttAuto.setOnAction(onClick-> System.out.println("Automatiquement"));
        buttManuel.setOnAction(onClick-> System.out.println("Manuellement"));

        //The display of the Items
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
        layout1.setBackground(new Background(new BackgroundFill(Color.DARKCYAN,CornerRadii.EMPTY,Insets.EMPTY)));//To set Colors
        layout1.getChildren().addAll(butPageDacc,label1);
        scene1 = new Scene(layout1,800,600);

        //Scene 2 Mise a jour des films
        VBox layout2=new VBox(10);
        label2 = new Label("Mise à jour des films");
        layout2.setAlignment(Pos.BOTTOM_CENTER);
        layout2.setBackground(new Background(new BackgroundFill(Color.DARKCYAN,CornerRadii.EMPTY,Insets.EMPTY)));
        layout2.getChildren().addAll(butPageDacc2,label2);
        scene2 = new Scene(layout2,800,600);

        //Scene 3 Mise a jour des prix
        VBox layout3 = new VBox(10);
        label3 = new Label("Mise à jour des prix");
        layout3.setAlignment(Pos.BOTTOM_CENTER);
        layout3.setBackground(new Background(new BackgroundFill(Color.DARKCYAN,CornerRadii.EMPTY,Insets.EMPTY)));
        layout3.getChildren().addAll(butPageDacc3,label3);
        scene3 = new Scene(layout3,800,600);

        //Scene 4 Dossier Clients
        VBox layout4 = new VBox(10);
        label4 = new Label("Dossier Clients");
        layout4.setAlignment(Pos.BOTTOM_CENTER);
        layout4.setBackground(new Background(new BackgroundFill(Color.DARKCYAN,CornerRadii.EMPTY,Insets.EMPTY)));
        layout4.getChildren().addAll(butPageDacc4,label4);
        scene4 = new Scene(layout4,800,600);

        //Scene 5 Ajouter un film
        AnchorPane layout5 = new AnchorPane();
        /*buttAjouterUnFilm.setLayoutX(200);
        butPageDacc5.setLayoutX(300);*/
        scene5= new Scene(layout5,800,600);
        layout5.setBackground(new Background(new BackgroundFill(Color.DARKCYAN,CornerRadii.EMPTY,Insets.EMPTY)));
        layout5.getChildren().addAll(buttAjouterUnFilm,butPageDacc5,nomsFilms);



        //Scene 6 where the Employee chooses to enter a new movie
        textScene6= new Text("Ajouter un film manuellement ou automatiquement?");
        textScene6.setX(250);
        textScene6.setY(70);
        AnchorPane layout6 = new AnchorPane(textScene6);
        layout6.setBackground(new Background(new BackgroundFill(Color.DARKCYAN,CornerRadii.EMPTY,Insets.EMPTY)));
        layout6.getChildren().addAll(buttAuto,buttManuel);
        scene6=new Scene(layout6,800,600);

        //Create a layout
        layout=new BorderPane(ImageUser);
        layout.setTop(menuBar);
        layout.setBackground(new Background(new BackgroundFill(Color.DARKCYAN,CornerRadii.EMPTY,Insets.EMPTY)));
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