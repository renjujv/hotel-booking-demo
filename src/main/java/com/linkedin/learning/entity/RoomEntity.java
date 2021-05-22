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

    @Id @Getter @Setter @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull @Getter @Setter
    private Integer RoomNumber;

    @NonNull @Getter @Setter
    private String Price;

    @Getter @Setter @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    private List<ReservationEntity> reservationEntityList;

    public RoomEntity(@NonNull Integer roomNumber, @NonNull String price) {
        this.RoomNumber = roomNumber;
        this.Price = price;
    }

    public void addReservationEntity(ReservationEntity reservationEntity){
        if(null == reservationEntityList) reservationEntityList = new ArrayList<>();
        reservationEntityList.add(reservationEntity);
    }
}
