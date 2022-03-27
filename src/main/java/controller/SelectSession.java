package controller;

import model.Movie;
import model.Room;
import model.Session;
import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

public class SelectSession
{
    public SQLTools sqlTools = new SQLTools();
    public ResultSet rs;
    public SelectSession(){

    }
//    public boolean getTimeSession(int room, Time time){
//        boolean test=true;
//        ArrayList<Session> datasession = new ArrayList<Session>();
//        ArrayList<Room>dataroom = new ArrayList<>()
//        String query="Select * from Session; ";
//        try{
//            Session s = new Session();
//            rs=sqlTools.executeQueryWithRs(query);
//            while (rs.next()){
//                s.sessionId=rs.getInt(1);
//                s.movieId=rs.getInt(2);
//                s.reservId= rs.getInt(3);
//                s.sessionTime=rs.getTime(4);
//                datasession.add(s);
//            }
//        }
//        catch (SQLException e){
//            System.out.println(e);
//        }
//        query="Select * from Room";
//        try{
//            Room r = new Room();
//            rs=sqlTools.executeQueryWithRs(query);
//            while (rs.next()){
//                r.roomId=rs.getInt(1);
//                r.seatCount=rs.getInt(2);
//                r.sessionId= rs.getInt(3);
//                dataroom.add(r);
//            }
//        }
//        catch (SQLException e){
//            System.out.println(e);
//        }
//        for(int i=0;i<dataroom.size();i++){
//            if(datasession.get(i).sessionTime==time && datasession.get(i).r)
//        }
//
//        return test;
//    }
    public void createSession(){
        int nbSession= (sqlTools.GetNbRow("Session")+1);
        //On suppose qu'on reçoit le array issu de movies déjà chargé de dbRepository
        ArrayList<Movie> movieArrayList = new ArrayList<Movie>();
        int chosenMovie= movieArrayList.get(0).movieId;  // on récup un film( le 1 ici)
        int t=780;  // Le temps selectionné au clic
        try {
            String query = "INSERT INTO Session (session_id, movie_id,reserv_id,session_time) VALUES (?,?,?,?);";
            PreparedStatement stmt = sqlTools.executeQueryWithPS(query);
            stmt.setInt(1,nbSession);
            stmt.setInt(2,chosenMovie);
            Reservation r = new Reservation();
            stmt.setInt(3,r.getReservId());
            stmt.setTime(4,sqlTools.translateTime(t));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}