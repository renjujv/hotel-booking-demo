package com.linkedin.learning.model.response;

import com.linkedin.learning.model.Links;
import lombok.*;

@AllArgsConstructor @NoArgsConstructor @ToString
public class ReservableRoomResponse {
    @Getter @Setter @NonNull
    private Integer id;
    @Getter @Setter @NonNull
    private Integer roomNumber;
    @Getter @Setter @NonNull
    private Integer price;
    @Getter @Setter
    private Links links;

    public ReservableRoomResponse(Integer roomNumber, Integer price) {
        this.roomNumber = roomNumber;
        this.price = price;
    }
}
