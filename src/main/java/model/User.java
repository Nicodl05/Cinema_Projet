package model;

import java.util.ArrayList;
import java.util.Date;


//Model
public class User {

    public int id;
    String firstName, lastName, email, passwd;
    public Date bday;
    boolean isEmployee = false;
    ArrayList<Integer> likedMovieIds;

    User(){

    }


}
