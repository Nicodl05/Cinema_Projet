package model;

import java.util.ArrayList;
import java.util.Date;


//Model
public class User {

    public int id;
   public  String firstName, lastName, email, passwd;
    public Date bday;
   public  boolean isEmployee = false;
    public ArrayList<Integer> likedMovieIds;

    public User(){

    }


}
