package com.example.cinema_projet;

import controller.DbRepository;
import controller.Profile;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Movie;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class MoviesController implements Initializable {

    @FXML
    private Button buttontest;

    @FXML
    private AnchorPane pane;

    @FXML
    private AnchorPane pane2;

    @FXML
    private TextField choix;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Button Employee;

    @FXML
    private Button MyMovies;

    @FXML
    private MenuItem SeDeco;


    @FXML
    private MenuItem BecomeEmp;

    Stage stage;
    Button button1;
    public static Movie afficherMovie = new Movie();
    public ArrayList<String> genre = new ArrayList<String>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        //pane = new AnchorPane();
        stage = new Stage();

        MenuBar menuBar=new MenuBar();
        Menu fileMenu=new Menu("Films");
        DbRepository info = new DbRepository();
        int z=0;
        genre.add("test");
        for(int i=0;i<info.movieArrayList.size();i++)
        {
            z=0;
            for(int n=0;n<genre.size();n++) {

                if (Objects.equals(info.movieArrayList.get(i).getGenre(), genre.get(n))) {
                    //System.out.println("dedans");
                    z++;
                    break;

                }
            }
            if(z==0){
                genre.add(info.movieArrayList.get(i).getGenre());
                MenuItem prevueFilms = new MenuItem(info.movieArrayList.get(i).getGenre());
                fileMenu.getItems().add(prevueFilms);
                fileMenu.getItems().add(new SeparatorMenuItem());

                prevueFilms.setOnAction(ActionEvent-> {
                    int compteur = 0;
                    pane2.getChildren().clear();
                    int a = 10;
                    int b = 50;
                    for (int p = 0; p < info.movieArrayList.size(); p++) {
                        System.out.println(info.movieArrayList.get(p).getGenre());

                        if (Objects.equals(info.movieArrayList.get(p).getGenre(), prevueFilms.getText())) {

                            String urlImage = info.movieArrayList.get(p).getUrlImage();
                            ImageView view = new ImageView(urlImage);
                            view.setFitHeight(220);
                            view.setFitWidth(220);

                            Button button1 = new Button(info.movieArrayList.get(p).getTitle(), view);
                            button1.setStyle("-fx-background-color: black");

                            button1.setOnAction(ActionEvent2 -> {

                                for(int o=0; o < info.movieArrayList.size();o++)
                                {
                                    if(Objects.equals(info.movieArrayList.get(o).getTitle(), button1.getText()))
                                    {
                                        afficherMovie = info.movieArrayList.get(o);
                                        System.out.println(afficherMovie.getTitle());
                                    }
                                }


                                try {
                                    DataTheMovie();
                                    stage = (Stage) button1.getScene().getWindow();
                                    stage.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            });

                            if (compteur != 0 && compteur % 3 == 0) {
                                a = 10;
                                b += 240;

                            }
                            compteur++;
                            button1.setPrefSize(210, 210);
                            button1.setLayoutX(a);
                            button1.setLayoutY(b);

                            pane2.getChildren().addAll(button1);
                            a += 250;
                        }

                    }

                });

            }
            }

        menuBar.getMenus().addAll(fileMenu);
        pane.getChildren().add(menuBar);

        InitialSetup();
    }
    @FXML
    public void ActualiseScene(ActionEvent event){

        int compteur=0;
        DbRepository info = new DbRepository();
        //stage = new Stage();

        if(choix.getText().isBlank())
        {
            InitialSetup();
        }
        else{
            pane2.getChildren().clear();
            int a = 10;
            int b = 50;
            for(int i=0;i<info.movieArrayList.size();i++) {


                if(Objects.equals(info.movieArrayList.get(i).getTitle(), choix.getText())){

                    String urlImage = info.movieArrayList.get(i).getUrlImage();
                    ImageView view = new ImageView(urlImage);
                    view.setFitHeight(220);
                    view.setFitWidth(220);

                    Button button1 = new Button(info.movieArrayList.get(i).getTitle(), view);
                    button1.setStyle("-fx-background-color: black");
                    button1.setOnAction(ActionEvent-> {
                        for(int o=0; o < info.movieArrayList.size();o++)
                        {
                            if(Objects.equals(info.movieArrayList.get(o).getTitle(), button1.getText()))
                            {
                                afficherMovie = info.movieArrayList.get(o);
                                System.out.println(afficherMovie.getTitle());
                            }
                        }


                        try {
                            DataTheMovie();
                            stage = (Stage) button1.getScene().getWindow();
                            stage.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    });

                    if (compteur != 0 && compteur%3 == 0) {
                        a = 10;
                        b += 240;

                    }
                    compteur++;
                    button1.setPrefSize(210, 210);
                    button1.setLayoutX(a);
                    button1.setLayoutY(b);

                    pane2.getChildren().addAll(button1);
                    a += 250;
                }

            }

        }

    }
    public void InitialSetup(){

        DbRepository info = new DbRepository();
        int a = 10;
        int b = 50;
        for(int i=0;i<info.movieArrayList.size();i++) {

            String urlImage = info.movieArrayList.get(i).getUrlImage();
            ImageView view = new ImageView(urlImage);
            view.setFitHeight(220);
            view.setFitWidth(220);

            Button button1 = new Button(info.movieArrayList.get(i).getTitle(), view);

            button1.setStyle("-fx-background-color:  black");


            button1.setOnAction(ActionEvent-> {
                for(int o=0; o < info.movieArrayList.size();o++)
                {
                    if(Objects.equals(info.movieArrayList.get(o).getTitle(), button1.getText()))
                    {
                        afficherMovie = info.movieArrayList.get(o);
                        System.out.println(afficherMovie.getTitle());
                    }
                }
                try {
                    DataTheMovie();
                    stage = (Stage) button1.getScene().getWindow();
                    stage.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });

            if (i != 0 && i % 3 == 0) {
                a = 10;
                b += 240;
            }
            button1.setPrefSize(210, 210);
            button1.setLayoutX(a);
            button1.setLayoutY(b);

            pane2.getChildren().add(button1);
            a += 250;

        }

    }
    public void DataTheMovie() throws IOException {

            Parent fxmlLoader = FXMLLoader.load(getClass().getResource("DataMovie.fxml"));
            Scene scene = new Scene(fxmlLoader);
            Stage stage = new Stage();
            //stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();

        }
    @FXML
    void GoToEmployee(ActionEvent event) throws Exception {

        Employe object = new Employe();
        if(LoginController.personne.isEmployee()==true)
        {
            object.start(stage);

            stage = (Stage) Employee.getScene().getWindow();
            stage.close();
        }
        else
            System.out.println("Pas employé");
    }
    @FXML
    void ShowMyMovies(ActionEvent event) throws IOException {


        Parent fxmlLoader = FXMLLoader.load(getClass().getResource("MyMovies.fxml"));
        Scene scene = new Scene(fxmlLoader,800,600);
        Stage stage = new Stage();
        //stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        stage = (Stage) MyMovies.getScene().getWindow();
        stage.close();

    }


    @FXML
    void SeDecoOnAction(ActionEvent event) throws IOException {

        Platform.exit();

    }


    @FXML
    void BecomeEmpOnAction(ActionEvent event) {
        Profile profile = new Profile(LoginController.personne);
        profile.become_Emp();

    }

        }
