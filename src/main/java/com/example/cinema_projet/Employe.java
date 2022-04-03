package com.example.cinema_projet;

import controller.DbRepository;
import controller.EmpModification;
import controller.SelectSession;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Movie;
import model.User;

import java.io.File;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

    public class Employe extends Application
    {
        EmpModification moviesModif, movieAdded;
        DbRepository recupFilms;//Create an object of the Repository class
        private Stage window;//Create a stage and call it window
        private ComboBox<String>sessionChoisie;
        private BorderPane layout;//Create the window layout
        private Button submitButtonScene2,submitButtonScene3,submitButtonScene7,submitButtonScene8,submitButtonScene9,butPageDaccAjoutFilm,buttPageAccManu,buttPageAccAuto,butPageDacc2,butPageDacc3,butPageDacc4,butPageDacc5,butPageDacc9,buttAjouterUnFilm,buttManuel,buttAuto;//Create buttons
        private ListView nomsFilms,userNames,moviesInfo,sessionMovies;//Create a list view that will contain the array list of the movies in the Repository class
        private ImageView ImageUser;//Image to display the Movie's Image
        private Image userImage;
        private Scene scene2,scene3,scene4,scene5,scene6,scene7,scene8,scene9;//Create different scene
        private Text textScene6,textScene3,textScene8,textscene2,textscene9;
        private TextField titreduFilm,genreduFilm,recapduFilm,releaseDateFilm,durationFilm,prixDuTicketFilm,modifPrix,titreFilmModif,ajouterNouveauTitre,filmId,filmAvailability,movieIDSession,timeSession;
        private String nouveauTitre,nouveauGenre,nouveauRecap,nouveauRelaseDate,nouveauPrix,nouveauDuration,titreFilmaModif;
        private int availability,idMovie,movieIdSession;
        private double newPrice,nouvPrix;
        private Date nouvDate;
        private  Time nouvHeure;

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
      //userImage = new Image("C:\\Users\\johns\\Documents\\GitHub\\Cinema_Projet\\src\\main\\java\\com\\example\\cinema_projet\\user.png");
        //userImage=new Image("C:\\Users\\adrie\\Documents\\GitHub\\Cinema_Projet\\src\\main\\java\\com\\example\\cinema_projet\\user.png");
      //  userImage =new Image("C:\\Users\\Nicolas\\OneDrive - Groupe INSEEC (POCE)\\ING3\\S6\\Java\\Cinema_Projet\\src\\main\\java\\com\\example\\cinema_projet\\user.png"); // C'est mon chemin
        //userImage =new Image("https://github.com/Nicodl05/Cinema_Projet/blob/3719b1875f1f8e0bd93f7fb4d33dc76d9288aa43/src/main/java/com/example/cinema_projet/user.png");
        File file = new File("user.png");
        String abspath=file.getAbsolutePath();
        userImage = new Image(abspath);       //"user.png"
        ImageUser=new ImageView();
        ImageUser.setImage(userImage);

        //Get movies Title
        recupFilms = new DbRepository();
        nomsFilms=new ListView<>();
        sessionMovies = new ListView<>();
        for(int i=0;i<recupFilms.movieArrayList.size();i++)
        {
            nomsFilms.getItems().add(i+1+"- "+recupFilms.movieArrayList.get(i).getTitle());//Fill the list view with the arraylist
            sessionMovies.getItems().addAll(recupFilms.movieArrayList.get(i).getMovieId()+"\t"+recupFilms.movieArrayList.get(i).getTitle());
        }
        nomsFilms.setLayoutX(0);
        nomsFilms.setLayoutY(300);
        nomsFilms.setPrefHeight(600);
        nomsFilms.setPrefWidth(800);

        sessionMovies.setLayoutX(0);
        sessionMovies.setLayoutY(300);
        sessionMovies.setPrefHeight(600);
        sessionMovies.setPrefWidth(800);

        //Load Movies info
        moviesInfo=new ListView<>();
        for(int i=0;i<recupFilms.movieArrayList.size();i++)
        {
            moviesInfo.getItems().addAll(recupFilms.movieArrayList.get(i).getMovieId()+"- "+recupFilms.movieArrayList.get(i).getTitle()+" : "+recupFilms.movieArrayList.get(i).isAvailable());//Fill the list view with the arraylist
        }
        moviesInfo.setLayoutX(0);
        moviesInfo.setLayoutY(300);
        moviesInfo.setPrefHeight(600);
        moviesInfo.setPrefWidth(800);

        //Load users
        userNames = new ListView<>();
        moviesModif = new EmpModification();
        moviesModif.loadUsersData();
        userNames.getItems().addAll("Nom\t\tPrénom\t\tEmail\t\tAnnée de Naissance");
        for(int i=0;i<moviesModif.getDataUser().size();i++)
        {
            userNames.getItems().addAll(moviesModif.getDataUser().get(i).getLastName()+"\t\t"+moviesModif.getDataUser().get(i).getFirstName()+"\t\t"+moviesModif.getDataUser().get(i).getEmail()+"\t\t"+moviesModif.getDataUser().get(i).getBday());//Fill the list view with the arraylist
        }
        userNames.setLayoutX(0);
        userNames.setLayoutY(300);
        userNames.setPrefHeight(600);
        userNames.setPrefWidth(800);

        //Create a ComboBox for the Session
        sessionChoisie = new ComboBox<>();
        sessionChoisie.getItems().addAll("9","12","15","18","21");
        sessionChoisie.setPromptText("Choisir une session");
        sessionChoisie.setFocusTraversable(false);
        sessionChoisie.setLayoutX(400);
        sessionChoisie.setLayoutY(200);
        

        //Add a movie
        titreduFilm = new TextField();
        genreduFilm = new TextField();
        recapduFilm = new TextField();
        releaseDateFilm = new TextField();
        prixDuTicketFilm = new TextField();
        durationFilm = new TextField();
        ajouterNouveauTitre = new TextField();//Add automatically movie
        filmAvailability = new TextField();
        filmId = new TextField();
        movieIDSession = new TextField();

        titreduFilm.setPromptText("Titre du film");
        titreduFilm.setFocusTraversable(false);
        genreduFilm.setPromptText("Genre du Film");
        genreduFilm.setFocusTraversable(false);
        recapduFilm.setPromptText("Recap du Film");
        recapduFilm.setFocusTraversable(false);
        releaseDateFilm.setPromptText("Date: yyyy-mm-dd");
        releaseDateFilm.setFocusTraversable(false);
        prixDuTicketFilm.setPromptText("Prix du billet: 0.00");
        prixDuTicketFilm.setFocusTraversable(false);
        durationFilm.setPromptText("Durée du Film: hh:mm:ss");
        durationFilm.setFocusTraversable(false);
        filmId.setPromptText("Chosir l'ID: ");
        filmId.setFocusTraversable(false);
        filmAvailability.setPromptText("Disponible: 1 /Non Disponible: 0");
        filmAvailability.setFocusTraversable(false);
        movieIDSession.setPromptText("ID du Film");
        movieIDSession.setFocusTraversable(false);

        titreduFilm.setLayoutX(30);
        titreduFilm.setLayoutY(20);
        genreduFilm.setLayoutX(30);
        genreduFilm.setLayoutY(50);
        recapduFilm.setLayoutX(30);
        recapduFilm.setLayoutY(80);
        releaseDateFilm.setLayoutX(30);
        releaseDateFilm.setLayoutY(110);
        prixDuTicketFilm.setLayoutX(30);
        prixDuTicketFilm.setLayoutY(140);
        durationFilm.setLayoutX(30);
        durationFilm.setLayoutY(170);
        ajouterNouveauTitre.setLayoutX(325);
        ajouterNouveauTitre.setLayoutY(300);
        filmId.setLayoutX(200);
        filmId.setLayoutY(200);
        filmAvailability.setLayoutX(400);
        filmAvailability.setLayoutY(200);
        filmAvailability.setMinWidth(200);
        movieIDSession.setLayoutX(200);
        movieIDSession.setLayoutY(200);
        

        //Price modification
        modifPrix = new TextField();
        titreFilmModif = new TextField();
        titreFilmModif.setPromptText("Titre du Film...");
        modifPrix.setPromptText("Nouveau Prix:0.00");
        modifPrix.setFocusTraversable(false);
        titreFilmModif.setFocusTraversable(false);
        titreFilmModif.setLayoutX(325);
        titreFilmModif.setLayoutY(170);
        modifPrix.setLayoutX(325);
        modifPrix.setLayoutY(220);

        //Buttons
        butPageDaccAjoutFilm = new Button("Retour à la page Employés");//Return to the main page
        buttPageAccManu = new Button("Retour à la page Employés");
        buttPageAccAuto = new Button("Retour à la page Employés");
        butPageDacc2 = new Button("Retour à la page Employés");
        butPageDacc3 = new Button("Retour à la page Employés");
        butPageDacc4 = new Button("Retour à la page Employés");
        butPageDacc5 = new Button("Retour à la page Employés");
        butPageDacc9 = new Button("Retour à la page Employés");
        buttAjouterUnFilm = new Button("Ajouter un Film");
        buttAuto = new Button("Automatiquement");//Add automatically a movie
        buttManuel = new Button("Manuellement");//Add manually a movie
        submitButtonScene3 = new Button("Submit");
        submitButtonScene2 = new Button("Submit");
        submitButtonScene7 = new Button("Submit");
        submitButtonScene8 = new Button("Submit");
        submitButtonScene9 = new Button("Submit");

        buttManuel.setLayoutX(300);
        buttAuto.setLayoutX(400);
        buttManuel.setLayoutY(400);
        buttAuto.setLayoutY(400);
        buttAjouterUnFilm.setLayoutX(345);
        buttAjouterUnFilm.setLayoutY(30);
        butPageDacc3.setLayoutX(325);
        butPageDacc3.setLayoutY(550);
        butPageDacc5.setLayoutX(315);
        butPageDacc5.setLayoutY(150);
        butPageDacc4.setLayoutX(315);
        butPageDacc4.setLayoutY(150);
        butPageDacc2.setLayoutX(200);
        butPageDacc2.setLayoutY(250);
        butPageDacc9.setLayoutX(315);
        butPageDacc9.setLayoutY(250);
        butPageDaccAjoutFilm.setLayoutX(325);
        butPageDaccAjoutFilm.setLayoutY(550);
        buttPageAccManu.setLayoutX(325);
        buttPageAccManu.setLayoutY(550);
        buttPageAccAuto.setLayoutX(325);
        buttPageAccAuto.setLayoutY(550);
        submitButtonScene3.setLayoutX(400);
        submitButtonScene3.setLayoutY(250);
        submitButtonScene2.setLayoutX(400);
        submitButtonScene2.setLayoutY(250);
        submitButtonScene7.setLayoutX(400);
        submitButtonScene7.setLayoutY(450);
        submitButtonScene8.setLayoutX(400);
        submitButtonScene8.setLayoutY(350);
        submitButtonScene9.setLayoutX(600);
        submitButtonScene9.setLayoutY(250);

        //File Menu
        Menu fileMenu=new Menu("Options");

        //Menu Items
        MenuItem miseAjourFilm=new MenuItem("Mise à jours des Films");
        MenuItem miseAjourPrix=new MenuItem("Mise à jours des Prix");
        MenuItem accesDossiers=new MenuItem("Accès Dossiers Clients");
        MenuItem ajouterunFilm=new MenuItem("Ajouter un Film");
        MenuItem ajouterUneSession = new MenuItem("Ajouter une Session");
        MenuItem returnMainMenu = new MenuItem("Retour à la page principale");

        //What happens the button is pressed
        miseAjourFilm.setOnAction(onClick->window.setScene(scene2));
        miseAjourPrix.setOnAction(onClick->window.setScene(scene3));
        accesDossiers.setOnAction(onClick->window.setScene(scene4));
        ajouterunFilm.setOnAction(onClick->window.setScene(scene5));
        ajouterUneSession.setOnAction(onClick->window.setScene(scene9));
        buttAjouterUnFilm.setOnAction(onClick->window.setScene(scene6));
        buttAuto.setOnAction(onClick-> window.setScene(scene8));
        buttManuel.setOnAction(onClick->window.setScene(scene7));
        submitButtonScene7.setOnAction(onClick->{
            nouveauTitre=titreduFilm.getText();
            System.out.println(nouveauTitre);
            nouveauGenre=genreduFilm.getText();
            System.out.println(nouveauGenre);
            nouveauRecap=recapduFilm.getText();
            System.out.println(nouveauRecap);
            nouveauRelaseDate=releaseDateFilm.getText();
            System.out.println(nouveauRelaseDate);
            nouveauPrix=prixDuTicketFilm.getText();
            System.out.println(nouveauPrix);
            nouveauDuration=durationFilm.getText();
            System.out.println(nouveauDuration);
            nouvDate = Date.valueOf(nouveauRelaseDate);
            nouvPrix=Double.parseDouble(nouveauPrix);
            nouvHeure= Time.valueOf(nouveauDuration);
            movieAdded=new EmpModification();
            movieAdded.addMovie(moviesModif.addMovieDataManual(-1,true,nouveauTitre,nouveauGenre,nouveauRecap,(Date) nouvDate,(double) nouvPrix,(Time) nouvHeure));
        });
        submitButtonScene3.setOnAction(conCick->{
            titreFilmaModif=titreFilmModif.getText();
            System.out.println(titreFilmaModif);
            newPrice= Double.valueOf(modifPrix.getText());
            System.out.println(newPrice);
            moviesModif.updateMoviePrice(titreFilmaModif,newPrice);
        });
        submitButtonScene2.setOnAction(onClick->{
            availability = Integer.valueOf(filmAvailability.getText());
            idMovie= Integer.valueOf(filmId.getText());
            moviesModif.update_movie_status(availability,idMovie);
        });
        submitButtonScene8.setOnAction(onClick->{
            movieAdded=new EmpModification();
            movieAdded.addMovie(moviesModif.addMovieDataAutomatic(ajouterNouveauTitre.getText()));
        });
        submitButtonScene9.setOnAction(onClick->{
            movieIdSession = Integer.valueOf(movieIDSession.getText());
            ArrayList<Integer>lie= new ArrayList<>();
            User user = new User(10,"","","","", null,false,lie);
            SelectSession session = new SelectSession(user);
            int sessionTime=Integer.valueOf(sessionChoisie.getValue());
            sessionTime*=60;
            session.createSession(movieIdSession,sessionTime);
        });

        //The display of the Items
        fileMenu.getItems().add(miseAjourFilm);
        fileMenu.getItems().add(miseAjourPrix);
        fileMenu.getItems().add(new SeparatorMenuItem());
        fileMenu.getItems().add(accesDossiers);
        fileMenu.getItems().add(new SeparatorMenuItem());
        fileMenu.getItems().add(ajouterunFilm);
        fileMenu.getItems().add(ajouterUneSession);
        fileMenu.getItems().add(new SeparatorMenuItem());
        fileMenu.getItems().add(returnMainMenu);


        //Main Menu Bar
        MenuBar menuBar=new MenuBar();
        menuBar.getMenus().addAll(fileMenu);


        //Scene 2 Mise a jour des films
        AnchorPane layout2 = new AnchorPane();
        textscene2= new Text("Veuillez mettre à jour les films...");
        textscene2.setX(150);
        textscene2.setY(70);
        textscene2.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        layout2.setBackground(new Background(new BackgroundFill(Color.DARKCYAN,CornerRadii.EMPTY,Insets.EMPTY)));
        layout2.getChildren().addAll(butPageDacc2,moviesInfo,textscene2,filmId,filmAvailability,submitButtonScene2);
        scene2 = new Scene(layout2,800,600);

        //Scene 3 Mise a jour des prix
        textScene3= new Text("Veuillez Saisir les Informations demandées...");
        textScene3.setX(150);
        textScene3.setY(70);
        textScene3.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        AnchorPane layout3 = new AnchorPane();
        layout3.setBackground(new Background(new BackgroundFill(Color.DARKCYAN,CornerRadii.EMPTY,Insets.EMPTY)));
        layout3.getChildren().addAll(textScene3,titreFilmModif,modifPrix,butPageDacc3,submitButtonScene3);
        scene3 = new Scene(layout3,800,600);

        //Scene 4 Dossier Clients
        AnchorPane layout4 = new AnchorPane();
        layout4.setBackground(new Background(new BackgroundFill(Color.DARKCYAN,CornerRadii.EMPTY,Insets.EMPTY)));
        layout4.getChildren().addAll(butPageDacc4,userNames);
        scene4 = new Scene(layout4,800,600);

        //Scene 5 Ajouter un film
        AnchorPane layout5 = new AnchorPane();
        scene5= new Scene(layout5,800,600);
        layout5.setBackground(new Background(new BackgroundFill(Color.DARKCYAN,CornerRadii.EMPTY,Insets.EMPTY)));
        layout5.getChildren().addAll(buttAjouterUnFilm,butPageDacc5,nomsFilms);



        //Scene 6 Auto or Manu?
        textScene6= new Text("Ajouter un film manuellement ou automatiquement?");
        textScene6.setX(150);
        textScene6.setY(70);
        textScene6.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        AnchorPane layout6 = new AnchorPane(textScene6);
        layout6.setBackground(new Background(new BackgroundFill(Color.DARKCYAN,CornerRadii.EMPTY,Insets.EMPTY)));
        layout6.getChildren().addAll(buttAuto,buttManuel,butPageDaccAjoutFilm);
        scene6=new Scene(layout6,800,600);

        //Scene 7 Add a movie Manually
        AnchorPane layout7 = new AnchorPane();
        layout7.setBackground(new Background(new BackgroundFill(Color.DARKCYAN,CornerRadii.EMPTY,Insets.EMPTY)));
        layout7.getChildren().addAll(titreduFilm,genreduFilm,recapduFilm,releaseDateFilm,prixDuTicketFilm,durationFilm,buttPageAccManu,submitButtonScene7);
        scene7 = new Scene(layout7,800,600);

        //Scene 8 Add a movie Automatically
        textScene8= new Text("Veuillez saisir le titre du Film...");
        textScene8.setX(150);
        textScene8.setY(70);
        textScene8.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        AnchorPane layout8 = new AnchorPane();
        layout8.setBackground(new Background(new BackgroundFill(Color.DARKCYAN,CornerRadii.EMPTY,Insets.EMPTY)));
        layout8.getChildren().addAll(ajouterNouveauTitre,buttPageAccAuto,textScene8,submitButtonScene8);
        scene8 = new Scene(layout8,800,600);

        //Scene 9 Ajouter une session
        AnchorPane layout9 = new AnchorPane();
        textscene9= new Text("Veuillez choisir l'ID du Film...");
        textscene9.setX(150);
        textscene9.setY(70);
        textscene9.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        layout9.setBackground(new Background(new BackgroundFill(Color.DARKCYAN,CornerRadii.EMPTY,Insets.EMPTY)));
        layout9.getChildren().addAll(butPageDacc9,textscene9,sessionMovies,movieIDSession,sessionChoisie,submitButtonScene9);
        scene9 = new Scene(layout9,800,600);

        //Create a layout
        layout=new BorderPane(ImageUser);
        layout.setTop(menuBar);
        layout.setBackground(new Background(new BackgroundFill(Color.DARKCYAN,CornerRadii.EMPTY,Insets.EMPTY)));
        Scene scene=new Scene(layout,800,600);
        window.setScene(scene);

        //Return to main Page button
        butPageDaccAjoutFilm.setOnAction(onClick->window.setScene(scene));
        buttPageAccManu.setOnAction(onClick->window.setScene(scene));
        buttPageAccAuto.setOnAction(onClick->window.setScene(scene));
        butPageDacc2.setOnAction(onClick->window.setScene(scene));
        butPageDacc3.setOnAction(onClick->window.setScene(scene));
        butPageDacc4.setOnAction(onClick->window.setScene(scene));
        butPageDacc5.setOnAction(onClick->window.setScene(scene));
        butPageDacc9.setOnAction(onClick->window.setScene(scene));

        window.show();
    }
}