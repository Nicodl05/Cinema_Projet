package model;

import java.util.ArrayList;

public class Room {
    private int roomId, seatCount;
    private int sessionId;

   public  Room() {

    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public Room(int _roomId, int _seats, int _sessionId) {
        roomId = _roomId;
        seatCount = _seats;
        sessionId=_sessionId;
    }

}
