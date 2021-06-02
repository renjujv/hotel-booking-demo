package com.testsigma.onboarding;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CircularDependency {
    public static void main(String args[]) throws IOException, ParseException {
        ObjectMapper mapper = new ObjectMapper();

        Room room1 = new Room(1, 101, "100");
        Room room2 = new Room(2,102, "150");
        Reservation reservation1 = new Reservation(1, LocalDate.parse("2021-02-02"), LocalDate.parse("2021-01-20"), room1);
        Reservation reservation2 = new Reservation(2, LocalDate.parse("2021-02-02"), LocalDate.parse("2021-01-21"), room1);
        Reservation reservation3 = new Reservation(3, LocalDate.parse("2021-02-02"), LocalDate.parse("2021-01-22"), room2);
        Reservation reservation4 = new Reservation(4, LocalDate.parse("2021-02-02"), LocalDate.parse("2021-01-23"), room2);

        room1.addReservation(reservation1);
        room1.addReservation(reservation2);
        room1.addReservation(reservation3);
        room2.addReservation(reservation4);

        String room1String = mapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(room1);
        String room2String = mapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(room2);
        String reservationString = mapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(reservation1);

        System.out.println(room1String);
        System.out.println(room2String);
        System.out.println(reservationString);
    }
}
class Reservation {
    public Integer id;
    public LocalDate checkin;
    public LocalDate checkout;
//    @JsonBackReference
    public Room room;

    Reservation(LocalDate checkin, LocalDate checkout){
        this.checkin = checkin;
        this.checkout = checkout;
    }

    Reservation(Integer id, LocalDate checkin, LocalDate checkout, Room room){
        this.id = id;
        this.checkin = checkin;
        this.checkout = checkout;
        this.room = room;
    }
}

class Room {
    public Integer id;
    public Integer roomNumber;
    public String price;
//    @JsonManagedReference
    private List<Reservation> reservationList;

    Room(Integer id, String price) {
        this.id = id;
        this.price = price;
    }

    Room(Integer id, Integer roomNumber, String price) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.price = price;
    }

    public void addReservation(Reservation reservation){
        if(null == reservationList) reservationList = new ArrayList<>();
        reservationList.add(reservation);
    }
}