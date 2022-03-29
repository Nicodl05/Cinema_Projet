package model;

import java.sql.Time;

public class MovieSession {
    private int sessionId, movieId, usedSeatCount;
    private Time sessionTIme;

   public MovieSession() {
    }

    public Time getSessionTIme() {
        return sessionTIme;
    }

    public void setSessionTIme(Time sessionTIme) {
        this.sessionTIme = sessionTIme;
    }

    public int getUsedSeatCount() {
        return usedSeatCount;
    }

    public void setUsedSeatCount(int usedSeatCount) {
        this.usedSeatCount = usedSeatCount;
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
