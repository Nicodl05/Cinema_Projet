package model;

import java.util.ArrayList;
import java.util.Date;


//Model
public class User {

    private int id;
    private String firstName, lastName, email, passwd;
    private Date bday;
    private boolean isEmployee = false;
    private ArrayList<Integer> likedMovieIds;

    //Constructor
    public User() {

    }

    public User(int id, String firstName, String lastName, String email, String passwd, Date bday, boolean isEmployee, ArrayList<Integer> likedMovieIds) {
        this.id = id;
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
        this.passwd=passwd;
        this.bday=bday;
        this.isEmployee=isEmployee;
        this.likedMovieIds=likedMovieIds;
    }

    // Following
    // Getter et setter des attributs de notre classe
    public ArrayList<Integer> getLikedMovieIds() {
        return likedMovieIds;
    }

    public void setLikedMovieIds(ArrayList<Integer> likedMovieIds) {
        this.likedMovieIds = likedMovieIds;
    }

    public boolean isEmployee() {
        return isEmployee;
    }

    public void setEmployee(boolean employee) {
        isEmployee = employee;
    }

    public Date getBday() {
        return bday;
    }

    public void setBday(Date bday) {
        this.bday = bday;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
