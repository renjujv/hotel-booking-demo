package com.testsigma.onboarding.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

//TODO Try implementing serializable for this and Reservation Entity

@Entity
@Table(name = "ROOM")
@NoArgsConstructor @ToString
public class Room {

    @Id @Getter @Setter @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull @Getter @Setter
    private Integer roomNumber;

    @NotNull @Getter @Setter
    private String price;

    @Getter @Setter @OneToMany(mappedBy = "Room",fetch = FetchType.EAGER, cascade = CascadeType.PERSIST )
    private List<Reservation> reservationsList;

    public Room(@NotNull Integer roomNumber, @NotNull String price) {
        this.roomNumber = roomNumber;
        this.price = price;
    }

    public void addReservation(Reservation reservation){
        if(reservationsList==null) reservationsList = new ArrayList<>();
        reservationsList.add(reservation);
    }
}
