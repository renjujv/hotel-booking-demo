package com.linkedin.learning.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Room")
@NoArgsConstructor
public class RoomEntity {

    @Id @Getter @Setter @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull @Getter @Setter
    private Integer roomNumber;

    @NotNull @Getter @Setter
    private String price;

    @Getter @Setter @OneToMany(mappedBy = "roomEntity",fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<ReservationEntity> reservationList;

    public RoomEntity(@NotNull Integer roomNumber, @NotNull String price) {
        this.roomNumber = roomNumber;
        this.price = price;
    }

    public void addReservationEntity(ReservationEntity reservation){
        if(null == reservationList) reservationList = new ArrayList<>();
        reservationList.add(reservation);
    }

    @Override
    public String toString() {
        return "RoomEntity{" +
                "id=" + id +
                ", roomNumber=" + roomNumber +
                ", price='" + price + '\'' +
                ", reservationList=" + reservationList +
                '}';
    }
}
