package com.testsigma.onboarding.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.testsigma.onboarding.serialization.ReservationSerializer;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "RESERVATION")
@NoArgsConstructor
@JsonSerialize(using = ReservationSerializer.class)
public class Reservation {

    @Id @Getter @Setter @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter @Setter @NotNull
    private LocalDate checkin;

    @Getter @Setter @NotNull
    private LocalDate checkout;

    @Getter @Setter @ManyToOne(targetEntity = Room.class)
    private Room bookedRoom;

    public Reservation(@NotNull LocalDate checkin, @NotNull LocalDate checkout, Room bookedRoom) {
        this.checkin = checkin;
        this.checkout = checkout;
        this.bookedRoom = bookedRoom;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", checkin=" + checkin +
                ", checkout=" + checkout +
                ", bookedRoom=" + (bookedRoom==null?"null":bookedRoom.getRoomNumber()) +
                '}';
    }
}
