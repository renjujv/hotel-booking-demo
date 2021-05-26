package com.linkedin.learning.converter;

import com.linkedin.learning.entity.RoomEntity;
import com.linkedin.learning.model.Links;
import com.linkedin.learning.model.Self;
import com.linkedin.learning.model.response.ReservableRoomResponse;
import com.linkedin.learning.rest.ResourceConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class RoomEntityToRoomResponseConverter implements Converter<RoomEntity, ReservableRoomResponse> {

    public ReservableRoomResponse convert(RoomEntity roomEntity) {
        ReservableRoomResponse reservableRoomResponse = new ReservableRoomResponse();
        reservableRoomResponse.setRoomNumber(roomEntity.getRoomNumber());
        reservableRoomResponse.setPrice(Integer.valueOf(roomEntity.getPrice()));
        reservableRoomResponse.setId(roomEntity.getId());
        Links links = new Links();
        Self self = new Self();
        self.setRef(ResourceConstants.ROOM_RESERVATION_V1 + "/" + roomEntity.getId());
        links.setSelf(self);
        reservableRoomResponse.setLinks(links);
        log.info("converting room entity to reservable room response {}",reservableRoomResponse);
        return reservableRoomResponse;
    }
}
