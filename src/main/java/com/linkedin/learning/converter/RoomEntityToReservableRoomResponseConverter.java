package com.linkedin.learning.converter;

import com.linkedin.learning.entity.RoomEntity;
import com.linkedin.learning.model.Links;
import com.linkedin.learning.model.Self;
import com.linkedin.learning.model.response.ReservableRoomResponse;
import com.linkedin.learning.rest.ResourceConstants;
import org.springframework.core.convert.converter.Converter;

public class RoomEntityToReservableRoomResponseConverter implements Converter<RoomEntity, ReservableRoomResponse> {

    public ReservableRoomResponse convert(RoomEntity roomEntity) {
        ReservableRoomResponse reservableRoomResponse = new ReservableRoomResponse();
        return reservableRoomResponse;
    }

    public static ReservableRoomResponse convertF(RoomEntity roomEntity) {
        ReservableRoomResponse reservableRoomResponse = new ReservableRoomResponse();
        reservableRoomResponse.setRoomNumber(roomEntity.getRoomNumber());
        reservableRoomResponse.setPrice(Integer.valueOf(roomEntity.getPrice()));
        reservableRoomResponse.setId(roomEntity.getId());

        Links links = new Links();
        Self self = new Self();
        self.setRef(ResourceConstants.ROOM_RESERVATION_V1 + "/" + roomEntity.getId());
        links.setSelf(self);
        reservableRoomResponse.setLinks(links);
        return reservableRoomResponse;
    }
}
