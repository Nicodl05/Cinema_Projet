package model;

import java.util.ArrayList;

public class Room {
    public int roomId, seatCount;
    public ArrayList<Integer> sessionIds;

   public  Room() {

    }

   public Room(int _roomId, int _seats, ArrayList<Integer> _sessionId) {
        roomId = _roomId;
        seatCount = _seats;
       for(var session: _sessionId)
           sessionIds.add(session);
    }

}
