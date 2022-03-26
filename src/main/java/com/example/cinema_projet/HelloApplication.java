package com.example.cinema_projet;

import controller.LoginAccountCreate;
import controller.SQLTools;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import view.*;





public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        LoginAccountCreate cr = new LoginAccountCreate();
        cr.login("nicolas.dreyfus@outlook.fr","cecile05!");

        //launch(args);
        //System.out.println("Hello world");
//        ArrayList<String> ta = new ArrayList<String>();
//        String query = "Select * from Movies";
//        try{
//            SQLTools t = new SQLTools();
//            ResultSet rs = t.executeQueryWithRs(query);
//            while(rs.next()) {
//                ta.add(rs.getString("title"));
//            }
//
//        }
//        catch (SQLException e){
//            System.out.println(e);
//        }
//        for(var title: ta)
//            System.out.println(title);






    }


}