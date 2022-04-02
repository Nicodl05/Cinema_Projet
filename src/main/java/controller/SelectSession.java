package controller;

import model.*;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class SelectSession {
    private final SQLTools sqlTools = new SQLTools();
    User user;

    /**
     * Constructeur
     * @param user1
     */
    public SelectSession(User user1) {

        user = user1;
    }

    /**
     * Permet de créer une session et de l'insérer dans la db
     */
    public void createSession(int chosenMovie,int t) {
        int nbSession = (sqlTools.GetNbRow("Session") + 1);
        //On suppose qu'on reçoit le array issu de movies déjà chargé de dbRepository
        ArrayList<Movie> movieArrayList = new ArrayList<Movie>();
        try {
            String query = "INSERT INTO Session (session_id, movie_id,reserv_id,session_time) VALUES (?,?,?,?);";
            sqlTools.setStmt(sqlTools.executeQueryWithPS(query));
            sqlTools.getStmt().setInt(1, nbSession);
            sqlTools.getStmt().setInt(2, chosenMovie);
            Reservation r = new Reservation();
            sqlTools.getStmt().setInt(3, r.getReservId());
            sqlTools.getStmt().setTime(4, sqlTools.translateTime(t));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Permet de rajouter dans la table historique, le film visionné par un user
     * @param movie
     */
    public void addToHistoric(Movie movie) {
        Date date = new Date();
        String query = "Insert into Historic (id_user,id_movie,last_viewed) Values (?,?,?);";
        try {
             sqlTools.setStmt(sqlTools.executeQueryWithPS(query));
            sqlTools.getStmt().setInt(1, user.getId());
            sqlTools.getStmt().setInt(2, movie.getMovieId());
            sqlTools.getStmt().setDate(3, (java.sql.Date) date);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /**
     * Permet de faire une modification dans la session quand une réservation a été faite
     * @param session correspond à la session pour la table reservation
     * @param movieSession correspond à la session du film
     */
    public void userSelectedSession(Session session, MovieSession movieSession) {
        if (movieSession.getSessionId() == session.getSessionId()) {
            int reservId = sqlTools.GetNbRow("Reservation");
            String query = "Insert into Reservation (reserv_id, user_id, movie_id, session_id) Values (?,?,?,?);";
            try {
                sqlTools.setStmt( sqlTools.executeQueryWithPS(query));
                sqlTools.getStmt().setInt(1, reservId);
                sqlTools.getStmt().setInt(2, user.getId());
                sqlTools.getStmt().setInt(3, movieSession.getMovieId());
                sqlTools.getStmt().setInt(4, session.getSessionId());
            } catch (SQLException e) {
                System.out.println(e);
            }
            int originalSeats = -1;
            query = "Select seats from Room where session_id=" + session.getSessionId();
            try {
                sqlTools.setRs(sqlTools.executeQueryWithRs(query));
                while (sqlTools.getRs().next()) {
                    originalSeats = sqlTools.getRs().getInt("seats");
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
            query = "Update Room Set seats=" + (originalSeats - 1) + " where session_id=" + session.getSessionId();
            sqlTools.setRs(sqlTools.executeQueryWithRs(query));
        } else
            System.out.println("Erreur de session");
    }

}
