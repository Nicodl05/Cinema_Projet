package model;

import java.sql.Time;

public class Session {
    private int sessionId, movieId, reservId;
    private Time sessionTime;
    //Constructor
    public Session(){

    }

    public Session(int sessionId,int movieId,int reservId,Time sessionTime) {
        this.sessionId = sessionId;
        this.movieId = movieId;
        this.reservId = reservId;
        this.sessionTime = sessionTime;
    }
    // Following
    // Getter et setter des attributs de notre classe
    public Time getSessionTime() {
        return sessionTime;
    }

    public void setSessionTime(Time sessionTime) {
        this.sessionTime = sessionTime;
    }

    public int getReservId() {
        return reservId;
    }

    public void setReservId(int reservId) {
        this.reservId = reservId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

}
