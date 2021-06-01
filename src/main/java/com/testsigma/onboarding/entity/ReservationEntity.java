package com.testsigma.onboarding.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "Reservation")
@NoArgsConstructor
public class ReservationEntity {

    @Id @Getter @Setter @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull @Getter @Setter
    private LocalDate checkin;

    @NotNull @Getter @Setter
    private LocalDate checkout;

    @ManyToOne(targetEntity = RoomEntity.class) @Getter @Setter @JoinColumn(name = "room_Id")
    private RoomEntity roomEntity;

    public ReservationEntity(@NotNull LocalDate checkin, @NotNull LocalDate checkout) {
        this.checkin = checkin;
        this.checkout = checkout;
    }

    public ReservationEntity(@NotNull LocalDate checkin, @NotNull LocalDate checkout,RoomEntity room) {
        this.checkin = checkin;
        this.checkout = checkout;
        this.roomEntity = room;
    }

    @Override
    public String toString() {
        return "ReservationEntity{" +
                "id=" + id +
                ", checkin=" + checkin +
                ", checkout=" + checkout +
                ", roomId=" + roomEntity.getId() +
                '}';
    }
}
