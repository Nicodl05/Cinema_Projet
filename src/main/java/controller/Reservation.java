package controller;

import model.User;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Reservation {
    public SQLTools sqlTools = new SQLTools();
    User user;

    Reservation() {

    }

    /**
     * Permet de récupérer l'id de la réservation
     *
     * @return le nb de lignes dans reservation +1
     */
    public int getReservId() {
        return (sqlTools.GetNbRow("Reservation") + 1);
    }

    /**
     * Permet de modifier le prix à partir de la date de naissance du user
     *
     * @return un int correspond au prix
     */
    public int newPrice() {

        // On suppose qu'on reçoit en paramètre un user
        int price = 8; // on considère qu'il a été récupéré
        user = new User();
        String query = "Select * from Person where person_id=" + user.getId();
        try {
            sqlTools.setRs(sqlTools.executeQueryWithRs(query));
            Date bday = sqlTools.getRs().getDate("bday");
            if ((2022 - bday.getYear() > 60) || (2022 - bday.getYear() < 25))
                price = 6;
            if (2022 - bday.getYear() < 10)
                price = 4;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return price;
    }

}
