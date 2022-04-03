package controller;

import model.*;


import javax.print.DocFlavor;
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
     *
     * @param user1
     */
    public SelectSession(User user1) {

        user = user1;
    }

    /**
     * Permet de créer une session et de l'insérer dans la db
     */
    public void createSession(int chosenMovie, int t) {
        int nbSession = (sqlTools.GetNbRow("Session") + 1);
        //On suppose qu'on reçoit le array issu de movies déjà chargé de dbRepository
        ArrayList<Movie> movieArrayList = new ArrayList<Movie>();
       // if (isSessionPossible) {
            try {
                String query = "INSERT INTO Session (session_id, movie_id,reserv_id,session_time) VALUES (?,?,?,?);";
                sqlTools.setStmt(sqlTools.executeQueryWithPS(query));
                sqlTools.getStmt().setInt(1, nbSession);
                sqlTools.getStmt().setInt(2, chosenMovie);
                Reservation r = new Reservation();
                sqlTools.getStmt().setInt(3, r.getReservId());
                sqlTools.getStmt().setTime(4, sqlTools.translateTime(t));
                sqlTools.getStmt().execute();
            } catch (SQLException e) {
                //e.printStackTrace();
                System.out.println(e);
            }
       /* } else {
            int nbRoom = (sqlTools.GetNbRow("Room") + 1);
            try {
                String query = "INSERT INTO Room (room_id, seats,session_id) VALUES (?,?,?);";
                sqlTools.setStmt(sqlTools.executeQueryWithPS(query));
                sqlTools.getStmt().setInt(1, nbRoom);
                sqlTools.getStmt().setInt(2, 100);
                Reservation r = new Reservation();
                sqlTools.getStmt().setInt(3, nbSession);

            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                String query = "INSERT INTO Session (session_id, movie_id,reserv_id,session_time) VALUES (?,?,?,?);";
                sqlTools.setStmt(sqlTools.executeQueryWithPS(query));
                sqlTools.getStmt().setInt(1, nbSession);
                sqlTools.getStmt().setInt(2, chosenMovie);
                Reservation r = new Reservation();
                sqlTools.getStmt().setInt(3, r.getReservId());
                sqlTools.getStmt().setTime(4, sqlTools.translateTime(t));
            } catch (SQLException E) {
                E.printStackTrace();
            }
        }*/
    }
    public ArrayList<String> getSessionBasedOn(Movie movie){
        String query = "Select * from Session where movie_id="+movie.getMovieId();
        ArrayList<String > sessionmovie = new ArrayList<>();
        try{
            sqlTools.setRs(sqlTools.executeQueryWithRs(query));
            while(sqlTools.getRs().next()){
                sessionmovie.add(String.valueOf(sqlTools.getRs().getTime("session_time")));
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }
        return sessionmovie;
    }

    public ArrayList<Room> getRoomDb() {
        ArrayList<Room> listRoom = new ArrayList<>();
        String query = "Select * from Room";
        try {
            sqlTools.setRs(sqlTools.executeQueryWithRs(query));
            while ((sqlTools.getRs()).next()) {
                Room room = new Room(sqlTools.getRs().getInt("room_id"), sqlTools.getRs().getInt("seats"), sqlTools.getRs().getInt("session_id"));
                listRoom.add(room);
            }
        } catch (SQLException E) {
            E.printStackTrace();
        }
        return listRoom;
    }

    public ArrayList<Session> getSessionDb(Movie movie) {
        ArrayList<Session> listSession = new ArrayList<>();
        String query = "Select * from Session where movie_id ="+movie.getMovieId();
        try {
            sqlTools.setRs(sqlTools.executeQueryWithRs(query));
            while ((sqlTools.getRs()).next()) {
                Session session = new Session(sqlTools.getRs().getInt("session_id"), sqlTools.getRs().getInt("movie_id"), sqlTools.getRs().getInt("reserv_id"), sqlTools.getRs().getTime("session_time"));
                listSession.add(session);
                System.out.println(session.getSessionId()+" "+ session.getMovieId() +" "+ session.getReservId());
            }

        } catch (SQLException E) {
            E.printStackTrace();
        }
        return listSession;
    }

    /**
     * Permet de rajouter dans la table historique, le film visionné par un user
     *
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
     *
     * @param session      correspond à la session pour la table reservation
     * @param movieSession correspond à la session du film
     */
    public void userSelectedSession(Session session, MovieSession movieSession,int nb_tickets) {
        if (movieSession.getSessionId() == session.getSessionId()) {
            int reservId = sqlTools.GetNbRow("Reservation");
            String query = "Insert into Reservation (reserv_id, user_id, movie_id, session_id) Values (?,?,?,?);";
            try {
                sqlTools.setStmt(sqlTools.executeQueryWithPS(query));
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
            query = "Update Room Set seats=" + (originalSeats - nb_tickets) + " where session_id=" + session.getSessionId();
            sqlTools.setRs(sqlTools.executeQueryWithRs(query));
        } else
            System.out.println("Erreur de session");
    }

}
