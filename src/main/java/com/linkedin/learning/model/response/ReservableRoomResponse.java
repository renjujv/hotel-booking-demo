package com.linkedin.learning.model.response;

import com.linkedin.learning.model.Links;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@AllArgsConstructor @NoArgsConstructor @ToString
public class ReservableRoomResponse {
    @Getter @Setter @NotNull
    private Integer id;
    @Getter @Setter @NotNull
    private Integer roomNumber;
    @Getter @Setter @NotNull
    private Integer price;
    @Getter @Setter
    private Links links;

    public ReservableRoomResponse(Integer roomNumber, Integer price) {
        this.roomNumber = roomNumber;
        this.price = price;
    }


}
