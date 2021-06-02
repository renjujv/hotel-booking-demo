package com.testsigma.onboarding.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//TODO 1. Try implementing serializable for this and Reservation Entity
//     2. Implement AbstractAuditable


@Entity
@Table(name = "ROOM")
@NoArgsConstructor
public class Room {

    @Id @Getter @Setter @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull @Getter @Setter
    private Integer roomNumber;

    @NotNull @Getter @Setter
    private String price;

    @JsonIgnore
    @Getter @Setter
    @OneToMany(mappedBy = "bookedRoom",fetch = FetchType.EAGER, cascade = CascadeType.PERSIST )
    private List<Reservation> reservationsList;

    public Room(@NotNull Integer roomNumber, @NotNull String price) {
        this.roomNumber = roomNumber;
        this.price = price;
    }

    public void addReservation(Reservation reservation){
        if(reservationsList==null) reservationsList = new ArrayList<>();
        reservationsList.add(reservation);
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", roomNumber=" + roomNumber +
                ", price='" + price + '\'' +
                ", reservationsList=" + reservationsList.stream().map(reservation ->
                reservation.getBookedRoom().roomNumber).collect(Collectors.toList()) +
                '}';
    }
}
