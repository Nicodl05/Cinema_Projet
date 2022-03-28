package com.example.cinema_projet;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MoviesController implements Initializable {

    @FXML
    private Button button;

    private ArrayList<Button> ButtonList;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //call new method to get all Image url
        //GetAllImageUrl();

    }
    public void CreateAllButtonToDisplay(){

        int a =100;
        int b = 100;
        for(int i=0;i<ButtonList.size();i++){

            a +=100;
            b +=100;
            if(i%3==0){
                a =100;
            }
            ButtonList.get(i).setLayoutX(a);
            ButtonList.get(i).setLayoutY(b);

        }

    }
    public void CreateAButton(String ImageUrl){

        ImageView view = new ImageView(ImageUrl);
        Button button1 = new Button("",view);

        ButtonList.add(button1);
    }

    public void FindAMovie(String textfield){


    }
}
