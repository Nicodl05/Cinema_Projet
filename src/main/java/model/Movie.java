package model;

import java.sql.Blob;
import java.sql.Time;
import java.util.Date;
import java.util.*;


// Model
public class Movie {
    private int movieId;
    private boolean isAvailable;
    private String title, genre, recap, trailer, urlImage;
    private Date releaseDate;//= new Date(2000,12,10);
    private double ticketPrice;
    private Time duration;//= new Time(02,32,00);
    //public ArrayList<Integer> actorIds;


    /**
     * Constructeur par défaut
     */
    public Movie(){

    }

    // Following
    // Getter et setter des attributs de notre classe
    public Movie(String movietitle){
        title=movietitle;
    }

    public void setDuration(Time duration) { this.duration = duration;  }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public void setRecap(String recap) {
        this.recap = recap;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public Time getDuration() {
        return duration;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public String getTrailer() {
        return trailer;
    }

    public String getRecap() {
        return recap;
    }

    public String getGenre() {
        return genre;
    }

    public String getTitle() {
        return title;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public int getMovieId() {
        return movieId;
    }
}
