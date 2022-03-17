package model;

import java.sql.Time;
import java.util.Date;
import java.util.*;


// Model
public class Movie {
    public int movieId;
    public boolean isAvailable;
    public String title, genre, recap, trailer, urlImage;
    public Date releaseDate;//= new Date(2000,12,10);
    public double ticketPrice;
    public Time duration;//= new Time(02,32,00);
    public ArrayList<String> actorIds;

    /**
     * Constructeur par défaut
     */
    public Movie(){

    }
    public Movie(String movietitle){
        title=movietitle;
    }
}
