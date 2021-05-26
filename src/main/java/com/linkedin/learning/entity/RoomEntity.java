package com.linkedin.learning.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Room")
@NoArgsConstructor @ToString
public class RoomEntity {

    @Id @Getter @Setter @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull @Getter @Setter
    private Integer roomNumber;

    @NotNull @Getter @Setter
    private String price;

    @Getter @Setter @OneToMany(mappedBy = "roomEntity",fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<ReservationEntity> reservationEntityList;

    public RoomEntity(@NotNull Integer roomNumber, @NotNull String price) {
        this.roomNumber = roomNumber;
        this.price = price;
    }

    public void addReservationEntity(ReservationEntity reservationEntity){
        if(null == reservationEntityList) reservationEntityList = new ArrayList<>();
        reservationEntityList.add(reservationEntity);
    }
}
