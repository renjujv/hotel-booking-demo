package com.testsigma.onboarding.converter;

import com.testsigma.onboarding.entity.Room;
import com.testsigma.onboarding.model.Links;
import com.testsigma.onboarding.model.Self;
import com.testsigma.onboarding.model.response.ReservableRoomResponse;
import com.testsigma.onboarding.rest.ResourceConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class RoomEntityToReservableRoomResponseConverter implements Converter<Room, ReservableRoomResponse> {

    public static ReservableRoomResponse converter(Room source) {
//        log.debug("Room Entity: {}"+source.toString());
        ReservableRoomResponse reservableRoomResponse = new ReservableRoomResponse();
        reservableRoomResponse.setRoomNumber(source.getRoomNumber());
        reservableRoomResponse.setPrice(Integer.valueOf(source.getPrice()));
        Links links = new Links();
        Self self = new Self();
        self.setRef(ResourceConstants.ROOM_RESERVATION_V1 + "/" + source.getId());
        links.setSelf(self);
        reservableRoomResponse.setLinks(links);

        if(source.getId() != null) {
            log.debug("Setting Reservable Room Response ID from Room Entity ID {}",source.getId());
            reservableRoomResponse.setId(source.getId());
        }
        else log.warn("Room Entity ID should be auto-generated. Please check logs.");

        log.debug("Reservable room response {}",reservableRoomResponse.toString());
        return reservableRoomResponse;
    }

    @Override
    public ReservableRoomResponse convert(Room room){
        log.error("Calling unintended converter in RoomEntityToReservableRoomResponseConverter");
        return null;
    }
}
