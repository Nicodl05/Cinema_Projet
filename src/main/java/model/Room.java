package model;

import java.util.ArrayList;

public class Room {
    public int roomId, seatCount;
    public int sessionId;

   public  Room() {

    }

   public Room(int _roomId, int _seats, int _sessionId) {
        roomId = _roomId;
        seatCount = _seats;
        sessionId=_sessionId;
    }

}
