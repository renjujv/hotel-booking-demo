package com.linkedin.learning.converter;

import com.linkedin.learning.entity.ReservationEntity;
import com.linkedin.learning.model.response.ReservationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class ReservationEntityToReservationResponseConverter implements Converter<ReservationEntity, ReservationResponse> {

    @Override
    public ReservationResponse convert(ReservationEntity source) {
        ReservationResponse reservationResponse = new ReservationResponse();
        reservationResponse.setCheckin(source.getCheckin());
        reservationResponse.setCheckout(source.getCheckout());

        if(source.getId() != null) reservationResponse.setId(source.getId());
        else log.warn("Reservation entity ID should be fetched from reservation request. Please check logs.");

        log.debug("converting reservation entity '{}' to reservation response.",reservationResponse.toString());
        return reservationResponse;
    }
}
