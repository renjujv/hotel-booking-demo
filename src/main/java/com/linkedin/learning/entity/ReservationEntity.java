package com.linkedin.learning.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "Reservation")
@NoArgsConstructor
public class ReservationEntity {

    @Id @Getter @Setter @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @NotNull @Getter @Setter
    private LocalDate checkin;

    @NotNull @Getter @Setter
    private LocalDate checkout;

    @ManyToOne @Getter @Setter
    private RoomEntity roomEntity;

    public ReservationEntity(@NotNull LocalDate checkin, @NotNull LocalDate checkout) {
        this.checkin = checkin;
        this.checkout = checkout;
    }
}
