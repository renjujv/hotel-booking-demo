package com.testsigma.onboarding.converter;

import com.testsigma.onboarding.entity.RoomEntity;
import com.testsigma.onboarding.model.Links;
import com.testsigma.onboarding.model.Self;
import com.testsigma.onboarding.model.response.ReservableRoomResponse;
import com.testsigma.onboarding.rest.ResourceConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class RoomEntityToReservableRoomResponseConverter implements Converter<RoomEntity, ReservableRoomResponse> {

    public static ReservableRoomResponse converter(RoomEntity source) {
        ReservableRoomResponse reservableRoomResponse = new ReservableRoomResponse();
        reservableRoomResponse.setRoomNumber(source.getRoomNumber());
        reservableRoomResponse.setPrice(Integer.valueOf(source.getPrice()));
        Links links = new Links();
        Self self = new Self();
        self.setRef(ResourceConstants.ROOM_RESERVATION_V1 + "/" + source.getId());
        links.setSelf(self);
        reservableRoomResponse.setLinks(links);

        if(source.getId() != null) reservableRoomResponse.setId(source.getId());
        else log.warn("Room Entity ID should be auto-generated. Please check logs.");

        log.debug("converting room entity to reservable room response {}",reservableRoomResponse);
        return reservableRoomResponse;
    }

    @Override
    public ReservableRoomResponse convert(RoomEntity roomEntity){
        log.error("Calling unintended converter in RoomEntityToReservableRoomResponseConverter");
        return null;
    }
}
