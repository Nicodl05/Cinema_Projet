package com.example.cinema_projet;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

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
    public void CreateAllButtonToDisplay(String ImageUrl){

        ImageView view = new ImageView(ImageUrl);
        Button button1 = new Button("",view);

        ButtonList.add(button1);

      //  ButtonList.get(1).setOnAction();



    }
    
    public void CreateAButton(){


    }
}
