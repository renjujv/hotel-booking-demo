package com.testsigma.onboarding.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "RESERVATION")
@NoArgsConstructor @ToString
public class Reservation {

    @Id @Getter @Setter @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter @Setter
    private LocalDate checkin;

    @Getter @Setter
    private LocalDate checkout;

    @Getter @Setter @ManyToOne(targetEntity = Room.class)
    private Room bookedRoom;

    public Reservation(LocalDate checkin, LocalDate checkout) {
        this.checkin = checkin;
        this.checkout = checkout;
    }

    public Reservation(LocalDate checkin, LocalDate checkout, Room bookedRoom) {
        this.checkin = checkin;
        this.checkout = checkout;
        this.bookedRoom = bookedRoom;
    }
}
